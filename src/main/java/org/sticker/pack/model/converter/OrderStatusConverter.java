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
            case PENDING:
                return "pending";
            case AWAITING_PAYMENT:
                return "awaiting_payment";
            case AWAITING_SHIPMENT:
                return "awaiting_shipment";
            case PAYED:
                return "payed";
            case SHIPPED:
                return "shipped";
            case COMPLETED:
                return "completed";
            case CANCELLED:
                return "cancelled";
            case REFUNDED:
                return "refunded";
            default:
                throw new IllegalArgumentException("Unknown" + orderStatus);
        }
    }

    @Override
    public OrderStatus convertToEntityAttribute(String status) {
        switch (status) {
            case "pending":
                return PENDING;
            case "awaiting_payment":
                return AWAITING_PAYMENT;
            case "awaiting_shipment":
                return AWAITING_SHIPMENT;
            case "payed":
                return PAYED;
            case "shipped":
                return SHIPPED;
            case "completed":
                return COMPLETED;
            case "cancelled":
                return CANCELLED;
            case "refunded":
                return REFUNDED;
            default:
                throw new IllegalArgumentException("Unknown" + status);
        }
    }
}
