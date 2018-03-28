package dhbw.leftlovers.service.uaa.repository;

import dhbw.leftlovers.service.uaa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Transactional
    void deleteByUsername(String username);
}
