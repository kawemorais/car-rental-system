package br.com.system.carrental.dtos.userDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserLoginDTO {

    @NotBlank(message = "O campo usuario nao pode ser vazio")
    private String username;

    @NotBlank(message = "O campo senha nao pode ser vazio")
    private String password;

}
