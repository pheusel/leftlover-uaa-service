package dhbw.leftlovers.service.uaa.controller;

import dhbw.leftlovers.service.uaa.entity.*;
import dhbw.leftlovers.service.uaa.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // JWT in body

    @PostMapping("/signup")
    TokenResponse signup(@RequestBody User user) {
        return new TokenResponse(userService.signup(user));
    }

    /*

    // JWT in header

    @PostMapping("/signup")
    ResponseEntity<?> signup(@RequestBody User user) {
        String bearerToken = userService.signup(user);
        return ResponseEntity.ok().header(HEADER_STRING, TOKEN_PREFIX + bearerToken).build();
    }

    */

    // JWT in body

    @PostMapping("/login")
    TokenResponse login(@RequestBody LoginUser loginUser) {
        return new TokenResponse(userService.login(loginUser.getUsername(), loginUser.getPassword()));
    }

    /*

    // JWT in header

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        String bearerToken = userService.login(username, password);
        return ResponseEntity.ok().header(HEADER_STRING, TOKEN_PREFIX + bearerToken).build();
    }

    */

    @DeleteMapping("/{username}")
    ResponseEntity<?> deleteUser(@PathVariable String username) {
        userService.delete(username);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{username}")
    ResponseEntity<?> editUser(@PathVariable String username, @RequestBody User input) {
        userService.update(username, input);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/validate")
    JWTValidationResponse validateToken(HttpServletRequest req) {
        return new JWTValidationResponse(userService.validateToken(req));
    }

    @GetMapping("/resolve")
    UserResponse resolveToken(HttpServletRequest req) {
        return modelMapper.map(userService.getUserFromJWT(req), UserResponse.class);
    }
}
