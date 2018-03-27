package by.bsuir.eeb.rsoicoursework.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import java.util.Collections;

@Configuration
@EnableOAuth2Client
public class OAuthConfig {

    @Autowired
    private Environment env;

    @Bean
    public OAuth2RestOperations restTemplate(OAuth2ClientContext oAuth2ClientContext) {
        return new OAuth2RestTemplate(resource(), oAuth2ClientContext);
    }

    private OAuth2ProtectedResourceDetails resource() {
        AuthorizationCodeResourceDetails resource = new AuthorizationCodeResourceDetails();
        resource.setClientId(env.getProperty("config.oauth2.clientID"));
        resource.setClientSecret(env.getProperty("config.oauth2.clientSecret"));
        resource.setAccessTokenUri(env.getProperty("config.oauth2.accessTokenUri"));
        resource.setUserAuthorizationUri(env.getProperty("config.oauth2.userAuthorizationUri"));
        resource.setScope(Collections.singletonList("read"));
        return resource;
    }
}
