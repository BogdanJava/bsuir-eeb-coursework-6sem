package by.bsuir.eeb.rsoicoursework.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Bogdan Shishkin
 * project: rsoi-coursework
 * date/time: 02.05.2018 / 17:07
 * email: bogdanshishkin1998@gmail.com
 */

@Data
@Entity
@Table(name = "account_transaction")
public class AccountTransaction {

    @Id
    @GeneratedValue
    private long id;

    @Temporal(TemporalType.DATE)
    private Date date;
    private double diff;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;
}
