package org.sticker.pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.sticker.pack.model.Sticker;
import org.sticker.pack.service.OrderService;
import org.sticker.pack.service.StickerService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ShopcartController {

    private static final String SHOPCART = "SHOPCART";
    private static final String ANONYMOUS_USER = "anonymousUser";

    @Autowired
    private StickerService stickerService;
    @Autowired
    private OrderService shopcartService;

    @GetMapping("/shopcart")
    private String getShopcartItems(HttpSession session, Model model) {
        List<Sticker> stickers;
        if (session.getAttribute(SHOPCART) != null)
            stickers = stickerService.getAllStickers((List<String>) session.getAttribute(SHOPCART));
        else
            stickers = new ArrayList<>();
        float totalPrice = stickers.stream().map(Sticker::getPrice).reduce(0f, Float::sum);
        model.addAttribute("stickers", stickers);
        model.addAttribute("totalPrice", totalPrice);
        return "shopcart";
    }

    @GetMapping("/shopcart/confirm")
    private String getShopcartConfirm(HttpSession session) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal().equals(ANONYMOUS_USER)) {
            return "redirect:/login";
        }
        return "redirect:/order";
    }

    @GetMapping("/shopcart/{item}/remove")
    private String removeShopcartItem(HttpSession session, @PathVariable("item") String item) {
        List<String> shopcart = (List<String>) session.getAttribute(SHOPCART);
        shopcart.remove(item);
        session.setAttribute(SHOPCART, shopcart);
        return "redirect:/shopcart";
    }

    @GetMapping("/shopcart/{item}/add")
    private String addShopcartItem(HttpSession session, @PathVariable("item") String item) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            if (session.getAttribute(SHOPCART) != null) {
                List<String> shopcart = (List<String>)session.getAttribute(SHOPCART);
                shopcart.add(item);
                session.setAttribute(SHOPCART, shopcart);
            } else {
                List<String> shopcart = new ArrayList<>();
                shopcart.add(item);
                session.setAttribute(SHOPCART, shopcart);
            }
        } else {
            shopcartService.addItemToShopcart(item, auth.getPrincipal().toString());
        }
        return "redirect:/sticker";
    }
}
