package org.sticker.pack.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import static org.sticker.pack.SecurityConfig.ROLE_ADMIN;

/**
 * Created by Mikhail on 23.06.2017.
 */
@Controller
public class IndexController {

    @GetMapping("/")
    public String getIndexPage() {
        boolean admin = SecurityContextHolder.getContext().getAuthentication()
                .getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(ROLE_ADMIN));
        return admin ? "redirect:/image" : "redirect:/sticker";
    }
}
