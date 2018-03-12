package dhbw.leftlovers.service.uaa.repository;

import dhbw.leftlovers.service.uaa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
}
