package org.sticker.pack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sticker.pack.model.Customer;
import org.sticker.pack.repository.CustomerRepository;

/**
 * Created by Mikhail on 01.07.2017.
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public void create(Customer customer) {
        customerRepository.save(customer);
    }
}
