package org.sticker.pack.aspect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.sticker.pack.aspect.TestExample;

/**
 * @author mmazurke <Mikhail.Mazurkevich@t-systems.com>
 */
@Controller
public class RepeaterController {

    @Autowired
    private TestExample testExample;

    @GetMapping("/pt")
    private ResponseEntity<HttpStatus> getPaymentPage() {
        System.out.println(testExample.testRepeater());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
