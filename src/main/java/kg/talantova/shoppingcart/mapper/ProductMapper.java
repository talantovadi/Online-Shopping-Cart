package kg.talantova.shoppingcart.mapper;

import kg.talantova.shoppingcart.DTO.product.ProductCreateDTO;
import kg.talantova.shoppingcart.DTO.product.ProductResponseDTO;
import kg.talantova.shoppingcart.DTO.user.UserResponseDTO;
import kg.talantova.shoppingcart.entity.Product;
import kg.talantova.shoppingcart.entity.User;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(ProductCreateDTO product);
    ProductResponseDTO toResponse(Product product);

    default Page<ProductResponseDTO> toProductResponsePage(Page<Product> productsPage) {
        return productsPage.map(this::toResponse);
    }
}
