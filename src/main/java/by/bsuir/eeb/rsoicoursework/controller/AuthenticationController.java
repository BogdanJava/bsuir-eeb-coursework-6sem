package by.bsuir.eeb.rsoicoursework.controller;

import by.bsuir.eeb.rsoicoursework.annotation.FreeAccess;
import by.bsuir.eeb.rsoicoursework.http.AuthToken;
import by.bsuir.eeb.rsoicoursework.http.HttpResponseEntity;
import by.bsuir.eeb.rsoicoursework.model.User;
import by.bsuir.eeb.rsoicoursework.service.UserService;
import by.bsuir.eeb.rsoicoursework.security.SecurityTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@FreeAccess
@RestController
@RequestMapping("/auth")
@PropertySource("classpath:application.properties")
public class AuthenticationController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityTools securityUtils;

    /**
     * todo: add credentials validation
     * @param credentials User data that was sent from Client
     * @return Just now registered ${@link User} or null
     */
    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public User signUp(@RequestBody User credentials) {
        return userService.save(credentials);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity login(@RequestBody User credentials) {
        AuthToken jwtToken;

        if (credentials.getEmail() == null || credentials.getPassword() == null) {
            return ResponseEntity.badRequest().body(new HttpResponseEntity("Bad credentials"));
        }

        User user = userService.findByEmail(credentials.getEmail());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new HttpResponseEntity("User with such email or username not found"));
        }

        if (!passwordEncoder.matches(credentials.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new HttpResponseEntity("Incorrect password"));
        }

        jwtToken = securityUtils.buildJwtToken(user);

        return ResponseEntity.ok(jwtToken);
    }

}
