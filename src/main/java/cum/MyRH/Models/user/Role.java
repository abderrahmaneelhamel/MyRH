package cum.MyRH.Models.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

@RequiredArgsConstructor
public enum Role {

  USER(Collections.emptySet()),
  ADMIN(
          Set.of(
                  ADMIN_READ,
                  ADMIN_UPDATE,
                  ADMIN_DELETE,
                  ADMIN_CREATE,
                  COMPANY_READ,
                  COMPANY_UPDATE,
                  COMPANY_DELETE,
                  COMPANY_CREATE,
                  APPLICANT_READ,
                  APPLICANT_UPDATE,
                  APPLICANT_DELETE,
                  APPLICANT_CREATE
          )
  ),
  COMPANY(
          Set.of(
                  COMPANY_READ,
                  COMPANY_UPDATE,
                  COMPANY_DELETE,
                  COMPANY_CREATE
          )
  ),
  APPLICANT(
          Set.of(
                  APPLICANT_READ,
                  APPLICANT_UPDATE,
                  APPLICANT_DELETE,
                  APPLICANT_CREATE
          )
  );

  @Getter
  private final Set<Permission> permissions;

  public List<SimpleGrantedAuthority> getAuthorities() {
    var authorities = getPermissions()
            .stream()
            .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
            .collect(Collectors.toList());
    authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
    return authorities;
  }
}
