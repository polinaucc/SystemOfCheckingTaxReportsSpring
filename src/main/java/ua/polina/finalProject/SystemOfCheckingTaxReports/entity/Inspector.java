package ua.polina.finalProject.SystemOfCheckingTaxReports.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "inspector")
public class Inspector {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id")
    private Long id;

    @Column(name="surname")
    private String surname;

    @Column(name="first_name")
    private String firstName;

    @Column(name="second_name")
    private String secondName;

    @Column(name="employment_date")
    private Date employmentDate;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "userFk")
    private User user;

    @OneToOne(mappedBy = "inspector")
    private Report report;

    @OneToMany(mappedBy = "inspector")
    private Set<Claim> claims;

}
