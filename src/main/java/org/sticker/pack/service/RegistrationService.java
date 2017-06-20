package org.sticker.pack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sticker.pack.model.User;
import org.sticker.pack.repository.UserRepository;

/**
 * Created by Mikhail on 21.06.2017.
 */
@Service
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;

    public void registrateUser(User user) {
        userRepository.save(user);
    }
}
