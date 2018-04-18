package by.bsuir.eeb.rsoicoursework.service.impl;

import by.bsuir.eeb.rsoicoursework.dao.CardDAO;
import by.bsuir.eeb.rsoicoursework.dao.TransactionDAO;
import by.bsuir.eeb.rsoicoursework.exceptions.NotEnoughMoneyException;
import by.bsuir.eeb.rsoicoursework.model.Card;
import by.bsuir.eeb.rsoicoursework.model.CardTransaction;
import by.bsuir.eeb.rsoicoursework.service.CardManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CardManagementServiceImpl implements CardManagementService {

    @Autowired
    private CardDAO cardDAO;

    @Autowired
    private TransactionDAO transactionDAO;

    @Override
    public CardTransaction balanceOperation(CardTransaction transaction) throws NotEnoughMoneyException {
        double balance = calculateCardBalance(transaction.getCard().getId());
        if (balance < transaction.getDiff()) {
            throw new NotEnoughMoneyException();
        }
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
        return transactionDAO.getAllByCardId(cardId)
                .stream()
                .map(CardTransaction::getDiff)
                .reduce((diff1, diff2) -> diff1 + diff2)
                .orElse(null);
    }

    @Override
    public Card save(Card card) {
        return cardDAO.save(card);
    }
}
