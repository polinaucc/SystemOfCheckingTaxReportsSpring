package ua.polina.finalProject.SystemOfCheckingTaxReports.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "legal_entity")
public class LegalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id")
    private Long id;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name="clientFk")
    private Client client;

    @Column(name="name")
    private String name;

    @Column(name="edrpou")
    private String edrpou;

    @Column(name="mfo")
    private String mfo;

    @Column(name="address")
    private String address;
}
