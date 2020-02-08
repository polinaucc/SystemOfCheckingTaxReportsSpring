package ua.polina.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.polina.system.entity.LegalEntity;

import java.util.Optional;

@Repository
public interface LegalEntityRepository extends JpaRepository<LegalEntity, Long> {
    Optional<LegalEntity> findByEdrpou(String edrpou);
    boolean existsLegalEntityByEdrpou(String edrpou);
    boolean existsLegalEntityByName(String name);
}
