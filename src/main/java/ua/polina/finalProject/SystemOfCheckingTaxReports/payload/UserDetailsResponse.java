package ua.polina.finalProject.SystemOfCheckingTaxReports.payload;

import lombok.Builder;
import lombok.Data;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.RoleType;

@Data
@Builder
public class UserDetailsResponse {
    Long id;
    String role;
}
