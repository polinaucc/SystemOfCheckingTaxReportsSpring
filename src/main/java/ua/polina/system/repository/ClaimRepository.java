package ua.polina.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.polina.system.entity.Claim;
import ua.polina.system.entity.Client;
import ua.polina.system.entity.Inspector;

import java.util.List;

@Repository
public interface ClaimRepository extends JpaRepository<Claim, Long> {
    List<Claim> findByInspector(Inspector inspector);
    List<Claim> findByClient(Client client);
}
