package by.bsuir.eeb.rsoicoursework.dao;

import by.bsuir.eeb.rsoicoursework.model.Account;
import by.bsuir.eeb.rsoicoursework.model.enums.AccountStatus;
import by.bsuir.eeb.rsoicoursework.model.enums.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountDAO extends JpaRepository<Account, Long> {
    List<Account> getAllByUserId(long userId);
    List<Account> getAllByAccountType(AccountType accountType);
    List<Account> getAllByAccountStatus(AccountStatus accountStatus);
}
