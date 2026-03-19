package java_projects.online_store.service;

import java_projects.online_store.dto.ProductRequestDTO;
import java_projects.online_store.dto.ProductResponseDTO;
import java_projects.online_store.entity.Category;
import java_projects.online_store.entity.Product;
import java_projects.online_store.repository.CategoryRepository;
import java_projects.online_store.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductResponseDTO create(ProductRequestDTO dto) {
        Product product = new Product();
        mapDtoToEntity(dto, product);
        return new ProductResponseDTO(productRepository.save(product));
    }

    public List<ProductResponseDTO> getAll() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponseDTO::new)
                .collect(Collectors.toList());
    }

    public ProductResponseDTO getById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Товар не найден: " + id));
        return new ProductResponseDTO(product);
    }

    public ProductResponseDTO update(Long id, ProductRequestDTO dto) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Товар не найден: " + id));
        mapDtoToEntity(dto, product);
        return new ProductResponseDTO(productRepository.save(product));
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    private void mapDtoToEntity(ProductRequestDTO dto, Product product) {
        product.setName(dto.getName());
        product.setBrand(dto.getBrand());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setStock(dto.getStock());
        if (dto.getCategoryId() != null) {
            categoryRepository.findById(dto.getCategoryId())
                    .ifPresent(product::setCategory);
        }
    }
}