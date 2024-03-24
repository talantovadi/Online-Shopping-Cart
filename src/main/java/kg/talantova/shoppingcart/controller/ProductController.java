package kg.talantova.shoppingcart.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import kg.talantova.shoppingcart.DTO.product.ProductCreateDTO;
import kg.talantova.shoppingcart.DTO.product.ProductResponseDTO;
import kg.talantova.shoppingcart.DTO.product.ProductUpdateDTO;
import kg.talantova.shoppingcart.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
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
}
