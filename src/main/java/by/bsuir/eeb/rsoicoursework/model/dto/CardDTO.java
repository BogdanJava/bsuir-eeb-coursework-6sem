package by.bsuir.eeb.rsoicoursework.model.dto;

import by.bsuir.eeb.rsoicoursework.model.Card;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO {
    private Card card;
    private long userId;
}
