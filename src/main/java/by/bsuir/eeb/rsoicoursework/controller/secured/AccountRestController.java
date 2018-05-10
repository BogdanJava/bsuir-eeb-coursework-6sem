package by.bsuir.eeb.rsoicoursework.controller.secured;

import by.bsuir.eeb.rsoicoursework.model.Account;
import by.bsuir.eeb.rsoicoursework.model.AccountTransaction;
import by.bsuir.eeb.rsoicoursework.model.enums.AccountType;
import by.bsuir.eeb.rsoicoursework.security.ResourceAccessResolver;
import by.bsuir.eeb.rsoicoursework.security.config.UserContextHolder;
import by.bsuir.eeb.rsoicoursework.service.AccountManagementService;
import by.bsuir.eeb.rsoicoursework.service.UserService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountRestController {

    @Autowired
    private AccountManagementService accountService;

    @Autowired
    private UserService userService;

    @Autowired
    private ResourceAccessResolver accessResolver;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createAccount(@RequestBody Account account) {
        account.setUser(userService.findById(UserContextHolder.getUserId()));
        if (!accessResolver.checkUserSpecificResourceAccess(account.getUser().getId()))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        accountService.createAccount(account);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getAllUserAccounts(@RequestParam long userId,
                                             @RequestParam(value = "type", defaultValue = "ALL") AccountType accountType) {
        if (!accessResolver.checkUserSpecificResourceAccess(userId))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        return ResponseEntity.ok(accountService.getAllUserAccounts(userId, accountType));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/balance/{accountId}")
    public ResponseEntity getAccountBalance(@PathVariable long accountId) {
        if (!accessResolver.checkUserSpecificResourceAccess(accountService.getById(accountId).getUser().getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        double balance = accountService.getAccountBalance(accountId);
        // simplified interest rate growth
        double interestRate = balance * accountService.getById(accountId).getInterestRate() / 100;
        return ResponseEntity.ok(ImmutableMap.of("balance", balance,
                "interestRate", interestRate));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/payoff")
    public ResponseEntity payoffCredit(@RequestBody AccountTransaction accountTransaction) {
        if (!accessResolver.checkUserSpecificResourceAccess(accountTransaction.getAccount().getUser().getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        boolean success = accountService.payoffCredit(accountTransaction);
        return ResponseEntity.ok(ImmutableMap.of("success", success));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{accountId}")
    public ResponseEntity getAccountById(@PathVariable long accountId) {
        if (!accessResolver.checkUserSpecificResourceAccess(accountService.getById(accountId).getUser().getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(accountService.getById(accountId));
    }
}
