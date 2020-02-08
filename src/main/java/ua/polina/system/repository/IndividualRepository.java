package ua.polina.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.polina.system.entity.*;

import java.util.List;
import java.util.Optional;

@Repository
public interface IndividualRepository extends JpaRepository<Individual, Long> {
    Optional<Individual> findByPassport (String passport);
    Optional<Individual> findByIdentCode (String identCode);
    List<Individual> findByAddress (String address);
    boolean existsIndividualByPassport(String passport);
    boolean existsIndividualByIdentCode(String identCode);
}
