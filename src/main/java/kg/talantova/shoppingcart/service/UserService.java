package kg.talantova.shoppingcart.service;

import kg.talantova.shoppingcart.DTO.user.UserCreateDTO;
import kg.talantova.shoppingcart.DTO.user.UserResponseDTO;
import kg.talantova.shoppingcart.DTO.user.UserUpdateDTO;
import kg.talantova.shoppingcart.entity.User;
import kg.talantova.shoppingcart.exception.NotFoundException;
import kg.talantova.shoppingcart.exception.NotValidException;
import kg.talantova.shoppingcart.mapper.UserMapper;
import kg.talantova.shoppingcart.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    private final UserMapper mapper;

    public UserService(UserRepository userRepository,  UserMapper mapper) {
        this.userRepository = userRepository;

        this.mapper = mapper;
    }

    public ResponseEntity<Page<UserResponseDTO>> getAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);

        return new ResponseEntity<>(mapper.toUserResponsePage(users), HttpStatus.OK);
    }

    public ResponseEntity<UserResponseDTO> getUser(Long id) {
        if(userRepository.findById(id).isEmpty()) {
            throw new NotFoundException("User with such id was not found ");
        }
        User userEntity = userRepository.findById(id).get();
        return new ResponseEntity<>(mapper.toUserResponse(userEntity), HttpStatus.OK);
    }
    public ResponseEntity<UserResponseDTO> createUser(UserCreateDTO user) {
        if(!user.getPassword().equals(user.getConfirmPassword())) {
            throw new NotValidException("Your password and confirm password not equals");
        }
        if(userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new NotValidException("User with that email address is already exist");
        }
        User userEntity = mapper.toEntity(user);
        userEntity.setUserCart(Collections.emptyList());
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

    public ResponseEntity<Void> deleteUser(Long id) {
        if(userRepository.findById(id).isEmpty()) {
            throw new NotFoundException("User with such id was not found ");
        }
        userRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
