package by.bsuir.eeb.rsoicoursework.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.TreeMap;

/**
 * @author Bogdan Shishkin
 * project: rsoi-coursework
 * date/time: 18.03.2018 / 3:56
 * email: bogdanshishkin1998@gmail.com
 *
 * ************ DEFENITION ************
 *
 * Class that represents user's account
 * todo: implement this class
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

}
