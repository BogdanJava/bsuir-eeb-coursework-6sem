package by.bsuir.eeb.rsoicoursework.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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

    @NotNull
    @Column
    private String number;

    @NotNull
    @Column
    private String givenBy;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
