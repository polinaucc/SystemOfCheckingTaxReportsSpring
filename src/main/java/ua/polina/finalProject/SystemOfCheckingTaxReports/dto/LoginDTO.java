package ua.polina.finalProject.SystemOfCheckingTaxReports.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginDTO {
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
