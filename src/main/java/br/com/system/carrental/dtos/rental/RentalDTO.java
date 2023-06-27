package br.com.system.carrental.dtos.rental;

import br.com.system.carrental.dtos.car.CarDTO;
import br.com.system.carrental.dtos.client.ClientDTO;
import br.com.system.carrental.dtos.user.UserResponseDTO;
import br.com.system.carrental.models.RentalModel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class RentalDTO {
    private Long id;

    private LocalDateTime rentStartDateTime;

    private LocalDateTime rentFinishDateTime;

    private BigDecimal rentTotalPrice;

    private UserResponseDTO user;

    private ClientDTO client;

    private CarDTO car;

    public RentalDTO(RentalModel rentalModel){
        this.id = rentalModel.getId();
        this.rentStartDateTime = rentalModel.getRentStartDateTime();
        this.rentFinishDateTime = rentalModel.getRentFinishDateTime();
        this.rentTotalPrice = rentalModel.getRentTotalPrice();
        this.user = new UserResponseDTO(rentalModel.getFkUser());
        this.client = new ClientDTO(rentalModel.getFkClient());
        this.car = new CarDTO(rentalModel.getFkCar());
    }
}
