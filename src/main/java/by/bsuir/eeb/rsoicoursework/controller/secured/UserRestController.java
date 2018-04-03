package by.bsuir.eeb.rsoicoursework.controller.secured;

import by.bsuir.eeb.rsoicoursework.annotation.FreeAccess;
import by.bsuir.eeb.rsoicoursework.model.User;
import by.bsuir.eeb.rsoicoursework.model.dto.Page;
import by.bsuir.eeb.rsoicoursework.service.UserService;
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

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<User>> getUsers(HttpServletRequest request) {
        Page page = Page.fromRequest(request);

        return ResponseEntity.ok(userService.getAllLimited(page));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity editUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.update(user));
    }

}
