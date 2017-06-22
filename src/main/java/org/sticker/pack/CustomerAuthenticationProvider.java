package org.sticker.pack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.sticker.pack.model.Customer;
import org.sticker.pack.repository.CustomerRepository;

import java.util.ArrayList;
import java.util.List;

import static org.sticker.pack.SecurityConfig.ROLE_ADMIN;
import static org.sticker.pack.SecurityConfig.ROLE_CUSTOMER;

/**
 * Created by Mikhail on 22.06.2017.
 */
@Component
public class CustomerAuthenticationProvider implements AuthenticationProvider{

    @Autowired
    private CustomerRepository customerRepository;
    private static final String STICKER_ADMIN = "sticker@admin";
    private static final String ADMIN_PASSWORD = "1234";

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication.getPrincipal() != null && authentication.getCredentials()!= null) {
            String email = authentication.getPrincipal().toString();
            String password = authentication.getCredentials().toString();
            if (email.equals(STICKER_ADMIN) && password.equals(ADMIN_PASSWORD)) {
                List<GrantedAuthority> grantedAuths = new ArrayList<>();
                grantedAuths.add(new SimpleGrantedAuthority(ROLE_ADMIN));
                authentication = new UsernamePasswordAuthenticationToken(email, password, grantedAuths);
                return authentication;
            } else {
                Customer customer = customerRepository.findFirstByEmailAndPassword(email, password);
                if (customer != null) {
                    List<GrantedAuthority> grantedAuths = new ArrayList<>();
                    grantedAuths.add(new SimpleGrantedAuthority(ROLE_CUSTOMER));
                    authentication = new UsernamePasswordAuthenticationToken(email, password, grantedAuths);
                    return authentication;
                }
                return null;
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
