package dhbw.leftlovers.service.uaa.service;

import dhbw.leftlovers.service.uaa.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public interface UserService {

    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    void save(User user);

    String login(String username, String password);

    String signup(User user);

    void delete(String username);

    void update(String username, User user);

    User getUserFromJWT(HttpServletRequest req);

    boolean validateToken(HttpServletRequest req);
}
