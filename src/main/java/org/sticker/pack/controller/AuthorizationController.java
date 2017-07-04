package org.sticker.pack.controller;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.sticker.pack.controller.dto.AuthenticationWrapper;
import org.sticker.pack.model.AuthType;
import org.sticker.pack.model.Customer;
import org.sticker.pack.service.CustomerService;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.sticker.pack.SecurityConfig.ROLE_CUSTOMER;

/**
 * Created by Mikhail on 23.05.2017.
 */
@Controller
public class AuthorizationController {

    @Value("client-id.google")
    private String googleClientId;
    private static final String REDIRECTING_URL = "/sticker";
    @Autowired
    private CustomerService customerService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/google-signin")
    public ResponseEntity<String> googleAuth(@RequestParam("token_id") String token) throws GeneralSecurityException, IOException {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier
                .Builder(new NetHttpTransport(), new JacksonFactory())
                .setAudience(Collections.singletonList(googleClientId))
                .build();

        GoogleIdToken idToken = verifier.verify(token);
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            Customer customer = new Customer();
            customer.setUuid(payload.getSubject());
            customer.setEmail(payload.getEmail());
            customer.setFirstName((String) payload.get("given_name"));
            customer.setLastName((String) payload.get("family_name"));
            customer.setUsername((String) payload.get("name"));
            customer.setAuthType(AuthType.GOOGLE);
            customerService.create(customer);
            List<GrantedAuthority> grantedAuths = new ArrayList<>();
            grantedAuths.add(new SimpleGrantedAuthority(ROLE_CUSTOMER));
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(payload.getEmail(),"", grantedAuths);
            authenticationToken.setDetails(AuthType.GOOGLE);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        return new ResponseEntity<>(REDIRECTING_URL, HttpStatus.OK);
    }

    @PostMapping(value = "/facebook-signin", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> facebookAuth(@RequestBody AuthenticationWrapper authData) {
        Customer customer = new Customer();
        customer.setUuid(authData.getId());
        customer.setEmail(authData.getEmail());
        customer.setUsername(authData.getName());
        customer.setFirstName(authData.getFirstName());
        customer.setLastName(authData.getLastName());
        customer.setMiddleName(authData.getMiddleName());
        customer.setAuthType(AuthType.FACEBOOK);
        customerService.create(customer);
        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        grantedAuths.add(new SimpleGrantedAuthority(ROLE_CUSTOMER));
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authData.getEmail(),"", grantedAuths);
        authenticationToken.setDetails(AuthType.FACEBOOK);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        return new ResponseEntity<>(REDIRECTING_URL, HttpStatus.OK);
    }

}
