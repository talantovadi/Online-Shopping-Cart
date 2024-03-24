package kg.talantova.shoppingcart.controller;

import io.swagger.v3.oas.annotations.Operation;
import kg.talantova.shoppingcart.DTO.product.ProductResponseDTO;
import kg.talantova.shoppingcart.service.CartService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/my-cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/add-product/{product-id}/{user-id}")
    @Operation(
            summary = "Добавление товара в мою корзину по id товара и id пользователя "
    )
    public ResponseEntity<Void> addProductToCart(@PathVariable("product-id") Long productId,
                                                 @PathVariable("user-id") Long userId) {
        return cartService.addProduct(productId, userId);
    }

    @DeleteMapping("/delete-product/{product-id}/{user-id}")
    @Operation(
            summary = "Удаление товара из моей корзины по его id "
    )
    public ResponseEntity<Void> deleteProductFromCart(@PathVariable("product-id") Long productId,
                                                      @PathVariable("user-id") Long userId) {
        return cartService.deleteProduct(productId, userId);
    }

    @GetMapping("/empty-cart/{user-id}")
    @Operation(
            summary = "Удаление всех товаров из моей корзины "
    )
    public ResponseEntity<Void> emptyCart( @PathVariable("user-id") Long userId) {
        return cartService.emptyCart(userId);
    }


    @GetMapping("/purchase/{user-id}")
    @Operation(
            summary = "Покупка товаров в моей корзине"
    )
    public ResponseEntity<Void> purchaseProductsFromCart( @PathVariable("user-id") Long userId) {
        return cartService.purchase(userId);
    }

    @GetMapping("{user-id}")
    @Operation(
            summary = "Посмотреть все товары в моей корзине"
    )
    public ResponseEntity<Page<ProductResponseDTO>> getAllProductsInMyCart(@PathVariable("user-id") Long userId,
                                                                           @PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.DESC) Pageable pageable) {
        return cartService.getAllMyProducts(userId, pageable);
    }

}
