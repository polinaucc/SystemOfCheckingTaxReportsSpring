package ua.polina.finalProject.SystemOfCheckingTaxReports.dto;

import lombok.*;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Claim;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClaimsDTO {
    private List<Claim> claims;
}
