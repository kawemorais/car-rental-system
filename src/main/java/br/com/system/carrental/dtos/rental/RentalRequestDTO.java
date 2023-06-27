package br.com.system.carrental.dtos.rental;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RentalRequestDTO {
    @Future
    private LocalDateTime rentStartDateTime;

    @Future
    private LocalDateTime rentFinishDateTime;

    @NotNull(message = "Usuário invalido")
    private Long fkUser;

    @NotNull(message = "Client invalido")
    private Long fkClient;

    @NotNull(message = "Carro invalido")
    private Long fkCar;
}
