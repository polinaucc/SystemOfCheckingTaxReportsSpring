package ua.polina.finalProject.SystemOfCheckingTaxReports.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name="claim")
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @JoinColumn(name="client_fk")
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Client client;

    @NotNull
    @JoinColumn(name="inspector_fk")
    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    private Inspector inspector;

    @NotBlank(message = "Reason is mandatory")
    @Column(name = "reason")
    private String reason;

    @NotNull
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Override
    public String toString() {
        if(client.getClientType()==ClientType.INDIVIDUAL)
        return "Claim{" +
                "id=" + id +
                ", client=" + client.getIndividual() +
                ", inspector=" + inspector +
                ", reason='" + reason + '\'' +
                ", status=" + status +
                '}';
        else
            return "Claim{" +
                    "id=" + id +
                    ", client=" + client.getLegalEntity() +
                    ", inspector=" + inspector +
                    ", reason='" + reason + '\'' +
                    ", status=" + status +
                    '}';

    }
}
