package by.bsuir.eeb.rsoicoursework.model.dto;

import by.bsuir.eeb.rsoicoursework.model.Phone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneDTO {
    private Phone phone;
    private long userId;
}
