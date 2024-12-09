package b41.lab4.controller;

import b41.lab4.data.Order;
import b41.lab4.exception.ResourceNotFoundException;
import b41.lab4.repository.OrderRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

// @TODO: повертати ResponseEntity зі статус кодами та відповідним Order як результат усіх запитів
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/filter")
    public List<Order> filterOrders(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate) {
        return orderRepository.findByOrderDateBetween(startDate, endDate);
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        // @TODO: повертати в результаті інформацію про існуючих viewer та ticket, а не порожні поля
        return orderRepository.save(order);
    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order updatedOrder) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.setViewer(updatedOrder.getViewer());
        order.setTicket(updatedOrder.getTicket());
        order.setOrderDate(updatedOrder.getOrderDate());
        return orderRepository.save(order);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderRepository.deleteById(id);
    }
}
