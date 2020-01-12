package ua.polina.finalProject.SystemOfCheckingTaxReports.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Claim;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Client;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.Inspector;

import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {
    List<Claim> findByInspector(Inspector inspector);
    List<Claim> findByClient(Client client);
}
