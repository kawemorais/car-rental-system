package br.com.system.carrental.services.rental;

import br.com.system.carrental.dtos.rental.RentalDTO;
import br.com.system.carrental.dtos.rental.RentalRequestDTO;

import java.util.List;
import java.util.Optional;

public interface RentalService {

    List<RentalDTO> listAllRents();

    Optional<RentalDTO> findRentalById(Long id);

    RentalDTO createRental(RentalRequestDTO rentalRequestDTO);

    Optional<RentalDTO> updateRentalById(Long id, RentalRequestDTO rentalRequestDTO);

    Optional<?> deleteRentalById(Long id);

}
