package by.bsuir.eeb.rsoicoursework.service.impl;

import by.bsuir.eeb.rsoicoursework.dao.AccountDAO;
import by.bsuir.eeb.rsoicoursework.dao.AccountTransactionDAO;
import by.bsuir.eeb.rsoicoursework.exceptions.AccountActionException;
import by.bsuir.eeb.rsoicoursework.model.Account;
import by.bsuir.eeb.rsoicoursework.model.AccountTransaction;
import by.bsuir.eeb.rsoicoursework.model.enums.AccountStatus;
import by.bsuir.eeb.rsoicoursework.model.enums.AccountType;
import by.bsuir.eeb.rsoicoursework.model.enums.Currency;
import by.bsuir.eeb.rsoicoursework.service.AccountManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class AccountManagementServiceImpl implements AccountManagementService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountManagementServiceImpl.class);

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private AccountTransactionDAO accountTransactionDAO;

    @Override
    public List<Account> getAllUserAccounts(long userId, AccountType accountType) {
        if(accountType.equals(AccountType.ALL)) return accountDAO.getAllByUserId(userId);
        return accountDAO.getAllByUserIdAndAccountType(userId, accountType);
    }

    @Override
    public void createAccount(Account account) {
        account.setAccountStatus(AccountStatus.OPEN);
        account.setInterestRate(account.getCurrency() == Currency.BYN ? 10 : 3);
        accountDAO.save(account);
    }

    @Override
    public void closeAccount(long id) {
        Account account = accountDAO.getOne(id);
        account.setAccountStatus(AccountStatus.CLOSED);
        account.setCloseDate(new Date());
    }

    @Override
    public void makePaymentForCredit(AccountTransaction accountTransaction) {
        if (accountTransaction.getDiff() < 0) throw new AccountActionException("Sum can't be negative value");
        accountTransactionDAO.save(accountTransaction);
    }

    @Override
    public double getAccountBalance(long accountId) {
        Account account = accountDAO.getOne(accountId);
        return account.getAccountTransactions()
                .stream()
                .map(AccountTransaction::getDiff)
                .reduce((a, b) -> a + b)
                .orElse(-1D);
    }

    @Override
    public Account getById(long id) {
        return accountDAO.getOne(id);
    }


}
