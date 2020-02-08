package ua.polina.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.polina.system.entity.Renouncement;
import ua.polina.system.entity.Report;

import java.util.List;

@Repository
public interface RenouncementRepository extends JpaRepository<Renouncement, Long> {
    List<Renouncement> findByReport(Report report);
}
