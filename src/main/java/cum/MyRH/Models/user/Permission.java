package cum.MyRH.Models.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    COMPANY_READ("company:read"),
    COMPANY_UPDATE("company:update"),
    COMPANY_CREATE("company:create"),
    COMPANY_DELETE("company:delete"),
    APPLICANT_READ("applicant:read"),
    APPLICANT_UPDATE("applicant:update"),
    APPLICANT_CREATE("applicant:create"),
    APPLICANT_DELETE("applicant:delete");

    @Getter
    private final String permission;
}
