package by.bsuir.eeb.rsoicoursework.service;

import by.bsuir.eeb.rsoicoursework.model.User;
import by.bsuir.eeb.rsoicoursework.model.dto.Page;
import by.bsuir.eeb.rsoicoursework.model.dto.PasswordChangeData;

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
    boolean isOldPasswordCorrect(long id, String password);
    boolean changePassword(PasswordChangeData passwordChangeData);
    boolean emailAlreadyReserved(String email);
}
