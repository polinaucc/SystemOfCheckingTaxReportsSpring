package ua.polina.finalProject.SystemOfCheckingTaxReports.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
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
    @Column(name="id")
    private Long id;

    @NotBlank(message = "Surname is mandatory")
    @Column(name="surname")
    private String surname;

    @NotBlank(message = "First name is mandatory")
    @Column(name="first_name")
    private String firstName;

    @Column(name="second_name")
    private String secondName;

    @Column(name="employment_date")
    private Date employmentDate;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_fk")
    private User user;

    @JsonIgnore
    @OneToOne(mappedBy = "inspector")
    private Report report;

    @JsonIgnore
    @OneToMany(mappedBy = "inspector")
    private Set<Claim> claims;
}
