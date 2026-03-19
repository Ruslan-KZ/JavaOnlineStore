package java_projects.online_store.dto;

import java_projects.online_store.entity.Product;
import lombok.Data;

@Data
public class ProductResponseDTO {

    private Long id;
    private String name;
    private String categoryName;
    private String brand;
    private Double price;
    private String description;
    private Integer stock;

    public ProductResponseDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.categoryName = product.getCategory() != null
                ? product.getCategory().getName()
                : null;
        this.brand = product.getBrand();
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.stock = product.getStock();
    }
}