package dhbw.leftlovers.service.uaa.controller;

import dhbw.leftlovers.service.uaa.entity.User;
import dhbw.leftlovers.service.uaa.exception.UsernameTakenException;
import dhbw.leftlovers.service.uaa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/UAAService")
public class UserController {

    @Autowired
    private UserService userService;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserService userService,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/signup")
    ResponseEntity<?> signUp(@RequestBody User user) {
        this.checkIfUsernameIsPresent(user.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User response = userService.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.getId()).toUri();

        return ResponseEntity.created(location).build();
    }


    private void checkIfUsernameIsPresent(String userId) {
        this.userService.findByUsername(userId).ifPresent(user -> {
            throw new UsernameTakenException(userId);
        });
    }

    // TODO: RequestBodies validieren (NotNull etc.)
    // TODO: Auto-Login
    // TODO: Login-Route auf Service umleiten
}