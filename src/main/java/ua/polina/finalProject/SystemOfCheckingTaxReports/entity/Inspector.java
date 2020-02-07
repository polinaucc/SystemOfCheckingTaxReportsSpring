package ua.polina.finalProject.SystemOfCheckingTaxReports.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "inspector")
public class Inspector {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Surname is mandatory")
    @Column(name = "surname")
    private String surname;

    @NotBlank(message = "First name is mandatory")
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "employment_date")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate employmentDate;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_fk")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "inspector")
    private Set<Claim> claims;

    @OneToMany(mappedBy = "inspector")
    private Set<Client> clients;

    @Override
    public String toString() {
        return surname + " " + firstName + " " + secondName;
    }
}
