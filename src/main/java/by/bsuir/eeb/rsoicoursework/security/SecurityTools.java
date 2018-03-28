package by.bsuir.eeb.rsoicoursework.security;

import by.bsuir.eeb.rsoicoursework.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
@PropertySource("classpath:application.properties")
public class SecurityTools {

    @Value("jwt.clientId")
    private String clientId;

    @Value("jwt.secret")
    private String secret;

    public String buildJwtToken(User credentials) {
        return Jwts.builder()
                .setSubject(credentials.getEmail())
                .setIssuer(clientId)
                .claim("roles", "user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 4320000L))
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encode(secret.getBytes()))
                .compact();
    }
}
