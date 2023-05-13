package br.com.system.carrental.services;


import br.com.system.carrental.models.UserModel;
import br.com.system.carrental.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;

public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepository.findByUsername(username).
                orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return new User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
}
