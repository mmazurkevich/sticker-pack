package org.sticker.pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.sticker.pack.model.Customer;
import org.sticker.pack.service.CustomerService;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Mikhail on 13.08.2017.
 */
@Controller
public class OrderController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/order")
    private String getOrderPage(HttpSession session, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            return "redirect:/";
        } else {
            Customer customer = customerService.find(auth.getPrincipal().toString());
            model.addAttribute("customer", customer);
            return "order";
        }
    }

    @PostMapping("/order")
    private String createOrder(HttpSession session, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        auth.getPrincipal().toString();
        return "order";
    }
}
