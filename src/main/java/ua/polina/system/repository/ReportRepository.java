package ua.polina.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.polina.system.entity.Client;
import ua.polina.system.entity.Inspector;
import ua.polina.system.entity.Report;
import ua.polina.system.entity.Status;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByClient(Client client);

    //TODO: add query to constants
    @Query(
            value = "SELECT r from Report r WHERE r.client IN (" +
                    "SELECT c.id from Client c WHERE c.inspector=?1)"
    )
    List<Report> findByInspector(Inspector inspector);

    List<Report> findByStatus(Status status);

}
