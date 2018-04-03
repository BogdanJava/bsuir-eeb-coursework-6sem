package by.bsuir.eeb.rsoicoursework.http;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthToken {
    private String jwtToken;
}
