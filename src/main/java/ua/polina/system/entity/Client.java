package ua.polina.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_fk")
    private User user;

    @NotNull
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ClientType clientType;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "inspector_fk")
    private Inspector inspector;

    @JsonIgnore
    @OneToOne(mappedBy = "client")
    private Individual individual;

    @JsonIgnore
    @OneToOne(mappedBy = "client")
    private LegalEntity legalEntity;

    @JsonIgnore
    @OneToMany(mappedBy = "client")
    private Set<Report> report;

    @JsonIgnore
    @OneToMany(mappedBy = "client")
    private Set<Claim> claims;

    @Override
    public String toString() {
        if (individual != null) {
            return individual.getSurname() +
                    " " + individual.getFirstName() + " "
                    + individual.getSecondName();
        } else {
            return legalEntity.getName();
        }
    }
}
