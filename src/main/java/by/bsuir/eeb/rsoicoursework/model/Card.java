package by.bsuir.eeb.rsoicoursework.model;

import by.bsuir.eeb.rsoicoursework.model.enums.CardType;
import by.bsuir.eeb.rsoicoursework.model.enums.Currency;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
@ToString(exclude = {"cardTransactions", "user"})
@Table(name = "card")
public class Card {

    @Id
    @GeneratedValue
    private long id;

    private String password;

    @Column(name = "card_number")
    private String cardNumber;

    private String csv;

    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "card")
    private Set<CardTransaction> cardTransactions;

}
