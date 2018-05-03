package by.bsuir.eeb.rsoicoursework.service.impl;

import by.bsuir.eeb.rsoicoursework.dao.AccountDAO;
import by.bsuir.eeb.rsoicoursework.exceptions.AccountActionException;
import by.bsuir.eeb.rsoicoursework.model.Account;
import by.bsuir.eeb.rsoicoursework.model.enums.AccountStatus;
import by.bsuir.eeb.rsoicoursework.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDAO accountDAO;

    @Override
    public List<Account> getAllUserAccounts(long userId) {
        return accountDAO.getAllByUserId(userId);
    }

    @Override
    public void createAccount(Account account) {
        accountDAO.save(account);
    }

    @Override
    public void closeAccount(Account account) {
        account = accountDAO.getOne(account.getId());
        account.setAccountStatus(AccountStatus.CLOSED);
    }

    @Override
    public void makePaymentForCredit(Account account, double sum) {
        if(sum < 0) throw new AccountActionException("Sum can't be negative value");

        account = accountDAO.getOne(account.getId());

    }
}
