package by.bsuir.eeb.rsoicoursework.model;

import by.bsuir.eeb.rsoicoursework.model.enums.AccountStatus;
import by.bsuir.eeb.rsoicoursework.model.enums.AccountType;
import by.bsuir.eeb.rsoicoursework.model.enums.Currency;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

/**
 * @author Bogdan Shishkin
 * project: rsoi-coursework
 * date/time: 18.03.2018 / 3:56
 * email: bogdanshishkin1998@gmail.com
 */

@Data
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue
    @Column
    private long id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;

    @Column(name = "recount_period")
    private int sumRecountPeriod;

    @Column(name = "interest_rate")
    private double interestRate;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
}
