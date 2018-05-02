package by.bsuir.eeb.rsoicoursework.service;

import by.bsuir.eeb.rsoicoursework.exceptions.NotEnoughMoneyException;
import by.bsuir.eeb.rsoicoursework.model.Card;
import by.bsuir.eeb.rsoicoursework.model.CardTransaction;

import java.util.List;

public interface CardManagementService {
    void executeBalanceOperation(CardTransaction cardTransaction) throws NotEnoughMoneyException;
    Card getCardById(long cardId);
    List<Card> getCardsByUserId(long userId);
    Double calculateCardBalance(long cardId);
    Card save(Card card);
    long getUserIdByCardId(long cardId);
    List<CardTransaction> getAllTransactions(long cardId);
    CardTransaction getTransaction(long transactionId);
    boolean isPasswordCorrect(long cardId, String password);
}
