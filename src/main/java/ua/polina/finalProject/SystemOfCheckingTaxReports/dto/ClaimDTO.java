package ua.polina.finalProject.SystemOfCheckingTaxReports.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClaimDTO {
    private Long clientId;
    private Long inspectorId;
    private String reason;
    private String status;
}
