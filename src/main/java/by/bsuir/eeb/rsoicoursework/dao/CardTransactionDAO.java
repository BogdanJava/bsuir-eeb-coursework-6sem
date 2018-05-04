package by.bsuir.eeb.rsoicoursework.dao;

import by.bsuir.eeb.rsoicoursework.model.CardTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardTransactionDAO extends JpaRepository<CardTransaction, Long> {
    List<CardTransaction> getAllByCardId(long cardId);
}
