package org.sticker.pack.model.converter;

import org.sticker.pack.model.OrderStatus;
import javax.persistence.AttributeConverter;
import javax.persistence.Convert;

import static org.sticker.pack.model.OrderStatus.*;

@Convert
public class OrderStatusConverter implements AttributeConverter<OrderStatus, String> {
    @Override
    public String convertToDatabaseColumn(OrderStatus orderStatus) {
        switch (orderStatus) {
            case DELIVERED:
                return "delivered";
            case PAYED:
                return "payed";
            default:
                throw new IllegalArgumentException("Unknown" + orderStatus);
        }
    }

    @Override
    public OrderStatus convertToEntityAttribute(String status) {
        switch (status) {
            case "delivered":
                return DELIVERED;
            case "payed":
                return PAYED;
            default:
                throw new IllegalArgumentException("Unknown" + status);
        }
    }
}
