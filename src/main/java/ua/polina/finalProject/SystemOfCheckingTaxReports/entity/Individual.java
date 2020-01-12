package ua.polina.finalProject.SystemOfCheckingTaxReports.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "individual")
public class Individual {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id")
    private Long id;

    @NotNull
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name="clientFk")
    private Client client;

    @NotBlank(message = "Surname is mandatory")
    @Column(name="surname")
    private String surname;

    @NotBlank(message = "First name is mandatory")
    @Column(name="first_name")
    private String firstName;

    @Column(name="second_name")
    private String secondName;

    @NotBlank(message = "Passport is mandatory")
    @Column(name="passport")
    private String passport;

    @NotBlank(message = "Identification code is mandatory")
    @Column(name="identification_code")
    private String identCode;

    @NotBlank(message = "Address is mandatory")
    @Column(name="address")
    private String address;
}
