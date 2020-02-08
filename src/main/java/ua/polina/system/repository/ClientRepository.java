package ua.polina.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.polina.system.entity.Client;
import ua.polina.system.entity.ClientType;
import ua.polina.system.entity.Inspector;
import ua.polina.system.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    List<Client> findByClientTypeAndInspector(ClientType type, Inspector inspector);
    Optional<Client> findByUser(User user);
}
