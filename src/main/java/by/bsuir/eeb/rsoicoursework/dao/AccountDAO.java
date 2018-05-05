package by.bsuir.eeb.rsoicoursework.dao;

import by.bsuir.eeb.rsoicoursework.model.Account;
import by.bsuir.eeb.rsoicoursework.model.enums.AccountStatus;
import by.bsuir.eeb.rsoicoursework.model.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountDAO extends JpaRepository<Account, Long> {
    List<Account> getAllByUserIdAndAccountType(long userId, AccountType accountType);
    List<Account> getAllByAccountStatus(AccountStatus accountStatus);
    List<Account> getAllByUserId(long userId);
}
