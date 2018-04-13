package by.bsuir.eeb.rsoicoursework.service;

import by.bsuir.eeb.rsoicoursework.model.Phone;

import java.util.List;

public interface PhoneService {
    List<Phone> getByUserId(long userId);
    Phone getById(long id);
    Phone save(Phone phone, long userId);
    Phone update(Phone phone);
    boolean delete(long id);
}
