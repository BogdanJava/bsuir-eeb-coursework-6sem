package by.bsuir.eeb.rsoicoursework.security.filter;

import by.bsuir.eeb.rsoicoursework.security.ResourceAccessResolver;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

@Component
public class JwtFilter extends GenericFilterBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);

    @Autowired
    private ResourceAccessResolver accessResolver;

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) servletRequest;

        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final String authHeader = request.getHeader("authorization");

        if (accessResolver.isProtectedUrl(request.getRequestURI())) {
            filterChain.doFilter(request, response);
        } else {
            if (request.getMethod().equalsIgnoreCase("options")) {
                response.setStatus(HttpServletResponse.SC_OK);
                filterChain.doFilter(request, response);
            } else {
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    response.sendError(HttpStatus.FORBIDDEN.value(), "Authentication header is missing or invalid");
                } else {
                    final String token = authHeader.substring(7);
                    try {
                        final Claims claims = Jwts.parser()
                                .setSigningKey(Base64.getEncoder().encodeToString(secret.getBytes()))
                                .parseClaimsJws(token).getBody();
                        request.setAttribute("claims", claims);
                        filterChain.doFilter(request, response);
                    } catch (SignatureException | MalformedJwtException e) {
                        response.sendError(HttpStatus.FORBIDDEN.value(), "Invalid token or signature");
                    }
                }
            }
        }
    }
}
