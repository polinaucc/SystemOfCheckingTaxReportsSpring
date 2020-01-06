package ua.polina.finalProject.SystemOfCheckingTaxReports.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.polina.finalProject.SystemOfCheckingTaxReports.entity.*;

import java.util.List;
import java.util.Optional;

@Repository
public interface IndividualRepository extends JpaRepository<Individual, Long> {
    Optional<List<Individual>> findByPassport (String passport);
    Optional<List<Individual>> findByIdentCode (String identCode);
    Optional<List<Individual>> findByAddress (String address);
}
