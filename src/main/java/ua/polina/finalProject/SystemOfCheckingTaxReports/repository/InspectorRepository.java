package ua.polina.finalProject.SystemOfCheckingTaxReports.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Inspector;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.User;

import java.util.Optional;

@Repository
public interface InspectorRepository extends JpaRepository<Inspector, Long> {
    Optional<Inspector> findByUser(User user);
}
