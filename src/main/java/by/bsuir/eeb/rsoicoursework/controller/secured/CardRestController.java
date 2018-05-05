package by.bsuir.eeb.rsoicoursework.controller.secured;

import by.bsuir.eeb.rsoicoursework.exceptions.NotEnoughMoneyException;
import by.bsuir.eeb.rsoicoursework.model.Card;
import by.bsuir.eeb.rsoicoursework.model.CardTransaction;
import by.bsuir.eeb.rsoicoursework.model.dto.CardDTO;
import by.bsuir.eeb.rsoicoursework.model.enums.Currency;
import by.bsuir.eeb.rsoicoursework.security.ResourceAccessResolver;
import by.bsuir.eeb.rsoicoursework.service.CardManagementService;
import by.bsuir.eeb.rsoicoursework.service.UserService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//todo replace all auth in methods with aspect advice @Before and throw some exception

@RestController
@RequestMapping("/api/cards")
public class CardRestController {

    @Autowired
    private CardManagementService cardManagementService;

    @Autowired
    private UserService userService;

    @Autowired
    private ResourceAccessResolver accessResolver;


    @RequestMapping(method = RequestMethod.GET, value = "/{cardId}")
    public ResponseEntity getCard(@PathVariable long cardId) {
        if (!accessResolver.checkUserSpecificResourceAccess(cardManagementService.getUserIdByCardId(cardId))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Card card = cardManagementService.getCardById(cardId);
        return card != null ? ResponseEntity.ok(card) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addCard(@RequestBody CardDTO cardDTO) {
        if (!accessResolver.checkUserSpecificResourceAccess(cardDTO.getUserId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        cardDTO.getCard().setUser(userService.findById(cardDTO.getUserId()));
        Card savedCard = cardManagementService.save(cardDTO.getCard());
        return savedCard != null ? ResponseEntity.ok(savedCard) : ResponseEntity.notFound().build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getAllCards(@RequestParam long userId) {
        if (!accessResolver.checkUserSpecificResourceAccess(userId))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        boolean exists = userService.exists(userId);
        return exists ? ResponseEntity.ok(cardManagementService.getCardsByUserId(userId)) : ResponseEntity.notFound().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/currency")
    public ResponseEntity getAllCardsByType( @RequestParam Currency currency, @RequestParam long userId) {
        if (!accessResolver.checkUserSpecificResourceAccess(userId))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        boolean exists = userService.exists(userId);
        return exists ? ResponseEntity.ok(cardManagementService.getCardsByUserIdAndCurrency(userId, currency)) :
                ResponseEntity.notFound().build();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/balance/{cardId}")
    public ResponseEntity getCardBalance(@PathVariable long cardId) {
        if (!accessResolver.checkUserSpecificResourceAccess(cardManagementService.getUserIdByCardId(cardId))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Double balance = cardManagementService.calculateCardBalance(cardId);
        return ResponseEntity.ok(ImmutableMap.of("balance", balance));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{cardId}/transactions")
    public ResponseEntity getAllTransactions(@PathVariable long cardId) {
        if (!accessResolver.checkUserSpecificResourceAccess(cardManagementService.getUserIdByCardId(cardId))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(cardManagementService.getAllTransactions(cardId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/transactions/{transactionId}")
    public ResponseEntity getTransactionById(@PathVariable long transactionId) {
        if (!accessResolver.checkUserSpecificResourceAccess(cardManagementService
                .getUserIdByCardId(cardManagementService.getTransaction(transactionId).getCard().getId()))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(cardManagementService.getTransaction(transactionId));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/transactions")
    public ResponseEntity saveTransaction(@RequestBody CardTransaction transaction) {
        try {
            this.cardManagementService.executeTransaction(transaction);
            return ResponseEntity.ok().build();
        } catch (NotEnoughMoneyException e) {
            return ResponseEntity.badRequest().body(ImmutableMap.of("error", e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{cardId}/isPasswordCorrect")
    public ResponseEntity isPasswordCorrect(@RequestParam String password, @PathVariable long cardId) {
        boolean passwordCorrect = this.cardManagementService.isPasswordCorrect(cardId, password);
        ImmutableMap<String, Boolean> result = ImmutableMap.of("passwordCorrect", passwordCorrect);
        return ResponseEntity.ok(result);
    }
}
