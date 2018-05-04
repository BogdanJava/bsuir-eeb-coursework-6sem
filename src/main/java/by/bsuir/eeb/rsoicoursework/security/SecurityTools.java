package by.bsuir.eeb.rsoicoursework.security;

import by.bsuir.eeb.rsoicoursework.model.User;
import by.bsuir.eeb.rsoicoursework.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
@PropertySource("classpath:application.properties")
public class SecurityTools {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${server.issuer}")
    private String issuer;

    @Autowired
    private UserService userService;

    public String buildJwtToken(User credentials) {
        return Jwts.builder()
                .claim("id", credentials.getId())
                .setSubject(credentials.getEmail())
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + 1000 * 3600 * 240))
                .signWith(SignatureAlgorithm.HS256, Base64.getEncoder().encodeToString(secret.getBytes()))
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(Base64.getEncoder().encodeToString(secret.getBytes()))
                .parseClaimsJws(token).getBody();
    }

    public User getUserFromClaims(Claims claims) {
        return userService.findByEmail(claims.getSubject());
    }
}
