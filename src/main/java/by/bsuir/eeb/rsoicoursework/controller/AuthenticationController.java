package by.bsuir.eeb.rsoicoursework.controller;

import by.bsuir.eeb.rsoicoursework.annotation.FreeAccess;
import by.bsuir.eeb.rsoicoursework.http.HttpResponseEntity;
import by.bsuir.eeb.rsoicoursework.model.User;
import by.bsuir.eeb.rsoicoursework.security.SecurityTools;
import by.bsuir.eeb.rsoicoursework.service.UserService;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@FreeAccess
@RestController
@RequestMapping("/auth")
@PropertySource("classpath:application.properties")
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityTools securityUtils;

    @Autowired
    private Validator validator;

    /**
     * @param credentials User data that was sent from Client
     * @return Just now registered ${@link User} or collection of errors
     */
    @RequestMapping(method = RequestMethod.POST, value = "/signup")
    public ResponseEntity signUp(@RequestBody User credentials) {
        Set<ConstraintViolation<User>> violations = validator.validate(credentials);
        if (!violations.isEmpty()) {
            violations.forEach(violation -> LOGGER.debug(violation.getMessage()));
            return ResponseEntity.badRequest().body(
                    ImmutableMap.of(
                            "message", "Validation error",
                            "errors", violations.stream()
                                    .map(ConstraintViolation::getMessage)
                                    .collect(Collectors.toList())));
        }
        return ResponseEntity.ok(userService.save(credentials));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity login(@RequestBody User credentials) {
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
        return ResponseEntity.ok(securityUtils.buildJwtToken(user));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/checkEmailExists/{email}")
    public Map<String, Boolean> checkEmailExists(@PathVariable String email) {
        return ImmutableMap.of("exists", userService.emailAlreadyReserved(email));
    }

}
