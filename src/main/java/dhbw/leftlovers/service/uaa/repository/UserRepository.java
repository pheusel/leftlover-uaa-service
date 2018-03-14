package dhbw.leftlovers.service.uaa.repository;

import dhbw.leftlovers.service.uaa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
