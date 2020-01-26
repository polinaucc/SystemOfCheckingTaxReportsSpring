package ua.polina.finalProject.SystemOfCheckingTaxReports.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class InspectorDTO {

    @NotBlank
    @Size(max = 40)
    @Email
    private String userEmail;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

    @NotBlank
    @Size(min=3, max=20)
    private String surname;

    @NotBlank
    @Size(min=3, max=20)
    private String firstName;

    @NotBlank
    @Size(min=3, max=20)
    private String secondName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date employmentDate;
}
