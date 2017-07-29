package org.sticker.pack.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.sticker.pack.model.Customer;
import org.sticker.pack.model.Order;
import org.sticker.pack.model.OrderItem;
import org.sticker.pack.model.OrderStatus;
import org.sticker.pack.model.Sticker;
import org.sticker.pack.repository.CustomerRepository;
import org.sticker.pack.repository.OrderItemRepository;
import org.sticker.pack.repository.OrderRepository;
import org.sticker.pack.repository.StickerRepository;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Mikhail on 23.07.2017.
 */
@Component
public class OrderService {

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private StickerRepository stickerRepository;

    private static final int MIN_ORDER_NUMBER = 1000;
    private static final int MAX_ORDER_NUMBER = 9999;

    public void addItemToShopcart(String email, String... stickerNumbers) {
        Customer customer = customerRepository.findFirstByEmail(email);
        Order order = customer.getOrders()
                .stream()
                .filter(it -> it.getOrderStatus().equals(OrderStatus.PENDING))
                .findFirst()
                .orElse(getEmptyOrder(customer));
        orderRepository.save(order);

        Arrays.asList(stickerNumbers).forEach(stickerNumber -> {
            Sticker sticker = stickerRepository.findFirstByUuid(stickerNumber);
            if (order.getOrderItems() != null) {
                Optional<OrderItem> optional = order.getOrderItems()
                        .stream()
                        .filter(orderItem -> orderItem.getSticker().getName().equals(sticker.getName()))
                        .findFirst();
                if (optional.isPresent()) {
                    OrderItem orderItem = optional.get();
                    orderItem.setItemsCount(orderItem.getItemsCount() + 1);
                    orderItemRepository.save(orderItem);
                    return;
                }
            }
            orderItemRepository.save(getOrderItem(sticker, order));
        });
    }

    public void removeItemFromShopcart(String email, String stickerNumber) {
        Customer customer = customerRepository.findFirstByEmail(email);
        Order order = customer.getOrders()
                .stream()
                .filter(it -> it.getOrderStatus().equals(OrderStatus.PENDING))
                .findFirst().get();
        OrderItem item = order.getOrderItems()
                .stream()
                .filter(orderItem -> orderItem.getSticker().getUuid().equals(stickerNumber))
                .findFirst().get();
        if (item.getItemsCount() > 1) {
            item.setItemsCount(item.getItemsCount() - 1);
            orderItemRepository.save(item);
        } else {
            orderItemRepository.delete(item.getUuid());
        }
        //TODO:: check order items count after remove element 
        if (order.getOrderItems().size() == 1)
            orderRepository.delete(order.getUuid());
    }

    private OrderItem getOrderItem(Sticker sticker, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItemsCount(1);
        orderItem.setSticker(sticker);
        orderItem.setOrder(order);
        return orderItem;
    }

    private Order getEmptyOrder(Customer customer) {
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderNumber(ThreadLocalRandom.current().nextInt(MIN_ORDER_NUMBER, MAX_ORDER_NUMBER + 1));
        order.setOrderStatus(OrderStatus.PENDING);
        return order;
    }
}
