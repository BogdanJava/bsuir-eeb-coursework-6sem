package by.bsuir.eeb.rsoicoursework.service;

import by.bsuir.eeb.rsoicoursework.model.Account;
import by.bsuir.eeb.rsoicoursework.model.AccountTransaction;
import by.bsuir.eeb.rsoicoursework.model.enums.AccountType;

import java.util.List;

public interface AccountManagementService {
    List<Account> getAllUserAccounts(long userId, AccountType accountType);
    void createAccount(Account account);
    void closeAccount(long id);
    double getAccountBalance(long accountId);
    Account getById(long id);
    boolean payoffCredit(AccountTransaction accountTransaction);
}
