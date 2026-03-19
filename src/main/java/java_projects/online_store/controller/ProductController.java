package java_projects.online_store.controller;

import java_projects.online_store.dto.ProductRequestDTO;
import java_projects.online_store.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("products", productService.getAll());
        return "products/list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("product", new ProductRequestDTO());
        model.addAttribute("categories", productService.getAllCategories());
        return "products/form";
    }

    @PostMapping("/new")
    public String create(@Valid @ModelAttribute("product") ProductRequestDTO dto,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", productService.getAllCategories());
            return "products/form";
        }
        productService.create(dto);
        return "redirect:/products";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        var p = productService.getById(id);
        ProductRequestDTO dto = new ProductRequestDTO();
        dto.setName(p.getName());
        dto.setBrand(p.getBrand());
        dto.setPrice(p.getPrice());
        dto.setDescription(p.getDescription());
        dto.setStock(p.getStock());
        model.addAttribute("product", dto);
        model.addAttribute("productId", id);
        model.addAttribute("categories", productService.getAllCategories());
        return "products/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id,
            @Valid @ModelAttribute("product") ProductRequestDTO dto,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", productService.getAllCategories());
            return "products/form";
        }
        productService.update(id, dto);
        return "redirect:/products";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        productService.delete(id);
        return "redirect:/products";
    }
}