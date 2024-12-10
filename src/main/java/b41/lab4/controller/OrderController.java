package b41.lab4.controller;

import b41.lab4.data.Order;
import b41.lab4.data.Ticket;
import b41.lab4.data.Viewer;
import b41.lab4.exception.ResourceNotFoundException;
import b41.lab4.repository.OrderRepository;
import b41.lab4.repository.TicketRepository;
import b41.lab4.repository.ViewerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin(origins = {"http://frontend:8081", "http://localhost:8081"})
public class OrderController {
    private final OrderRepository orderRepository;
    private final TicketRepository ticketRepository;
    private final ViewerRepository viewerRepository;

    public OrderController(OrderRepository orderRepository, TicketRepository ticketRepository, ViewerRepository viewerRepository) {
        this.orderRepository = orderRepository;
        this.ticketRepository = ticketRepository;
        this.viewerRepository = viewerRepository;
    }

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Order>> filterOrders(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        List<Order> orders = orderRepository.findByOrderDateBetween(startDate, endDate);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        Viewer viewer = viewerRepository.findById(order.getViewer().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Viewer not found"));

        Ticket ticket = ticketRepository.findById(order.getTicket().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found"));

        order.setViewer(viewer);
        order.setTicket(ticket);

        Order savedOrder;
        try {
            savedOrder = orderRepository.save(order);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order updatedOrder) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.setViewer(updatedOrder.getViewer());
        order.setTicket(updatedOrder.getTicket());
        order.setOrderDate(updatedOrder.getOrderDate());
        Order savedOrder;
        try {
            savedOrder = orderRepository.save(order);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(savedOrder, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order not found");
        }
        orderRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
