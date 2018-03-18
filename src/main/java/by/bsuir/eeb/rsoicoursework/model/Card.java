package by.bsuir.eeb.rsoicoursework.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * @author Bogdan Shishkin
 * project: rsoi-coursework
 * date/time: 18.03.2018 / 1:52
 * email: bogdanshishkin1998@gmail.com
 */

@Data
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "card")
public class Card {

    @Id
    @Column
    @GeneratedValue
    private long id;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "csv")
    private String csv;
}
