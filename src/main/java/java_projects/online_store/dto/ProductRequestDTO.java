package java_projects.online_store.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class ProductRequestDTO {

    @NotBlank(message = "Название не может быть пустым")
    @Size(min = 2, max = 255)
    private String name;

    private Long categoryId;

    @Size(max = 100)
    private String brand;

    @NotNull(message = "Цена обязательна")
    @Positive(message = "Цена должна быть положительной")
    private Double price;

    @Size(max = 1000)
    private String description;

    @Min(0)
    private Integer stock = 0;
}