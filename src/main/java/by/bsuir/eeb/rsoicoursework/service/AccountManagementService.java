package by.bsuir.eeb.rsoicoursework.service;

import by.bsuir.eeb.rsoicoursework.model.Account;
import by.bsuir.eeb.rsoicoursework.model.AccountTransaction;

import java.util.List;

public interface AccountManagementService {
    List<Account> getAllUserAccounts(long userId);
    void createAccount(Account account);
    void closeAccount(long id);
    void makePaymentForCredit(AccountTransaction accountTransaction);
    double getAccountBalance(long accountId);
}
