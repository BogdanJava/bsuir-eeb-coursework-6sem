package by.bsuir.eeb.rsoicoursework.controller.secured;

import by.bsuir.eeb.rsoicoursework.model.Account;
import by.bsuir.eeb.rsoicoursework.model.enums.AccountType;
import by.bsuir.eeb.rsoicoursework.security.ResourceAccessResolver;
import by.bsuir.eeb.rsoicoursework.service.AccountManagementService;
import by.bsuir.eeb.rsoicoursework.service.CardManagementService;
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
    private CardManagementService cardManagementService;

    @Autowired
    private ResourceAccessResolver accessResolver;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createAccount(@RequestBody Account account) {
        account.setUser(cardManagementService.getUserByCardId(account.getCard().getId()));
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
        return ResponseEntity.ok(ImmutableMap.of("balance", accountService.getAccountBalance(accountId)));
    }
}
