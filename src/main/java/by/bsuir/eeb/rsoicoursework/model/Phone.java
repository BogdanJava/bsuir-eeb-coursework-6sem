package by.bsuir.eeb.rsoicoursework.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author Bogdan Shishkin
 * project: rsoi-coursework
 * date/time: 18.03.2018 / 1:54
 * email: bogdanshishkin1998@gmail.com
 */

@Data
@Entity
@Table(name = "phone")
@ToString(exclude = "user")
public class Phone {

    @Id
    @Column
    @GeneratedValue
    private long id;

    @Column(name = "number")
    private String number;

    @Column
    private String type;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
}
