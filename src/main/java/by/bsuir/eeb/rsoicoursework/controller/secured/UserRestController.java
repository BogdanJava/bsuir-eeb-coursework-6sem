package by.bsuir.eeb.rsoicoursework.controller.secured;

import by.bsuir.eeb.rsoicoursework.model.Phone;
import by.bsuir.eeb.rsoicoursework.model.User;
import by.bsuir.eeb.rsoicoursework.model.dto.Page;
import by.bsuir.eeb.rsoicoursework.model.dto.PasswordChangeData;
import by.bsuir.eeb.rsoicoursework.service.PhoneService;
import by.bsuir.eeb.rsoicoursework.service.UserService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Bogdan Shishkin
 * project: rsoi-coursework
 * date/time: 19.03.2018 / 23:07
 * email: bogdanshishkin1998@gmail.com
 */

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private PhoneService phoneService;

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/{password}")
    public ResponseEntity isPasswordCorrect(@PathVariable Long id, @PathVariable String password) {
        return ResponseEntity.ok(ImmutableMap
                .builder()
                .put("correct", userService.isOldPasswordCorrect(id, password))
                .build());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsers(HttpServletRequest request) {
        return ResponseEntity.ok(userService.getAllLimited(Page.fromRequest(request)));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity editUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.update(user));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/changePass")
    public ResponseEntity changePassword(@RequestBody PasswordChangeData passwordChangeData) {
        return ResponseEntity.ok(ImmutableMap.of("success", userService.changePassword(passwordChangeData)));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{userId}/phones")
    public ResponseEntity getUserPhones(@PathVariable long userId) {
        List<Phone> phonesByUserId = phoneService.getByUserId(userId);
        if (phonesByUserId == null) {
            return ResponseEntity.badRequest().body(ImmutableMap.of("error", "No such user exists"));
        } else {
            return ResponseEntity.ok(phonesByUserId);
        }
    }

}
