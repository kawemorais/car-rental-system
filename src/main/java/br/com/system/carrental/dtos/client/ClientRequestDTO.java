package br.com.system.carrental.dtos.client;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientRequestDTO {

    @NotBlank(message = "O campo nome nao pode ser vazio")
    private String name;

    @Positive
    private Integer age;

    @NotBlank(message = "O campo sexo nao pode ser vazio")
    private String sex;

    @Size(min = 11, message = "O campo cpf deve ter no minimo 11 caracteres")
    private String cpf;

    @Size(min = 11, message = "O campo cnh deve ter no minimo 11 caracteres")
    private String cnhNumber;

    @Size(min = 8, message = "O campo cep deve ter no minimo 8 caracteres")
    private String cep;

    @NotBlank(message = "O campo sexo nao pode ser vazio")
    private String houseNumber;

    private String addressComplement;
    
}
