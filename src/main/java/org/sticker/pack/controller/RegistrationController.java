package org.sticker.pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.sticker.pack.controller.dto.RegistrationFormDTO;
import org.sticker.pack.model.AuthType;
import org.sticker.pack.model.Customer;
import org.sticker.pack.service.CustomerService;


@Controller
public class RegistrationController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/registration")
    public String registrationPage() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute RegistrationFormDTO registrationForm) {
        customerService.create(convert(registrationForm));
        return "redirect:/login";
    }

    private Customer convert(RegistrationFormDTO registrationForm) {
        Customer customer = new Customer();
        customer.setEmail(registrationForm.getEmail());
        customer.setPassword(registrationForm.getPassword());
        customer.setFirstName(registrationForm.getFirstName());
        customer.setLastName(registrationForm.getLastName());
        customer.setAuthType(AuthType.DEFAULT);
        return customer;
    }
}
