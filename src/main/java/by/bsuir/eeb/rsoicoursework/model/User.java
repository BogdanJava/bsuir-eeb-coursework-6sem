package by.bsuir.eeb.rsoicoursework.model;

import by.bsuir.eeb.rsoicoursework.model.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;
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
@NoArgsConstructor
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = {"email", "id"}))
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {

    public User(long id, String password) {
        this.password = password;
        this.id = id;
    }

    @Id
    @Column
    @GeneratedValue
    private long id;

    @NotNull
    @Past
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthday;

    @NotNull(message = "Incorrect email")
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Embedded
    private Address address;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Passport passport;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Card> cards;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Phone> phones;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Account> accounts;
}
