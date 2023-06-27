package br.com.system.carrental.services.user;

import br.com.system.carrental.dtos.user.UserRequestDTO;
import br.com.system.carrental.dtos.user.UserResponseDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserResponseDTO> listAllUsers();

    Optional<UserResponseDTO> findUserById(Long id);

    UserResponseDTO createUser(UserRequestDTO userRequestDTO) throws Exception;

    Optional<UserResponseDTO> updateUserById(Long id, UserRequestDTO userRequestDTO);

    Optional<?> deleteUserById(Long id);

}
