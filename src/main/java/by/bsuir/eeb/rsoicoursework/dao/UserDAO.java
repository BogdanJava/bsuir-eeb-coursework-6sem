package by.bsuir.eeb.rsoicoursework.dao;

import by.bsuir.eeb.rsoicoursework.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Bogdan Shishkin
 * project: rsoi-coursework
 * date/time: 18.03.2018 / 1:57
 * email: bogdanshishkin1998@gmail.com
 */

public interface UserDAO extends JpaRepository<User, Long> {
    User getByEmail(String email);
}
