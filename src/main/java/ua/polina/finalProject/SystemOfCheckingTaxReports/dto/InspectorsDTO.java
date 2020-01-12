package ua.polina.finalProject.SystemOfCheckingTaxReports.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Inspector;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InspectorsDTO {
    private List<Inspector> inspectors;
}
