package org.sticker.pack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.sticker.pack.model.OrderItem;

/**
 * Created by Mikhail on 23.07.2017.
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, String> {
}
