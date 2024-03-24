package kg.talantova.shoppingcart.mapper;

import kg.talantova.shoppingcart.DTO.UserCreateDTO;
import kg.talantova.shoppingcart.DTO.UserResponseDTO;
import kg.talantova.shoppingcart.DTO.UserUpdateDTO;
import kg.talantova.shoppingcart.entity.User;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDTO toUserResponse(User user);


    User toEntity(UserCreateDTO userRequest);
    User toEntityFromUserUpdate(UserUpdateDTO user);



    default Page<UserResponseDTO> toUserResponsePage(Page<User> userPage) {
        return userPage.map(this::toUserResponse);
    }
}
