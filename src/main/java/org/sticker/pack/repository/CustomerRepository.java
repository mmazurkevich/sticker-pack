package org.sticker.pack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.sticker.pack.model.Customer;

/**
 * Created by Mikhail on 18.06.2017.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, String>{

    Customer findFirstByEmailAndPassword(String email, String password);
    Customer findFirstByEmail(String email);
    Customer findFirstByUuid(String uuid);

}

