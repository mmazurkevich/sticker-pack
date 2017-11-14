package org.sticker.pack.aspect;

import org.springframework.stereotype.Service;

/**
 * @author mmazurke <Mikhail.Mazurkevich@t-systems.com>
 */
@Service
public class TestExampleImpl implements TestExample{

    @Repeater
    public void testRepeater() {
        System.out.println("Repeated element");
    }
}
