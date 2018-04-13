package by.bsuir.eeb.rsoicoursework.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

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
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
