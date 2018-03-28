package dhbw.leftlovers.service.uaa.service;

import dhbw.leftlovers.service.uaa.entity.User;
import dhbw.leftlovers.service.uaa.exception.EmailTakenException;
import dhbw.leftlovers.service.uaa.exception.JWTValidationException;
import dhbw.leftlovers.service.uaa.exception.LoginException;
import dhbw.leftlovers.service.uaa.exception.UsernameTakenException;
import dhbw.leftlovers.service.uaa.repository.UserRepository;
import dhbw.leftlovers.service.uaa.security.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final JWTTokenProvider JWTTokenProvider;

    private final AuthenticationManager authenticationManager;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, JWTTokenProvider JWTTokenProvider, AuthenticationManager authenticationManager, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.JWTTokenProvider = JWTTokenProvider;
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public String login(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return JWTTokenProvider.createToken(username);
        } catch (AuthenticationException e) {
            throw new LoginException();
        }
    }

    @Override
    public String signup(User user) {
        this.checkIfUsernameIsPresent(user.getUsername());
        this.checkIfEmailIsPresent(user.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        this.save(user);
        return JWTTokenProvider.createToken(user.getUsername());
    }

    @Override
    @Transactional
    public void delete(String username) {
        userRepository.deleteByUsername(username);
    }

    @Override
    @Transactional
    public void update(String username, User input) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        user.setEmail(input.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(input.getPassword()));
    }

    @Override
    public User getUserFromJWT(HttpServletRequest req) {
        return userRepository.findByUsername(JWTTokenProvider.getUsername(JWTTokenProvider.resolveToken(req))).orElseThrow(JWTValidationException::new);
    }

    @Override
    public boolean validateToken(HttpServletRequest req) {
        return JWTTokenProvider.validateToken(req);
    }

    private void checkIfUsernameIsPresent(String username) {
        this.findByUsername(username).ifPresent(user -> {
            throw new UsernameTakenException(username);
        });
    }

    private void checkIfEmailIsPresent(String email) {
        this.findByEmail(email).ifPresent(user -> {
            throw new EmailTakenException(email);
        });
    }
}
