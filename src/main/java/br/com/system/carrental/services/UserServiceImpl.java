package br.com.system.carrental.services;

import br.com.system.carrental.dtos.UserRequestDTO;
import br.com.system.carrental.dtos.UserResponseDTO;
import br.com.system.carrental.models.UserModel;
import br.com.system.carrental.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserResponseDTO> listAllUsers() {
        List<UserModel> userList = userRepository.findAll();
        List<UserResponseDTO> userResponseDTOList = new ArrayList<>();

        userList.forEach(userModel -> userResponseDTOList.add(new UserResponseDTO(userModel)));

        return userResponseDTOList;
    }

    @Override
    public Optional<UserResponseDTO> findUserById(Long id) {
        Optional<UserModel> user = userRepository.findById(id);

        if(user.isPresent()){
            return Optional.of(new UserResponseDTO(user.get()));
        }

        return Optional.empty();
    }

    @Override
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        UserModel user = new UserModel(userRequestDTO);

        userRepository.save(user);

        return new UserResponseDTO(user);
    }

    @Override
    public Optional<UserResponseDTO> updateUserById(Long id, UserRequestDTO userRequestDTO) {
        Optional<UserModel> user = userRepository.findById(id);

        if (user.isPresent()){
            UserModel newUser = user.get();
            newUser.setName(userRequestDTO.getName());
            newUser.setUsername(userRequestDTO.getUsername());
            newUser.setPassword(userRequestDTO.getPassword());

            userRepository.save(newUser);

            return Optional.of(new UserResponseDTO(newUser));
        }

        return Optional.empty();
    }

    @Override
    public void deleteUser(Long id) {
        Optional<UserModel> user = userRepository.findById(id);

        if(user.isPresent()){
            userRepository.deleteById(user.get().getId());
        }
    }
}
