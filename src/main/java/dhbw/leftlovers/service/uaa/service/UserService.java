package dhbw.leftlovers.service.uaa.service;

import dhbw.leftlovers.service.uaa.entity.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByUsername(String username);

    User save(User user);
}
