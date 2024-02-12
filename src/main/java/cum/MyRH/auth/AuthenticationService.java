package cum.MyRH.auth;

import cum.MyRH.Config.JwtService;
import cum.MyRH.Models.Entities.*;
import cum.MyRH.Models.Enums.State;
import cum.MyRH.Models.token.Token;
import cum.MyRH.Models.token.TokenRepository;
import cum.MyRH.Models.token.TokenType;
import cum.MyRH.Models.user.User;
import cum.MyRH.Models.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import cum.MyRH.Repositories.AdminRepository;
import cum.MyRH.Repositories.ApplicantRepository;
import cum.MyRH.Repositories.CompanyRepository;
import cum.MyRH.Repositories.PlanRepository;
import cum.MyRH.Services.FileService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository userRepository;
  private final CompanyRepository companyRepository;
  private final ApplicantRepository applicantRepository;
  private final PlanRepository planRepository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final FileService fileService;
  private final AuthenticationManager authenticationManager;
  private final AdminRepository adminRepository;

  public AuthenticationResponse register(RegisterRequest request) throws IOException {
    System.out.println(request);
    var user = User.builder()
        .name(request.getName())
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword()))
        .role(request.getRole())
        .build();
    var savedUser = userRepository.save(user);
    var jwtToken = jwtService.generateToken(savedUser,savedUser);
    switch (request.getRole()) {
      case ADMIN:
        Admin admin = new Admin(0L, request.getName() ,request.getEmail(),passwordEncoder.encode(request.getPassword()), State.ONLINE);
        adminRepository.save(admin);
        jwtToken = jwtService.generateToken(savedUser,savedUser,admin);
        break;
      case APPLICANT:
        Files cv = fileService.storeDataIntoFileSystem(request.getFile());
        Applicant applicant = new Applicant(0L, request.getName(), request.getLastName(), request.getEmail(),passwordEncoder.encode(request.getPassword()),request.getLevel(),request.getProfile(),request.getCity(),cv, State.ONLINE);
        applicantRepository.save(applicant);
        jwtToken = jwtService.generateToken(savedUser,savedUser,applicant);
        break;
      case COMPANY:
        Files image = fileService.storeDataIntoFileSystem(request.getFile());
        Plan plan = planRepository.findById(1L).orElse(null);
        Company company = new Company(0L, request.getName(), request.getEmail(),passwordEncoder.encode(request.getPassword()),request.getAddress(),request.getPhone(),image,plan,State.ONLINE);
        companyRepository.save(company);
        jwtToken = jwtService.generateToken(savedUser,savedUser,company);
        break;
    }
    var refreshToken = jwtService.generateRefreshToken(savedUser);
    saveUserToken(savedUser, jwtToken);
    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
        .refreshToken(refreshToken)
        .build();
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    var user = userRepository.findByEmail(request.getEmail())
        .orElseThrow();
    var jwtToken = jwtService.generateToken(user,user);
    switch (user.getRole()) {
      case ADMIN:
        Admin admin = adminRepository.findByEmail(user.getEmail());
        admin.setState(State.ONLINE);
        jwtToken = jwtService.generateToken(user, user,admin);
        break;
      case COMPANY:
        Company company = companyRepository.findCompanyWithoutImage(user.getEmail()).orElse(null);
        Long imageId = companyRepository.findCompanyImageId(company.getId());
        Files image = new Files(imageId);
        company.setImage(image);
        company.setState(State.ONLINE);
        companyRepository.save(company);
        jwtToken = jwtService.generateToken(user, user,company);
        break;
      case APPLICANT:
        Applicant applicant = applicantRepository.findApplicantWithoutCv(user.getEmail()).orElse(null);
        Long cvId = applicantRepository.findApplicantCvId(applicant.getId());
        Files cv = new Files(cvId);
        applicant.setCv(cv);
        applicant.setState(State.ONLINE);
        applicantRepository.save(applicant);
        jwtToken = jwtService.generateToken(user, user,applicant);
        break;
    }
    var refreshToken = jwtService.generateRefreshToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return AuthenticationResponse.builder()
        .accessToken(jwtToken)
            .refreshToken(refreshToken)
        .build();
  }

  private void saveUserToken(User user, String jwtToken) {
    var token = Token.builder()
        .user(user)
        .token(jwtToken)
        .tokenType(TokenType.BEARER)
        .expired(false)
        .revoked(false)
        .build();
    tokenRepository.save(token);
  }

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.userRepository.findByEmail(userEmail)
              .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user,user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }


}
