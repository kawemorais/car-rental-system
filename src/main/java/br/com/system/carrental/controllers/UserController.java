package br.com.system.carrental.controllers;

import br.com.system.carrental.dtos.UserRequestDTO;
import br.com.system.carrental.dtos.UserResponseDTO;
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

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody @Valid UserRequestDTO userRequestDTO){
        try{
            UserResponseDTO userResponseDTO = userServiceImpl.createUser(userRequestDTO);
            return new ResponseEntity<>(userResponseDTO, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUserById(@PathVariable Long id,
                                                          @RequestBody @Valid UserRequestDTO userRequestDTO){

        Optional<UserResponseDTO> userResponseDTO = userServiceImpl.updateUserById(id, userRequestDTO);

        if(userResponseDTO.isPresent()) {
            return new ResponseEntity<>(userResponseDTO.get(), HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id){
        try {
            userServiceImpl.deleteUser(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
