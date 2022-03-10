package it.unito.order_service.controller;

import it.unito.order_service.entity.Bag;
import it.unito.order_service.entity.Item;
import it.unito.order_service.messaging.CreatedOrder;
import it.unito.order_service.messaging.OrderUser;
import it.unito.order_service.messaging.ResponseMessage;
import it.unito.order_service.repository.OrderItemRepository;
import it.unito.order_service.repository.OrderRepository;
import it.unito.order_service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("api/v1/bag")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository itemRepository;


    private String orderId = "";

    private final PaymentService paymentService;

    public OrderController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderUser> findById(@PathVariable Long id){

        Optional<Bag> bag_opt =  orderRepository.findById(id);
        if(bag_opt.isPresent()){
            Bag bag = bag_opt.get();
            OrderUser order = new OrderUser(bag.getIdUser(),bag.getIdAddress(),bag.getItems());
            return new ResponseEntity(order,HttpStatus.OK);
            //itemRepository.findByBag(bag);
        }
        else return new ResponseEntity(new OrderUser(),HttpStatus.NOT_FOUND);
    }

    @GetMapping("user/{id}")
    public ResponseEntity<List<OrderUser>> findByIdUser(@PathVariable Long id){

        Optional<List<Bag>> bag_opt = orderRepository.findByIdUser(id);
        if(bag_opt.isPresent()){
            List<Bag> bags = bag_opt.get();
            List<OrderUser> orders = new ArrayList<OrderUser>();

            for(Bag bag: bags){
                orders.add(new OrderUser(bag.getIdUser(),bag.getIdAddress(),bag.getItems()));
            }

            return new ResponseEntity(orders,HttpStatus.OK);
        }
        else return new ResponseEntity(new ArrayList<OrderUser>(),HttpStatus.NOT_FOUND);


    }

    @PostMapping("/create")
    public ResponseEntity<ResponseMessage> create(@RequestBody OrderUser order){


        //order.getIdUser();

        Bag bag = orderRepository.save(new Bag(order.getIdUser(),order.getIdAddress()));

        Set<Item> items = order.getItems();

        float totalPrice = 0;

        for(Item i : items){
            i.setBag(bag);
            totalPrice += i.getPrice();
            itemRepository.save(i);
        }

        System.out.println("total: " + totalPrice);

        bag.setItems(items);
        bag.setPrice(totalPrice);

        orderRepository.save(bag);

        return new ResponseEntity(new ResponseMessage(String.valueOf(bag.getId())),HttpStatus.OK);
    }


    @GetMapping("/capture")
    public String captureOrder(@RequestParam String token) throws IOException {
        orderId = token;
        return paymentService.captureOrder(token);
    }

    @PostMapping("/payment")
    public URI placeOrder(@RequestParam Double totalAmount, HttpServletRequest request) throws IOException {
        final URI returnUrl = buildReturnUrl(request);
        CreatedOrder createdOrder = null;

        createdOrder = paymentService.createOrder(totalAmount, returnUrl);
        return createdOrder.getApprovalLink();


    }

    private URI buildReturnUrl(HttpServletRequest request) {
        try {
            URI requestUri = URI.create(request.getRequestURL().toString());
            return new URI("http://localhost:4200/cart");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }


}
