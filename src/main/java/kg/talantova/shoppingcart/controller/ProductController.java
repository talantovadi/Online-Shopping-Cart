package kg.talantova.shoppingcart.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kg.talantova.shoppingcart.DTO.product.ProductCreateDTO;
import kg.talantova.shoppingcart.DTO.product.ProductResponseDTO;
import kg.talantova.shoppingcart.DTO.product.ProductUpdateDTO;
import kg.talantova.shoppingcart.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(
            summary = "Получение всех товаров "
    )
    public ResponseEntity<Page<ProductResponseDTO>> getAllProducts(@PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.DESC) Pageable pageable) {
        return productService.getAll(pageable);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Получение товара по его id  "
    )
    public ResponseEntity<ProductResponseDTO> getProduct(@PathVariable("id") Long id) {
        return productService.getProduct(id);
    }

    @PostMapping
    @Operation(
            summary = "Создание нового товара"
    )
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductCreateDTO newProduct) {
        return productService.createProduct(newProduct);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Изменение существующего товара"
    )
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable("id") Long id,
                                                            @Valid @RequestBody ProductUpdateDTO updatedProduct) {
        return productService.updateProduct(id, updatedProduct);
    }
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление существующего товара по его id "
    )
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {
        return productService.deleteProduct(id);
    }



}
