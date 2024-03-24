package kg.talantova.shoppingcart.mapper;

import kg.talantova.shoppingcart.DTO.UserCreateDTO;
import kg.talantova.shoppingcart.DTO.UserResponseDTO;
import kg.talantova.shoppingcart.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO toUserResponse(User user);

    User toEntity(UserCreateDTO userRequest);
}
