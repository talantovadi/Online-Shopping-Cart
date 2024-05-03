package kg.talantova.shoppingcart.controller;

import io.swagger.v3.oas.annotations.Operation;
import kg.talantova.shoppingcart.DTO.product.ProductResponseDTO;
import kg.talantova.shoppingcart.entity.User;
import kg.talantova.shoppingcart.service.CartService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/my-cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/add-product/{product-id}")
    @Operation(
            summary = "Добавление товара в мою корзину по id товара  "
    )
    public ResponseEntity<Void> addProductToCart(@PathVariable("product-id") Long productId,
                                                 @AuthenticationPrincipal User user) {
        return cartService.addProduct(productId, user.getId());
    }

    @DeleteMapping("/{product-id}")
    @Operation(
            summary = "Удаление товара из моей корзины по его id "
    )
    public ResponseEntity<Void> deleteProductFromCart(@PathVariable("product-id") Long productId,
                                                      @AuthenticationPrincipal User user) {
        return cartService.deleteProduct(productId, user.getId());
    }

    @DeleteMapping()
    @Operation(
            summary = "Удаление всех товаров из моей корзины "
    )
    public ResponseEntity<Void> emptyCart( @AuthenticationPrincipal User user) {
        return cartService.emptyCart(user.getId());
    }


    @GetMapping("/purchase")
    @Operation(
            summary = "Покупка товаров в моей корзине"
    )
    public ResponseEntity<Void> purchaseProductsFromCart(@AuthenticationPrincipal User user) {
        return cartService.purchase(user.getId());
    }

    @GetMapping()
    @Operation(
            summary = "Посмотреть все товары в моей корзине"
    )
    public ResponseEntity<Page<ProductResponseDTO>> getAllProductsInMyCart(@PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.DESC) Pageable pageable,
                                                                           @AuthenticationPrincipal User user) {
        return cartService.getAllMyProducts(user.getId(), pageable);
    }

}
