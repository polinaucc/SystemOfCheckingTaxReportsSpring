package ua.polina.finalProject.SystemOfCheckingTaxReports.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Renouncement;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Report;

import java.util.List;
import java.util.Optional;

@Repository
public interface RenouncementRepository extends JpaRepository<Renouncement, Long> {
    List<Renouncement> findByReport(Report report);
}
