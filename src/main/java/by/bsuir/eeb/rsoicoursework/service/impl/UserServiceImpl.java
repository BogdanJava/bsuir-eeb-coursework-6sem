package by.bsuir.eeb.rsoicoursework.service.impl;

import by.bsuir.eeb.rsoicoursework.dao.UserDAO;
import by.bsuir.eeb.rsoicoursework.model.User;
import by.bsuir.eeb.rsoicoursework.model.dto.Page;
import by.bsuir.eeb.rsoicoursework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Bogdan Shishkin
 * project: rsoi-coursework
 * date/time: 18.03.2018 / 2:12
 * email: bogdanshishkin1998@gmail.com
 */

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userDAO.save(user);
    }

    @Override
    public void delete(User user) {
        userDAO.delete(user);
    }

    @Override
    public User findById(long id) {
        return userDAO.getOne(id);
    }

    @Override
    public User update(User user) {
        return entityManager.merge(user);
    }

    @Override
    public List<User> getAll() {
        return userDAO.findAll();
    }

    @Override
    public List<User> getAllLimited(Page page) {
        return userDAO.findAll(new PageRequest(page.getFrom(), page.getLength())).getContent();
    }

    @Override
    public User findByEmail(String email) {
        return userDAO.getByEmail(email);
    }
}
