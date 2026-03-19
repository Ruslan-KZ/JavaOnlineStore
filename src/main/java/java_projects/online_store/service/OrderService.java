package java_projects.online_store.service;

import java_projects.online_store.entity.Order;
import java_projects.online_store.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public Order getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Заказ не найден: " + id));
    }

    public Order create(Order order) {
        return orderRepository.save(order);
    }

    public Order update(Long id, Order updated) {
        Order order = getById(id);
        order.setStatus(updated.getStatus());
        order.setTotalAmount(updated.getTotalAmount());
        order.setPaymentMethod(updated.getPaymentMethod());
        order.setDeliveryMethod(updated.getDeliveryMethod());
        return orderRepository.save(order);
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}