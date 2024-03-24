package kg.talantova.shoppingcart.service;

import kg.talantova.shoppingcart.DTO.UserCreateDTO;
import kg.talantova.shoppingcart.DTO.UserResponseDTO;
import kg.talantova.shoppingcart.DTO.UserUpdateDTO;
import kg.talantova.shoppingcart.entity.User;
import kg.talantova.shoppingcart.exception.NotFoundException;
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
        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new NotValidException("User with that email address is already exist");
        }
        User userEntity = mapper.toEntity(user);
        userRepository.save(userEntity);
        return new ResponseEntity<>(mapper.toUserResponse(userEntity), HttpStatus.OK);
    }

    public ResponseEntity<UserResponseDTO> updateUser(UserUpdateDTO updatedUser, Long id) {
        if(userRepository.findById(id).isEmpty()) {
            throw new NotFoundException("User with such id was not found ");
        }
        if(userRepository.findByEmail(updatedUser.getEmail()).isPresent() &&
                !userRepository.findByEmail(updatedUser.getEmail()).get().getId().equals(id)) {
            throw new NotValidException("User with that email address is already exist");
        }
        User userEntity = userRepository.findById(id).get();
        userEntity.setFirstName(updatedUser.getFirstName());
        userEntity.setLastName(updatedUser.getLastName());
        userEntity.setEmail(updatedUser.getEmail());
        userEntity.setPhone(updatedUser.getPhone());
        return new ResponseEntity<>(mapper.toUserResponse(userEntity), HttpStatus.OK);
    }

}
