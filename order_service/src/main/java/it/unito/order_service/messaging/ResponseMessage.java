package it.unito.order_service.messaging;

public class ResponseMessage {
    public String message;

    public ResponseMessage(String message){ this.message = message; }

    public String getMessage() {return message;}

    public void setMessage(String message){this.message = message;}
}
