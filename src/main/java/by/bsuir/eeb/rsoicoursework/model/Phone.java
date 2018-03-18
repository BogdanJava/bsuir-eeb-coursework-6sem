package by.bsuir.eeb.rsoicoursework.model;

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

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "operator_code")
    private String operatorCode;

    @Column(name = "number")
    private String number;
}
