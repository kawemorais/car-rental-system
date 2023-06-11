package br.com.system.carrental.controllers;

import br.com.system.carrental.dtos.user.UserRequestDTO;
import br.com.system.carrental.dtos.user.UserResponseDTO;
import br.com.system.carrental.exception.EntityNotFoundExeption;
import br.com.system.carrental.services.user.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserServiceImpl userService;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userService = userServiceImpl;
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAllUsers(){
        List<UserResponseDTO> userList = userService.listAllUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findUserById(@PathVariable Long id){
        Optional<UserResponseDTO> user = userService.findUserById(id);

        if (user.isPresent()){
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }

        throw new EntityNotFoundExeption("Usuário ID: " + id + " não encontrado");
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO userRequestDTO) throws Exception {
        UserResponseDTO userResponseDTO = userService.createUser(userRequestDTO);
        return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUserById(@PathVariable Long id,
                                                          @RequestBody @Valid UserRequestDTO userRequestDTO){

        Optional<UserResponseDTO> userResponseDTO = userService.updateUserById(id, userRequestDTO);

        if(userResponseDTO.isPresent()) {
            return new ResponseEntity<>(userResponseDTO.get(), HttpStatus.OK);
        }

        throw new EntityNotFoundExeption("Usuário ID: " + id + " não encontrado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id){

        Optional<Boolean> isDeleted = userService.deleteUserById(id);

        if(isDeleted.isEmpty()){
            throw new EntityNotFoundExeption("Usuário ID: " + id + " não encontrado");
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }

}
