package java_projects.online_store.controller;

import java_projects.online_store.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("orders", orderService.getAll());
        return "orders/list";
    }
}