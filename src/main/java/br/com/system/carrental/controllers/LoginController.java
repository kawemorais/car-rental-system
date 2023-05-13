package br.com.system.carrental.controllers;

import br.com.system.carrental.dtos.UserLoginDTO;
import br.com.system.carrental.services.LoginServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

    @Autowired
    private LoginServiceImpl loginService;

    @PostMapping
    public ResponseEntity<?> checkLogin(@RequestBody @Valid UserLoginDTO user){
        String userLogin = user.getUsername();
        String userPassword = user.getPassword();
        Boolean isLoginCorrect = loginService.verifyLogin(userLogin, userPassword);

        if(isLoginCorrect){
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

}
