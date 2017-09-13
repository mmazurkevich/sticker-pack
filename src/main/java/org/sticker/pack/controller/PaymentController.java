package org.sticker.pack.controller;

import com.braintreegateway.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.PrintStream;
import java.math.BigDecimal;

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

    @PostMapping("/checkout")
    private String getPaymentPage(@RequestBody String nonceFromTheClient) {
        TransactionRequest request = new TransactionRequest()
                .amount(new BigDecimal("10.00"))
                .paymentMethodNonce(nonceFromTheClient)
                .options()
                .submitForSettlement(true)
                .done();

        Result<Transaction> result = gateway.transaction().sale(request);

        if (!result.isSuccess()){
            ValidationErrors errors = result.getErrors();
            errors.getAllValidationErrors().forEach(System.out::println);
//            Transaction transaction = result.getTransaction();
//
//            System.out.println(transaction.getStatus());
//            System.out.println(transaction.getProcessorResponseCode());
//            System.out.println(transaction.getProcessorResponseText());
        }
        return "redirect:/payment";
    }
}
