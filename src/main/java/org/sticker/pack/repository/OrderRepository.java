package org.sticker.pack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.sticker.pack.model.Order;

/**
 * Created by Mikhail on 18.06.2017.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, String>{
}
