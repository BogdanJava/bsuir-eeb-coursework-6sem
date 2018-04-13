package by.bsuir.eeb.rsoicoursework.service.impl;

import by.bsuir.eeb.rsoicoursework.dao.PhoneDAO;
import by.bsuir.eeb.rsoicoursework.dao.UserDAO;
import by.bsuir.eeb.rsoicoursework.model.Phone;
import by.bsuir.eeb.rsoicoursework.model.User;
import by.bsuir.eeb.rsoicoursework.service.PhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PhoneServiceImpl implements PhoneService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PhoneDAO phoneDAO;

    @Autowired
    private UserDAO userDAO;

    @Override
    public List<Phone> getByUserId(long userId) {
        return userDAO.exists(userId) ? phoneDAO.getPhonesByUserId(userId) : null;
    }

    @Override
    public Phone getById(long id) {
        return phoneDAO.exists(id) ? phoneDAO.getOne(id) : null;
    }

    @Override
    public Phone save(Phone phone, long userId) {
        User user = userDAO.exists(userId) ? userDAO.getOne(userId) : null;
        if (user != null) {
            phone.setType(phone.getType().toLowerCase());
            phone.setUser(user);
            return phoneDAO.save(phone);
        } else {
            return null;
        }
    }

    @Override
    public Phone update(Phone phone) {
        return phoneDAO.exists(phone.getId()) ? entityManager.merge(phone) : null;
    }

    @Override
    public boolean delete(long id) {
        boolean exists = phoneDAO.exists(id);
        if(exists) {
            phoneDAO.delete(id);
            return true;
        } else {
            return false;
        }
    }
}
