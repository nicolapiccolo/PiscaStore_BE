package it.unito.order_service.service;

import it.unito.order_service.messaging.CreatedOrder;

import java.io.IOException;
import java.net.URI;

public interface PaymentService {

    CreatedOrder createOrder(Double totalAmount, URI returnUrl) throws IOException;

    String captureOrder(String orderId) throws IOException;
}
