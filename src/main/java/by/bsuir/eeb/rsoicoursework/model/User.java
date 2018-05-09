package by.bsuir.eeb.rsoicoursework.model;

import by.bsuir.eeb.rsoicoursework.model.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
@EqualsAndHashCode(exclude = {"phones", "cards", "passport", "accounts"})
@ToString(exclude = {"phones", "cards", "passport", "accounts"})
public class User {

    public User(long id, String password) {
        this.password = password;
        this.id = id;
    }

    public User(long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    private long id;

    @Past
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthday;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Embedded
    private Address address;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Passport passport;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Card> cards;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Account> accounts;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Phone> phones;

    @Transient
    public String getFullName() {
        return String.join(" ", firstName, lastName);
    }
}
