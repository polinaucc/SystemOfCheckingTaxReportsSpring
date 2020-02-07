package ua.polina.finalProject.SystemOfCheckingTaxReports.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Client;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Inspector;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Report;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Status;

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
