package dhbw.leftlovers.service.uaa.controller;

import dhbw.leftlovers.service.uaa.entity.User;
import dhbw.leftlovers.service.uaa.exception.UsernameTakenException;
import dhbw.leftlovers.service.uaa.repository.UserRepository;
import dhbw.leftlovers.service.uaa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public void signUp(@RequestBody User user) {
        this.checkIfUsernameIsPresent(user.getUsername());
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userService.save(user);
	}

    private void checkIfUsernameIsPresent(String userId) {
        this.userService.findByUsername(userId).ifPresent(user -> {
            throw new UsernameTakenException(userId);
        });
    }

	// TODO: Auto-Login, Login-Route auf Service umleiten
}