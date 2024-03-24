package kg.talantova.shoppingcart.mapper;

import kg.talantova.shoppingcart.DTO.product.ProductCreateDTO;
import kg.talantova.shoppingcart.DTO.product.ProductResponseDTO;
import kg.talantova.shoppingcart.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductCreateDTO product);
    ProductResponseDTO toResponse(Product product);
}
