package by.bsuir.eeb.rsoicoursework.service;

import by.bsuir.eeb.rsoicoursework.model.User;
import by.bsuir.eeb.rsoicoursework.model.dto.Page;

import java.util.List;

/**
 * @author Bogdan Shishkin
 * project: rsoi-coursework
 * date/time: 18.03.2018 / 2:10
 * email: bogdanshishkin1998@gmail.com
 */

public interface UserService {
    User save(User user);
    void delete(User user);
    User findById(long id);
    User update(User user);
    List<User> getAll();
    List<User> getAllLimited(Page page);
    User findByEmail(String email);
    boolean isPasswordCorrect(long id, String password);
}
