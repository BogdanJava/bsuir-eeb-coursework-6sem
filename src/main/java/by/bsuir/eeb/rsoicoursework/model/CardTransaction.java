package by.bsuir.eeb.rsoicoursework.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name = "card_transaction")
public class CardTransaction {

    @Id
    @GeneratedValue
    private long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    private String name;
    private String description;
    private double diff;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "card_id")
    private Card card;
}
