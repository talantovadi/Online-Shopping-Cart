package kg.talantova.shoppingcart.service;

import kg.talantova.shoppingcart.DTO.product.ProductCreateDTO;
import kg.talantova.shoppingcart.DTO.product.ProductResponseDTO;
import kg.talantova.shoppingcart.entity.Product;
import kg.talantova.shoppingcart.exception.NotValidException;
import kg.talantova.shoppingcart.mapper.ProductMapper;
import kg.talantova.shoppingcart.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;


    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public ResponseEntity<ProductResponseDTO> createProduct(ProductCreateDTO newProduct) {
        if(productRepository.findByName(newProduct.getName()).isPresent()) {
            throw new NotValidException("Product with such name is already exist ");
        }
        Product productEntity = productMapper.toEntity(newProduct);
        productEntity.setAvailable(true);
        productEntity.setRating(0);
        productRepository.save(productEntity);
        return new ResponseEntity<>(productMapper.toResponse(productEntity), HttpStatus.OK);
    }



}
