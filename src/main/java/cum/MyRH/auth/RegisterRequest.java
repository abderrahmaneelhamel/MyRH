package cum.MyRH.auth;

import cum.MyRH.Models.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  // Admin
  public RegisterRequest(String name, String email, String password, Role role) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.role = role;
  }

  // Applicant
  public RegisterRequest(String name, String lastName, String email, String password, Role role, String level, String city, String profile, MultipartFile file) {
    this.name = name;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.role = role;
    this.level = level;
    this.city = city;
    this.profile = profile;
    this.file = file;
  }

  // Company
  public RegisterRequest(String name, String email, String password, Role role, String address, String phone, MultipartFile file) {
    this.name = name;
    this.email = email;
    this.password = password;
    this.role = role;
    this.address = address;
    this.phone = phone;
    this.file = file;
  }

  private String name;
  private String lastName;
  private String email;
  private String password;
  private Role role;
  private String address;
  private String phone;
  private String level;
  private String city;
  private String profile;
  private MultipartFile file;
}
