package ua.polina.finalProject.SystemOfCheckingTaxReports.entity;

import org.springframework.security.core.GrantedAuthority;

public enum RoleType implements GrantedAuthority {
    CLIENT,
    INSPECTOR,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
