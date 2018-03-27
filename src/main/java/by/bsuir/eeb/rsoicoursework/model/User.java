package by.bsuir.eeb.rsoicoursework.model;

import by.bsuir.eeb.rsoicoursework.model.enums.Gender;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

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

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthday;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Embedded
    private Address address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passport_id")
    private Passport passport;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Card> cards;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Phone> phones;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Account> accounts;
}
