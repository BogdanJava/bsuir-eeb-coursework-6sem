package by.bsuir.eeb.rsoicoursework.service.impl;

import by.bsuir.eeb.rsoicoursework.dao.CardDAO;
import by.bsuir.eeb.rsoicoursework.dao.TransactionDAO;
import by.bsuir.eeb.rsoicoursework.exceptions.NotEnoughMoneyException;
import by.bsuir.eeb.rsoicoursework.model.Card;
import by.bsuir.eeb.rsoicoursework.model.CardTransaction;
import by.bsuir.eeb.rsoicoursework.service.CardManagementService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CardManagementServiceImpl implements CardManagementService {

    @Autowired
    private CardDAO cardDAO;

    @Autowired
    private TransactionDAO transactionDAO;

    @Override
    public CardTransaction executeBalanceOperation(CardTransaction transaction) throws NotEnoughMoneyException {
        double balance = calculateCardBalance(transaction.getCard().getId());
        if (transaction.getDiff() < 0 && balance < Math.abs(transaction.getDiff())) {
            throw new NotEnoughMoneyException();
        }
        transaction.setDate(new Date());
        return transactionDAO.save(transaction);
    }

    @Override
    public Card getCardById(long cardId) {
        return cardDAO.getOne(cardId);
    }

    @Override
    public List<Card> getCardsByUserId(long userId) {
        return cardDAO.getAllByUserId(userId);
    }

    @Override
    public Double calculateCardBalance(long cardId) {
        return transactionDAO.getAllByCardId(cardId).stream()
                .map(CardTransaction::getDiff)
                .reduce((diff1, diff2) -> diff1 + diff2)
                .orElse(0.00);
    }

    @Override
    public Card save(Card card) {
        String cardNumber;
        while (true) {
            cardNumber = RandomStringUtils.randomNumeric(16);
            if (cardDAO.findByCardNumber(cardNumber) == null) break;
        }
        card.setCsv(RandomStringUtils.randomNumeric(3));
        card.setCardNumber(cardNumber);
        return cardDAO.save(card);
    }

    @Override
    public long getUserIdByCardId(long cardId) {
        return getCardById(cardId).getUser().getId();
    }

    @Override
    public List<CardTransaction> getAllTransactions(long cardId) {
        return transactionDAO.getAllByCardId(cardId);
    }

    @Override
    public CardTransaction getTransaction(long transactionId) {
        return transactionDAO.getOne(transactionId);
    }
}
