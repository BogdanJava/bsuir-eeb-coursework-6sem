package by.bsuir.eeb.rsoicoursework.dao;

import by.bsuir.eeb.rsoicoursework.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardDAO extends JpaRepository<Card, Long> {
    List<Card> getAllByUserId(long userId);
}
