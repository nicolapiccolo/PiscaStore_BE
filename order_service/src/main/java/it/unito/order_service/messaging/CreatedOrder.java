package it.unito.order_service.messaging;

import java.net.URI;

public class CreatedOrder {
    private final String orderId;
    private final URI approvalLink;

    public CreatedOrder(String orderId, URI approvalLink) {
        this.orderId = orderId;
        this.approvalLink = approvalLink;
    }

    public String getOrderId() {
        return orderId;
    }

    public URI getApprovalLink() {
        return approvalLink;
    }
}