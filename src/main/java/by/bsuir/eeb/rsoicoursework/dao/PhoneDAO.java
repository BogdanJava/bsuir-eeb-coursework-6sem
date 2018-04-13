package by.bsuir.eeb.rsoicoursework.dao;

import by.bsuir.eeb.rsoicoursework.model.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhoneDAO extends JpaRepository<Phone, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM phone WHERE phone.user_id = :id")
    List<Phone> getPhonesByUserId(@Param("id") long id);
}
