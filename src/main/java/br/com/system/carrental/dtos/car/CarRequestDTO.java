package br.com.system.carrental.dtos.car;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
public class CarRequestDTO {

    @NotBlank(message = "O campo modelo do carro nao pode ser vazio")
    private String carModel;

    @NotBlank(message = "O campo marca do carro nao pode ser vazio")
    private String carBrand;

    @NotNull(message = "O campo ano de fabricação não pode ser vazio")
    private String fabricationYear;

    @NotBlank(message = "O campo placa nao pode ser vazio")
    @Size(min = 7, max = 7, message = "Placa invalida")
    private String plateNumber;

    @NotNull
    @DecimalMin(value = "0", message = "O preço de aluguel deve ser maior do que 0")
    private BigDecimal rentPrice;

}
