package by.bsuir.eeb.rsoicoursework.dao;

import by.bsuir.eeb.rsoicoursework.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountDAO extends JpaRepository<Account, Long> {
}
