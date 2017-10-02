package org.sticker.pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.sticker.pack.controller.dto.OrderInfoWrapper;
import org.sticker.pack.model.Customer;
import org.sticker.pack.model.Order;
import org.sticker.pack.model.OrderStatus;
import org.sticker.pack.service.CustomerService;
import org.sticker.pack.service.OrderService;

import javax.servlet.http.HttpSession;

/**
 * Created by Mikhail on 13.08.2017.
 */
@Controller
public class OrderController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/order")
    private String getOrderPage(HttpSession session, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            return "redirect:/login";
        } else {
            Customer customer = customerService.find(auth.getPrincipal().toString());
            model.addAttribute("customer", customer);
            return "order";
        }
    }

    @PostMapping("/order")
    private String createOrder(@ModelAttribute OrderInfoWrapper orderInfo) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Customer customer = customerService.find(auth.getPrincipal().toString());
        Order order = customer.getOrders().stream().filter(it -> it.getOrderStatus().equals(OrderStatus.PENDING)).findFirst().get();
        order.setOrderStatus(OrderStatus.AWAITING_PAYMENT);
        order.setDeliveryAddress(orderInfo.getDeliveryAddress());
        order.setPostIndex(orderInfo.getPostIndex());
        order.setCountry(orderInfo.getCountry());
        order.setCity(orderInfo.getCity());
        orderService.updateOrder(order);
        return "order";
    }
}
