package org.sticker.pack;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Mikhail on 23.05.2017.
 */
@Controller
public class AuthorizationController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
