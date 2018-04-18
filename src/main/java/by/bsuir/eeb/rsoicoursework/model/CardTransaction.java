package by.bsuir.eeb.rsoicoursework.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "card_transaction")
public class CardTransaction {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private double diff;

    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;
}
