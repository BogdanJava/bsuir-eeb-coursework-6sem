package by.bsuir.eeb.rsoicoursework.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Bogdan Shishkin
 * project: rsoi-coursework
 * date/time: 19.03.2018 / 18:04
 * email: bogdanshishkin1998@gmail.com
 */

@Data
@NoArgsConstructor
@Entity
@Table(name = "passport")
public class Passport {

    @Id
    @GeneratedValue
    @Column
    private long id;

    @Column
    private String number;

    @Column
    private String serial;

    @Column
    private String passportId;

    @OneToOne(mappedBy = "passport")
    private User user;
}
