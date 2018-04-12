package by.bsuir.eeb.rsoicoursework.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Bogdan Shishkin
 * project: rsoi-coursework
 * date/time: 12.04.2018 / 14:36
 * email: bogdanshishkin1998@gmail.com
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChangeData {
    private long id;
    private String oldPassword;
    private String newPassword;
}
