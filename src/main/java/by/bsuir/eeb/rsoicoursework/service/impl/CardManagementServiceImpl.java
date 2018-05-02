package by.bsuir.eeb.rsoicoursework.service.impl;

import by.bsuir.eeb.rsoicoursework.dao.CardDAO;
import by.bsuir.eeb.rsoicoursework.dao.TransactionDAO;
import by.bsuir.eeb.rsoicoursework.exceptions.NotEnoughMoneyException;
import by.bsuir.eeb.rsoicoursework.model.Card;
import by.bsuir.eeb.rsoicoursework.model.CardTransaction;
import by.bsuir.eeb.rsoicoursework.model.enums.TransactionType;
import by.bsuir.eeb.rsoicoursework.service.CardManagementService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CardManagementServiceImpl implements CardManagementService {

    @Autowired
    private CardDAO cardDAO;

    @Autowired
    private TransactionDAO transactionDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void executeBalanceOperation(CardTransaction transaction) throws NotEnoughMoneyException {
        double balance = calculateCardBalance(transaction.getCard().getId());
        if (transaction.getDiff() < 0 && balance < Math.abs(transaction.getDiff())) {
            throw new NotEnoughMoneyException();
        }
        transaction.setDate(new Date());
        if(transaction.getTransactionType() == null) transaction.setTransactionType(TransactionType.OTHER);
        if(transaction.getTransactionType().equals(TransactionType.TRANSFER)) {
            CardTransaction secondTransaction = new CardTransaction();
            secondTransaction.setDate(transaction.getDate());
            secondTransaction.setName(transaction.getName());
            secondTransaction.setTransactionType(TransactionType.TRANSFER);
            secondTransaction.setDescription("Received money from card #" + transaction.getCard().getCardNumber());
            secondTransaction.setDiff(-1 * transaction.getDiff());
            secondTransaction.setCard(cardDAO.findByCardNumber(transaction.getAdditionalInfo()));
            transactionDAO.save(secondTransaction);
        }
        transactionDAO.save(transaction);
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
        card.setPassword(passwordEncoder.encode(card.getPassword()));
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

    @Override
    public boolean isPasswordCorrect(long cardId, String password) {
        Card card = cardDAO.getOne(cardId);
        return passwordEncoder.matches(password, card.getPassword());
    }
}
