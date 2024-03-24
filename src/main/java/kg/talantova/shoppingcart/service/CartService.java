package kg.talantova.shoppingcart.service;

import kg.talantova.shoppingcart.DTO.product.ProductResponseDTO;
import kg.talantova.shoppingcart.entity.Product;
import kg.talantova.shoppingcart.entity.User;
import kg.talantova.shoppingcart.exception.NotFoundException;
import kg.talantova.shoppingcart.mapper.ProductMapper;
import kg.talantova.shoppingcart.repository.ProductRepository;
import kg.talantova.shoppingcart.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class CartService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public CartService(UserRepository userRepository, ProductRepository productRepository, ProductMapper productMapper) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ResponseEntity<Void> addProduct(Long productId, Long userId) {
        if(productRepository.findById(productId).isEmpty()) {
            throw new NotFoundException("Product with such id does not exist");
        }
        if(userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("User with such id was not found ");
        }
        User user = userRepository.findById(userId).get();
        Product product = productRepository.findById(productId).get();
        if(product.getQuantity() < 1) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        user.getUserCart().add(product);
        product.getUsers().add(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteProduct(Long productId, Long userId) {
        if(productRepository.findById(productId).isEmpty()) {
            throw new NotFoundException("Product with such id does not exist");
        }
        if(userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("User with such id was not found ");
        }
        User user = userRepository.findById(userId).get();
        Product product = productRepository.findById(productId).get();
        if(!user.getUserCart().contains(product)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        user.getUserCart().remove(product);
        product.getUsers().remove(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> emptyCart(Long userId) {
        if(userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("User with such id was not found ");
        }
        User user = userRepository.findById(userId).get();
        user.setUserCart(Collections.emptyList());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Void> purchase(Long userId) {
        if(userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("User with such id was not found ");
        }
        User user = userRepository.findById(userId).get();
        if(user.getUserCart().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<Product> myProducts = user.getUserCart();
        for(Product product: myProducts) {
            product.setQuantity(product.getQuantity() - 1);
        }
        user.setUserCart(Collections.emptyList());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Page<ProductResponseDTO>> getAllMyProducts(Long userId, Pageable pageable) {
        if(userRepository.findById(userId).isEmpty()) {
            throw new NotFoundException("User with such id was not found ");
        }
        User user = userRepository.findById(userId).get();
        List<Product> myProducts = user.getUserCart();
        List<ProductResponseDTO> myProductsResponse = productMapper.toResponseList(myProducts);
        return new ResponseEntity<>(new PageImpl<>(myProductsResponse, pageable, myProductsResponse.size()), HttpStatus.OK);
    }

}
