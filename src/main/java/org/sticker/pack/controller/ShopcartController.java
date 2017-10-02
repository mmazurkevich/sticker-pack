package org.sticker.pack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.sticker.pack.controller.dto.ShopcartElementWrapper;
import org.sticker.pack.model.Sticker;
import org.sticker.pack.service.OrderService;
import org.sticker.pack.service.StickerService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.sticker.pack.controller.HttpUtilities.SHOPCART;

@Controller
public class ShopcartController {

    private static final String ANONYMOUS_USER = "anonymousUser";

    @Autowired
    private StickerService stickerService;
    @Autowired
    private OrderService orderService;

    @GetMapping("/shopcart")
    private String getShopcartItems(HttpSession session, Model model) {
        List<ShopcartElementWrapper> shopcartElements = new ArrayList();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            if (session.getAttribute(SHOPCART) != null)
                shopcartElements = convert(stickerService.getAllStickers((List<String>) session.getAttribute(SHOPCART)));
        } else {
            if (session.getAttribute(SHOPCART) != null) {
                List<String> shopcart = (List<String>)session.getAttribute(SHOPCART);
                orderService.addItemToShopcart(auth.getPrincipal().toString(), (String[])shopcart.toArray());
                session.setAttribute(SHOPCART, null);
            }
            shopcartElements = orderService.getItemsFromShopcart(auth.getPrincipal().toString())
                    .stream().map(orderItem -> {
                        ShopcartElementWrapper elementWrapper = new ShopcartElementWrapper();
                        elementWrapper.setUuid(orderItem.getSticker().getUuid());
                        elementWrapper.setName(orderItem.getSticker().getName());
                        elementWrapper.setCount(orderItem.getItemsCount());
                        elementWrapper.setPrice(orderItem.getItemsCount() * orderItem.getSticker().getPrice());
                        return elementWrapper;
                    }).collect(Collectors.toList());
        }
        float totalPrice = shopcartElements.stream()
                .map(ShopcartElementWrapper::getPrice)
                .reduce(0f, Float::sum);
        model.addAttribute("stickers", shopcartElements);
        model.addAttribute("totalPrice", totalPrice);
        return "shopcart";
    }

    @GetMapping("/shopcart/confirm")
    private String getShopcartConfirm(HttpSession session) {
        //TODO:: Проверить что не залогинен если залогинен то презаполнить поля заказа
        // иначе просто дать пользовтаелю заполнить их
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal().equals(ANONYMOUS_USER)) {
            return "redirect:/login";
        }
        return "redirect:/order";
    }

    @GetMapping("/shopcart/{item}/remove")
    private String removeShopcartItem(HttpSession session, @PathVariable("item") String item) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof AnonymousAuthenticationToken) {
            List<String> shopcart = (List<String>) session.getAttribute(SHOPCART);
            shopcart.remove(item);
            if (shopcart.size() == 0)
                session.setAttribute(SHOPCART, null);
            else
                session.setAttribute(SHOPCART, shopcart);
        } else {
            orderService.removeItemFromShopcart(auth.getPrincipal().toString(), item);
        }
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
            orderService.addItemToShopcart(auth.getPrincipal().toString(), item);
        }
        return "redirect:/sticker";
    }

    private List<ShopcartElementWrapper> convert(List<Sticker> stickers) {
        List<ShopcartElementWrapper> result = new ArrayList<>();
        stickers.stream().collect(
                Collectors.groupingBy(
                        Function.identity(), Collectors.counting()
                )
        ).forEach((sticker, count) -> {
            ShopcartElementWrapper element = new ShopcartElementWrapper();
            element.setUuid(sticker.getUuid());
            element.setName(sticker.getName());
            element.setCount(count.intValue());
            element.setPrice(sticker.getPrice() * count);
            result.add(element);
        });
         return result;
    }
}
