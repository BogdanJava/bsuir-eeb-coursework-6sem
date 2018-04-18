package by.bsuir.eeb.rsoicoursework.model;

import by.bsuir.eeb.rsoicoursework.model.enums.Currency;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

/**
 * @author Bogdan Shishkin
 * project: rsoi-coursework
 * date/time: 18.03.2018 / 1:52
 * email: bogdanshishkin1998@gmail.com
 */

@Data
@Entity
@NoArgsConstructor
@Table(name = "card")
public class Card {

    @Id
    @Column
    @GeneratedValue
    private long id;

    @Column(name = "card_number")
    private String cardNumber;

    @Column
    private String csv;

    @Column
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "card")
    private Set<CardTransaction> cardTransactions;

}
