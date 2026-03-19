package java_projects.online_store;

import java_projects.online_store.entity.*;
import java_projects.online_store.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;


@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public void run(String... args) {

        // Категории
        Category electronics = new Category(null, "Электроника", null);
        Category clothes     = new Category(null, "Одежда", null);
        Category shoes       = new Category(null, "Обувь", null);
        categoryRepository.saveAll(List.of(electronics, clothes, shoes));

        Category phones  = new Category(null, "Смартфоны", electronics);
        Category laptops = new Category(null, "Ноутбуки", electronics);
        Category tablets = new Category(null, "Планшеты", electronics);
        Category routers = new Category(null, "Роутеры", electronics);
        Category tvs     = new Category(null, "Телевизоры", electronics);
        categoryRepository.saveAll(List.of(phones, laptops, tablets, routers, tvs));

        // Пользователи
        User user1 = new User(null, "Иванов Иван Иванович", "+77001112233",
                "ivanov@mail.ru", "hash123", LocalDateTime.now(), "active");
        User user2 = new User(null, "Петрова Анна Сергеевна", "+77009998877",
                "petrova@mail.ru", "hash456", LocalDateTime.now(), "active");
        User user3 = new User(null, "Сидоров Алексей Петрович", "+77775554433",
                "sidorov@mail.ru", "hash789", LocalDateTime.now(), "blocked");
        userRepository.saveAll(List.of(user1, user2, user3));

        // Товары
        Product p1 = new Product(null, "iPhone 15 Pro", phones, "Apple", 450000.0,
                "Флагманский смартфон Apple", 10);
        Product p2 = new Product(null, "Samsung Galaxy S24", phones, "Samsung", 380000.0,
                "Топовый Android смартфон", 15);
        Product p3 = new Product(null, "MacBook Pro 14", laptops, "Apple", 750000.0,
                "Ноутбук на чипе M3", 5);
        Product p4 = new Product(null, "Lenovo ThinkPad X1", laptops, "Lenovo", 520000.0,
                "Бизнес ноутбук", 8);
        Product p5 = new Product(null, "iPad Pro 12.9", tablets, "Apple", 350000.0,
                "Профессиональный планшет", 12);
        Product p6 = new Product(null, "Samsung Galaxy Tab S9", tablets, "Samsung", 280000.0,
                "Android планшет", 7);
        Product p7 = new Product(null, "TP-Link Archer AX73", routers, "TP-Link", 45000.0,
                "WiFi 6 роутер", 20);
        Product p8 = new Product(null, "Samsung QN90C 55\"", tvs, "Samsung", 620000.0,
                "QLED телевизор 4K", 4);
        productRepository.saveAll(List.of(p1, p2, p3, p4, p5, p6, p7, p8));

        // Заказы
        Order order1 = new Order(null, LocalDateTime.now(), user1,
                "delivered", 450000.0, "card", "courier");
        Order order2 = new Order(null, LocalDateTime.now(), user2,
                "new", 380000.0, "cash", "pickup");
        Order order3 = new Order(null, LocalDateTime.now().minusDays(3), user1,
                "processing", 750000.0, "card", "courier");
        orderRepository.saveAll(List.of(order1, order2, order3));

        // Отзывы
        Review r1 = new Review(null, p1, user1, 5, "Отличный телефон, всем рекомендую!", LocalDateTime.now());
        Review r2 = new Review(null, p1, user2, 4, "Хороший телефон, но дорогой", LocalDateTime.now());
        Review r3 = new Review(null, p2, user1, 5, "Samsung не подвёл, отличная камера", LocalDateTime.now());
        Review r4 = new Review(null, p3, user2, 5, "MacBook — лучший ноутбук что у меня был", LocalDateTime.now());
        Review r5 = new Review(null, p4, user3, 3, "Средний ноутбук за свои деньги", LocalDateTime.now());
        reviewRepository.saveAll(List.of(r1, r2, r3, r4, r5));

        // Платежи
        Payment pay1 = new Payment(null, order1, LocalDateTime.now(), 450000.0, "completed", "TXN-001-2026");
        Payment pay2 = new Payment(null, order2, LocalDateTime.now(), 380000.0, "pending",   "TXN-002-2026");
        Payment pay3 = new Payment(null, order3, LocalDateTime.now(), 750000.0, "completed", "TXN-003-2026");
        paymentRepository.saveAll(List.of(pay1, pay2, pay3));
    }
}
