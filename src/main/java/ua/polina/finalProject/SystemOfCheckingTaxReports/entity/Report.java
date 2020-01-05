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
@Table(name = "report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="id")
    private Long id;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name="clientFk")
    private Client client;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name="inspectorFk")
    private Inspector inspector;

    @Column(name="date")
    private Date date;

    @Column(name="comment")
    private String comment;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany
    @JoinTable(
            name = "attached_fi;es",
            joinColumns = @JoinColumn(name = "report_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id"))
    private Set<File> files;

    @OneToMany(mappedBy = "report")
    private Set<Renouncement> renouncement;
}
