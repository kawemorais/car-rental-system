package br.com.system.carrental.controllers;

import br.com.system.carrental.dtos.userDTO.UserRequestDTO;
import br.com.system.carrental.dtos.userDTO.UserResponseDTO;
import br.com.system.carrental.exception.UserNotFoundExeption;
import br.com.system.carrental.services.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserServiceImpl userServiceImpl;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAllUsers(){
        List<UserResponseDTO> userList = userServiceImpl.listAllUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findUserById(@PathVariable Long id){
        Optional<UserResponseDTO> user = userServiceImpl.findUserById(id);

        if (user.isPresent()){
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }

        throw new UserNotFoundExeption("Usuário ID: " + id + " não encontrado");
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO userRequestDTO) throws Exception {
        UserResponseDTO userResponseDTO = userServiceImpl.createUser(userRequestDTO);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUserById(@PathVariable Long id,
                                                          @RequestBody @Valid UserRequestDTO userRequestDTO){

        Optional<UserResponseDTO> userResponseDTO = userServiceImpl.updateUserById(id, userRequestDTO);

        if(userResponseDTO.isPresent()) {
            return new ResponseEntity<>(userResponseDTO.get(), HttpStatus.OK);
        }

        throw new UserNotFoundExeption("Usuário ID: " + id + " não encontrado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id){

        Optional<Boolean> isDeleted = userServiceImpl.deleteUserById(id);

        if(isDeleted.isEmpty()){
            throw new UserNotFoundExeption("Usuário ID: " + id + " não encontrado");
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
