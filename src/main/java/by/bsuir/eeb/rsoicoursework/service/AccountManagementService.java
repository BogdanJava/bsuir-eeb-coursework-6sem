package by.bsuir.eeb.rsoicoursework.service;

import by.bsuir.eeb.rsoicoursework.model.Account;

import java.util.List;

public interface AccountService {
    List<Account> getAllUserAccounts(long userId);
    void createAccount(Account account);
    void closeAccount(Account account);
    void makePaymentForCredit(Account account, double sum);
}
