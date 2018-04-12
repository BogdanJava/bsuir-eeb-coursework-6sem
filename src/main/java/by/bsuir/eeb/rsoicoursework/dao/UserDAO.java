package by.bsuir.eeb.rsoicoursework.dao;

import by.bsuir.eeb.rsoicoursework.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @author Bogdan Shishkin
 * project: rsoi-coursework
 * date/time: 18.03.2018 / 1:57
 * email: bogdanshishkin1998@gmail.com
 */

public interface UserDAO extends JpaRepository<User, Long> {
    User getByEmail(String email);

    @Query("SELECT new User(u.id, u.password) FROM User u WHERE u.id = :id")
    User getPasswordById(@Param("id") long id);

}
