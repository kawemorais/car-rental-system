package br.com.system.carrental.services;

import br.com.system.carrental.dtos.UserRequestDTO;
import br.com.system.carrental.dtos.UserResponseDTO;
import br.com.system.carrental.models.UserModel;
import br.com.system.carrental.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserResponseDTO> listAllUsers() {
        List<UserModel> userList = userRepository.findAll();
        List<UserResponseDTO> userResponseDTOList = new ArrayList<>();

        userList.forEach(userModel -> userResponseDTOList.add(new UserResponseDTO(userModel)));

        return userResponseDTOList;
    }

    @Transactional(readOnly = true)
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
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
            newUser.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));

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
