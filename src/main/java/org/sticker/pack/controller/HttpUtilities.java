package org.sticker.pack.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by Mikhail on 29.07.2017.
 */
@Component
public class HttpUtilities {

    public static final String SHOPCART = "SHOPCART";

    public HttpSession getHttpSession() {
        RequestAttributes requestAttributes = RequestContextHolder
                .currentRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = attributes.getRequest();
        return request.getSession(true);
    }
}
