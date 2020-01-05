package ua.polina.finalProject.SystemOfCheckingTaxReports.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "individual")
public class Individual {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id")
    private Long id;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name="clientFk")
    private Client client;

    @Column(name="surname")
    private String surname;

    @Column(name="first_name")
    private String firstName;

    @Column(name="second_name")
    private String secondName;

    @Column(name="passport")
    private String passport;

    @Column(name="identification_code")
    private String identCode;

    @Column(name="address")
    private String address;
}
