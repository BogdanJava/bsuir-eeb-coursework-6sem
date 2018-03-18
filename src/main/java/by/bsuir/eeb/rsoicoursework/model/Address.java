package by.bsuir.eeb.rsoicoursework.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author Bogdan Shishkin
 * project: rsoi-coursework
 * date/time: 18.03.2018 / 1:50
 * email: bogdanshishkin1998@gmail.com
 */

@Data
@Entity
@Table(name = "address")
public class Address {

    @Id
    @Column
    @GeneratedValue
    private long id;

    @Column
    private String country;

    @Column
    private String city;

    @Column
    private String street;

    @Column
    private String zip;

    @Column(name = "house_number")
    private String houseNumber;

    @Column(name = "flat_number")
    private String flatNumber;
}
