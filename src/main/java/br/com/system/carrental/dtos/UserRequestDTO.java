package br.com.system.carrental.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequestDTO {

    @NotBlank(message = "O campo nome nao pode ser vazio")
    private String name;

    @NotBlank(message = "O campo usuario nao pode ser vazio")
    private String username;

    @Size(min = 8, message = "O campo senha deve ser no minimo 8 caracteres")
    private String password;

}
