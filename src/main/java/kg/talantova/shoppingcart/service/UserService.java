package kg.talantova.shoppingcart.service;

import kg.talantova.shoppingcart.DTO.UserCreateDTO;
import kg.talantova.shoppingcart.DTO.UserResponseDTO;
import kg.talantova.shoppingcart.entity.User;
import kg.talantova.shoppingcart.exception.NotValidException;
import kg.talantova.shoppingcart.mapper.UserMapper;
import kg.talantova.shoppingcart.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    private final UserMapper mapper;

    public UserService(UserRepository userRepository,  UserMapper mapper) {
        this.userRepository = userRepository;

        this.mapper = mapper;
    }

    public ResponseEntity<UserResponseDTO> createUser(UserCreateDTO user) {
        if(!user.getPassword().equals(user.getConfirmPassword())) {
            throw new NotValidException("Your password and confirm password not equals");
        }
        User userEntity = mapper.toEntity(user);
        userRepository.save(userEntity);
        return new ResponseEntity<>(mapper.toUserResponse(userEntity), HttpStatus.OK);
    }


}
