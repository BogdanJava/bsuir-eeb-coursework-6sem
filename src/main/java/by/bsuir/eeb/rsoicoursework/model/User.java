package by.bsuir.eeb.rsoicoursework.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Bogdan Shishkin
 * project: rsoi-coursework
 * date/time: 18.03.2018 / 1:41
 * email: bogdanshishkin1998@gmail.com
 */

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @Column
    @GeneratedValue
    private long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToOne
    private Address address;

    @OneToOne
    private Card card;

    @OneToOne
    private Phone phone;
}
