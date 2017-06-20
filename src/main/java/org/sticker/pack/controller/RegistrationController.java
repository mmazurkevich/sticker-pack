package org.sticker.pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.sticker.pack.controller.dto.RegistrationFormDTO;
import org.sticker.pack.model.User;
import org.sticker.pack.service.RegistrationService;


@Controller
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @GetMapping("/registration")
    public String registrationPage() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute RegistrationFormDTO registrationForm) {
        registrationService.registrateUser(convert(registrationForm));
        return "redirect:/login";
    }

    private User convert(RegistrationFormDTO registrationForm) {
        User user = new User();
        user.setEmail(registrationForm.getEmail());
        user.setPassword(registrationForm.getPassword());
        user.setFirstName(registrationForm.getFirstName());
        user.setLastName(registrationForm.getLastName());
        return user;
    }
}
