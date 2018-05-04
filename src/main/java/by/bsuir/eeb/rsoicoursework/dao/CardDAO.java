package by.bsuir.eeb.rsoicoursework.dao;

import by.bsuir.eeb.rsoicoursework.model.Card;
import by.bsuir.eeb.rsoicoursework.model.enums.CardType;
import by.bsuir.eeb.rsoicoursework.model.enums.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardDAO extends JpaRepository<Card, Long> {
    List<Card> getAllByUserId(long userId);
    List<Card> getAllByUserIdAndCurrency(long userId, Currency currency);
    Card findByCardNumber(String cardNumber);
}
