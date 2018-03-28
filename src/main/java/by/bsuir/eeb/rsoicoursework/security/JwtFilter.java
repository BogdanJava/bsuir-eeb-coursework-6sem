package by.bsuir.eeb.rsoicoursework.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
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

@Component
public class JwtFilter extends GenericFilterBean {

    @Autowired
    private ResourceAccessResolver accessResolver;

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
                    response.sendError(HttpStatus.UNAUTHORIZED.value(), "Authentication header is missing or invalid");
                } else {
                    final String token = authHeader.substring(7);

                    try {
                        final Claims claims = Jwts.parser().setSigningKey("secretkey").parseClaimsJws(token).getBody();
                        request.setAttribute("claims", claims);
                    } catch (SignatureException | MalformedJwtException e) {
                        response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid token");
                    }
                    filterChain.doFilter(request, response);
                }
            }
        }
    }
}
