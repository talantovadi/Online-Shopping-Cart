package kg.talantova.shoppingcart.service;

import kg.talantova.shoppingcart.entity.Product;
import kg.talantova.shoppingcart.entity.User;
import kg.talantova.shoppingcart.exception.NotFoundException;
import kg.talantova.shoppingcart.repository.ProductRepository;
import kg.talantova.shoppingcart.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional
public class CartService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartService(UserRepository userRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
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
}
