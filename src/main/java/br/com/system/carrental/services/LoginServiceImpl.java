package br.com.system.carrental.services;

import br.com.system.carrental.models.UserModel;
import br.com.system.carrental.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public LoginServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Boolean verifyLogin(String login, String password) {
        Optional<UserModel> user = userRepository.findByUsername(login);

        if (user.isPresent()){
            String userPassword = user.get().getPassword();

            return passwordEncoder.matches(password, userPassword);
        }

        return false;
    }
}
