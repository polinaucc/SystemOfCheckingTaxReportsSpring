package ua.polina.finalProject.SystemOfCheckingTaxReports.dto;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Data
public class LoginDTO {
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
