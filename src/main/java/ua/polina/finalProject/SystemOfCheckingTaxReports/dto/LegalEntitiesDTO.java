package ua.polina.finalProject.SystemOfCheckingTaxReports.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.LegalEntity;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LegalEntitiesDTO {
    private List<LegalEntity> legalEntities;
}
