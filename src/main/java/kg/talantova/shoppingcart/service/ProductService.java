package kg.talantova.shoppingcart.service;

import kg.talantova.shoppingcart.DTO.product.ProductCreateDTO;
import kg.talantova.shoppingcart.DTO.product.ProductResponseDTO;
import kg.talantova.shoppingcart.DTO.product.ProductUpdateDTO;
import kg.talantova.shoppingcart.entity.Product;
import kg.talantova.shoppingcart.exception.NotFoundException;
import kg.talantova.shoppingcart.exception.NotValidException;
import kg.talantova.shoppingcart.mapper.ProductMapper;
import kg.talantova.shoppingcart.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;


    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ResponseEntity<Page<ProductResponseDTO>> getAll(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);

        return new ResponseEntity<>(productMapper.toProductResponsePage(products), HttpStatus.OK);
    }

    public ResponseEntity<ProductResponseDTO> getProduct(Long id) {
        if(productRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Product with such id does not exist");
        }
        Product product = productRepository.findById(id).get();
        return new ResponseEntity<>(productMapper.toResponse(product), HttpStatus.OK);
    }
    public ResponseEntity<ProductResponseDTO> createProduct(ProductCreateDTO newProduct) {
        if(productRepository.findByName(newProduct.getName()).isPresent()) {
            throw new NotValidException("Product with such name is already exist ");
        }
        Product productEntity = productMapper.toEntity(newProduct);
        productEntity.setUsers(Collections.emptyList());
        productEntity.setAvailable(true);
        productEntity.setRating(0);
        productRepository.save(productEntity);
        return new ResponseEntity<>(productMapper.toResponse(productEntity), HttpStatus.OK);
    }


    public ResponseEntity<ProductResponseDTO> updateProduct(Long id, ProductUpdateDTO updatedProduct) {
        if(productRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Product with such id does not exist");
        }
        Product productEntity = productRepository.findById(id).get();
        productEntity.setName(updatedProduct.getName());
        productEntity.setShortDescription(updatedProduct.getShortDescription());
        productEntity.setAvailable(updatedProduct.isAvailable());
        productEntity.setRating(updatedProduct.getRating());
        productEntity.setPrice(updatedProduct.getPrice());
        productEntity.setQuantity(updatedProduct.getQuantity());
        productRepository.save(productEntity);
        return new ResponseEntity<>(productMapper.toResponse(productEntity), HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteProduct(Long id) {
        if(productRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Product with such id does not exist");
        }

        productRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
