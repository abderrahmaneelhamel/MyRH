package cum.MyRH.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import static org.springframework.security.config.Customizer.withDefaults;

import static cum.MyRH.Models.user.Permission.ADMIN_CREATE;
import static cum.MyRH.Models.user.Permission.ADMIN_DELETE;
import static cum.MyRH.Models.user.Permission.ADMIN_READ;
import static cum.MyRH.Models.user.Permission.ADMIN_UPDATE;
import static cum.MyRH.Models.user.Permission.COMPANY_CREATE;
import static cum.MyRH.Models.user.Permission.COMPANY_DELETE;
import static cum.MyRH.Models.user.Permission.COMPANY_READ;
import static cum.MyRH.Models.user.Permission.COMPANY_UPDATE;
import static cum.MyRH.Models.user.Permission.APPLICANT_CREATE;
import static cum.MyRH.Models.user.Permission.APPLICANT_DELETE;
import static cum.MyRH.Models.user.Permission.APPLICANT_READ;
import static cum.MyRH.Models.user.Permission.APPLICANT_UPDATE;
import static cum.MyRH.Models.user.Role.ADMIN;
import static cum.MyRH.Models.user.Role.COMPANY;
import static cum.MyRH.Models.user.Role.APPLICANT;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private static final String[] WHITE_LIST_URL = {
            "/api/auth/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"};
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                .requestMatchers("/api/admin/**").hasAnyRole(ADMIN.name())
                                .requestMatchers("/api/quiz/**").hasAnyRole(ADMIN.name(),APPLICANT.name())
                                .requestMatchers("/api/company/**").hasAnyRole(COMPANY.name(), APPLICANT.name(),ADMIN.name())
                                .requestMatchers("/api/job/**").hasAnyRole(COMPANY.name(), APPLICANT.name(),ADMIN.name())
                                .requestMatchers("/api/applicant/**").hasAnyRole(APPLICANT.name(),ADMIN.name(),COMPANY.name())
                                .requestMatchers(GET, "/api/admin/**").hasAnyAuthority(ADMIN_READ.name())
                                .requestMatchers(GET, "/api/company/**").hasAnyAuthority(COMPANY_READ.name())
                                .requestMatchers(GET, "/api/company/**").hasAnyAuthority(APPLICANT_READ.name())
                                .requestMatchers(GET, "/api/job/**").hasAnyAuthority(APPLICANT_READ.name())
                                .requestMatchers(GET, "/api/applicant/**").hasAnyAuthority(APPLICANT_READ.name())
                                .requestMatchers(GET, "/api/applicant/**").hasAnyAuthority(COMPANY_READ.name())
                                .requestMatchers(POST, "/api/admin/**").hasAnyAuthority(ADMIN_CREATE.name())
                                .requestMatchers(POST, "/api/quiz/**").hasAnyAuthority(ADMIN_CREATE.name())
                                .requestMatchers(POST, "/api/quiz/**").hasAnyAuthority(ADMIN_READ.name())
                                .requestMatchers(POST, "/api/quiz/**").hasAnyAuthority(APPLICANT_READ.name())
                                .requestMatchers(POST, "/api/company/**").hasAnyAuthority(COMPANY_CREATE.name())
                                .requestMatchers(POST, "/api/applicant/**").hasAnyAuthority(APPLICANT_CREATE.name())
                                .requestMatchers(PUT, "/api/admin/**").hasAnyAuthority(ADMIN_UPDATE.name())
                                .requestMatchers(PUT, "/api/company/**").hasAnyAuthority(COMPANY_UPDATE.name())
                                .requestMatchers(PUT, "/api/applicant/**").hasAnyAuthority(APPLICANT_UPDATE.name())
                                .requestMatchers(DELETE, "/api/admin/**").hasAnyAuthority(ADMIN_DELETE.name())
                                .requestMatchers(DELETE, "/api/company/**").hasAnyAuthority(COMPANY_DELETE.name())
                                .requestMatchers(DELETE, "/api/applicant/**").hasAnyAuthority(APPLICANT_DELETE.name())
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                );

        return http
                    .oauth2Login(withDefaults())
                    .formLogin(withDefaults())
                    .build();
    }
}
