package org.sticker.pack.controller;

import com.braintreegateway.BraintreeGateway;
import com.braintreegateway.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Mikhail on 13.09.2017.
 */
@Controller
public class PaymentController {

    private static BraintreeGateway gateway = new BraintreeGateway(
            Environment.SANDBOX,
            "cjbt3dcnxcqs7bdv",
            "rgf7cwbrv8nvdnv9",
            "cc4edd55cda3db25b1fab76d6278d52f"
    );


    @GetMapping("/payment")
    private String getPaymentPage(Model model) {
        model.addAttribute("clientToken", gateway.clientToken().generate());
        return "dropin";
    }
}
