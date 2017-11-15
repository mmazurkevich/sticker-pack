package org.sticker.pack.aspect;

import org.springframework.stereotype.Service;

/**
 * @author mmazurke <Mikhail.Mazurkevich@t-systems.com>
 */
@Service
public class TestExample {

    @Repeater
    public boolean testRepeater() {
        System.out.println("Repeated element");
        return true;
    }
}
