package org.sticker.pack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sticker.pack.model.Customer;
import org.sticker.pack.repository.CustomerRepository;

/**
 * Created by Mikhail on 21.06.2017.
 */
@Service
public class RegistrationService {

    @Autowired
    private CustomerRepository customerRepository;

    public void registrateUser(Customer user) {
        customerRepository.save(user);
    }
}
