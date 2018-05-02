package by.bsuir.eeb.rsoicoursework.security.filter;

import by.bsuir.eeb.rsoicoursework.security.ResourceAccessResolver;
import by.bsuir.eeb.rsoicoursework.security.SecurityTools;
import by.bsuir.eeb.rsoicoursework.security.config.UserContextHolder;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtFilter extends GenericFilterBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtFilter.class);

    @Autowired
    private ResourceAccessResolver accessResolver;

    @Autowired
    private SecurityTools securityTools;

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        final HttpServletRequest request = (HttpServletRequest) servletRequest;

        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final String authHeader = request.getHeader("authorization");

        Cookie tokenCookie = null;
        if(request.getCookies() != null) {
            tokenCookie = Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals("access_token"))
                    .findFirst()
                    .orElse(null);
        }

        if (!accessResolver.isProtectedResource(request.getRequestURI())) {
            filterChain.doFilter(request, response);
        } else {
            if (request.getMethod().equalsIgnoreCase("options")) {
                response.setStatus(HttpServletResponse.SC_OK);
                filterChain.doFilter(request, response);
            } else {
                if ((authHeader == null || !authHeader.startsWith("Bearer ")) && tokenCookie == null) {
                    response.sendError(HttpStatus.FORBIDDEN.value(), "Authentication header is missing or invalid");
                } else {
                    try {
                        final Claims claims = securityTools.parseToken(authHeader != null ? authHeader.substring(7) : tokenCookie.getValue());
                        request.setAttribute("claims", claims);
                        UserContextHolder.setUserId((Integer) claims.get("id"));
                        filterChain.doFilter(request, response);
                    } catch (RuntimeException e) {
                        response.sendError(HttpStatus.FORBIDDEN.value(), "Invalid token or signature");
                    }
                }
            }
        }
    }
}
