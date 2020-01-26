package ua.polina.finalProject.SystemOfCheckingTaxReports.payload;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignUpRequest {
    @NotBlank
    @Size(min=3, max=20)
    private String firstName;

    @NotBlank
    @Size(min=3, max=20)
    private String secondName;

    @NotBlank
    @Size(min=3, max=20)
    private String surname;

    @NotBlank
    @Size(max=40)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;
}
