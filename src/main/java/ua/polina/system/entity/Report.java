package ua.polina.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "client_fk")
    private Client client;

    @Column(name = "date")
    private LocalDateTime date;

    @NotBlank(message = "Comment is mandatory")
    @Column(name = "comment")
    private String comment;

    @NotNull
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany
    @JoinTable(
            name = "attached_file",
            joinColumns = @JoinColumn(name = "report_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id"))
    private Set<File> files;

    @JsonIgnore
    @OneToMany(mappedBy = "report")
    private Set<Renouncement> renouncement;
}
