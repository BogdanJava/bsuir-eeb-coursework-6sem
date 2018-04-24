package by.bsuir.eeb.rsoicoursework.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Bogdan Shishkin
 * project: rsoi-coursework
 * date/time: 18.03.2018 / 1:50
 * email: bogdanshishkin1998@gmail.com
 */

@Data
@NoArgsConstructor
@Embeddable
public class Address {

    private String country;

    private String city;

    private String street;

    private String zip;

    @Column(name = "house_number")
    private String houseNumber;

    @Column(name = "flat_number")
    private String flatNumber;
}
