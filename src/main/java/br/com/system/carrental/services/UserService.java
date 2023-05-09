package br.com.system.carrental.services;

import br.com.system.carrental.dtos.UserRequestDTO;
import br.com.system.carrental.dtos.UserResponseDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserResponseDTO> listAllUsers();

    Optional<UserResponseDTO> findUserById(Long id);

    UserResponseDTO createUser(UserRequestDTO userRequestDTO);

    Optional<UserResponseDTO> updateUserById(Long id, UserRequestDTO userRequestDTO);

    void deleteUser(Long id);

}
