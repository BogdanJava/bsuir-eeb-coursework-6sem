package by.bsuir.eeb.rsoicoursework.aspect;

import by.bsuir.eeb.rsoicoursework.exceptions.NotEnoughMoneyException;
import by.bsuir.eeb.rsoicoursework.model.Card;
import by.bsuir.eeb.rsoicoursework.model.CardTransaction;
import by.bsuir.eeb.rsoicoursework.service.CardManagementService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
public class FirstTransactionAspect {

    @Autowired
    private CardManagementService cardManagementService;

    @After("execution(* by.bsuir.eeb.rsoicoursework.service.CardManagementService.save(..)) && args(card)")
    public void addFirstTransaction(Card card) throws NotEnoughMoneyException {
        if (card != null) {
            CardTransaction cardTransaction = new CardTransaction();
            cardTransaction.setCard(card);
            cardTransaction.setDiff(1000D);
            cardTransaction.setName("Init transaction");
            cardTransaction.setDescription("Initial transaction after creating a new card. " +
                    "You have start balance 1000" + card.getCurrency());
            cardManagementService.executeBalanceOperation(cardTransaction);
        }
    }
}
