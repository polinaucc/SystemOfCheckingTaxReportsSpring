package ua.polina.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.polina.system.entity.Inspector;
import ua.polina.system.entity.User;

import java.util.Optional;

@Repository
public interface InspectorRepository extends JpaRepository<Inspector, Long> {
    Optional<Inspector> findByUser(User user);
}
