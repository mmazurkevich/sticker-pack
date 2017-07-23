package org.sticker.pack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sticker.pack.model.Customer;
import org.sticker.pack.model.Sticker;
import org.sticker.pack.repository.CustomerRepository;
import org.sticker.pack.repository.OrderItemRepository;
import org.sticker.pack.repository.StickerRepository;

/**
 * Created by Mikhail on 23.07.2017.
 */
@Service
public class OrderService {

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private StickerRepository stickerRepository;

    public void addItemToShopcart(String stickerNumber, String email) {
        Customer customer = customerRepository.findFirstByEmail(email);
        Sticker sticker = stickerRepository.findFirstByUuid(stickerNumber);

    }
}
