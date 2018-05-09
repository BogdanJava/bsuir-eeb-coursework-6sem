package by.bsuir.eeb.rsoicoursework.aspect;

import by.bsuir.eeb.rsoicoursework.dao.AccountTransactionDAO;
import by.bsuir.eeb.rsoicoursework.exceptions.NotEnoughMoneyException;
import by.bsuir.eeb.rsoicoursework.model.Account;
import by.bsuir.eeb.rsoicoursework.model.AccountTransaction;
import by.bsuir.eeb.rsoicoursework.model.Card;
import by.bsuir.eeb.rsoicoursework.model.CardTransaction;
import by.bsuir.eeb.rsoicoursework.model.enums.AccountType;
import by.bsuir.eeb.rsoicoursework.model.enums.TransactionType;
import by.bsuir.eeb.rsoicoursework.service.CardManagementService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.Date;

@Aspect
@Transactional
public class FirstTransactionAspect {

    @Autowired
    private CardManagementService cardManagementService;

    @Autowired
    private AccountTransactionDAO accountTransactionDAO;

    @After("execution(* by.bsuir.eeb.rsoicoursework.service.CardManagementService.save(..)) && args(card)")
    public void addFirstCardTransaction(Card card) throws NotEnoughMoneyException {
        if (card != null) {
            CardTransaction cardTransaction = new CardTransaction();
            cardTransaction.setCard(card);
            cardTransaction.setDiff(1000D);
            cardTransaction.setName("Init transaction");
            cardTransaction.setDescription("Initial transaction after creating a new card. " +
                    "You have start balance 1000" + card.getCurrency());
            cardManagementService.executeTransaction(cardTransaction);
        }
    }

    @After("execution(* by.bsuir.eeb.rsoicoursework.service.AccountManagementService.createAccount(..)) && args(account)")
    public void addFirstAccountTransaction(Account account) {
        if (account != null) {
            AccountTransaction initTransaction = new AccountTransaction();
            initTransaction.setDiff(account.getStartSum());
            initTransaction.setAccount(account);
            initTransaction.setDate(new Date());
            accountTransactionDAO.save(initTransaction);
            if (account.getAccountType().equals(AccountType.DEPOSIT)) {
                CardTransaction cardTransaction = new CardTransaction();
                cardTransaction.setCard(account.getCard());
                cardTransaction.setName("Account payment");
                cardTransaction.setDate(initTransaction.getDate());
                cardTransaction.setDiff(-account.getStartSum());
                cardTransaction.setTransactionType(TransactionType.OTHER);
                cardTransaction.setDescription("Payment for an account operation");
                cardManagementService.executeTransaction(cardTransaction);
            }
        }
    }
}
