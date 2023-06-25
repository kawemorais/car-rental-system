package br.com.system.carrental.controllers;

import br.com.system.carrental.dtos.rental.RentalDTO;
import br.com.system.carrental.dtos.rental.RentalRequestDTO;
import br.com.system.carrental.exception.EntityNotFoundExeption;
import br.com.system.carrental.services.rental.RentalServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/api/v1/rent")
public class RentalController {

    private final RentalServiceImpl rentalService;


    public RentalController(RentalServiceImpl rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping
    public ResponseEntity<List<RentalDTO>> findAllClients(){
        List<RentalDTO> rentList = rentalService.listAllRents();
        return new ResponseEntity<>(rentList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalDTO> findRentById(@PathVariable Long id){
        Optional<RentalDTO> rent = rentalService.findRentalById(id);

        if (rent.isPresent()){
            return new ResponseEntity<>(rent.get(), HttpStatus.OK);
        }

        throw new EntityNotFoundExeption("Aluguel ID: " + id + " não encontrado");
    }

    @PostMapping
    public ResponseEntity<RentalDTO> createRent(@RequestBody @Valid RentalRequestDTO rentalRequestDTO){
        RentalDTO rent = rentalService.createRental(rentalRequestDTO);
        return new ResponseEntity<>(rent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentalDTO> updateRentById(@PathVariable Long id,
                                                      @RequestBody @Valid RentalRequestDTO rentalRequestDTO){

        Optional<RentalDTO> rentDTO = rentalService.updateRentalById(id, rentalRequestDTO);

        if(rentDTO.isPresent()) {
            return new ResponseEntity<>(rentDTO.get(), HttpStatus.OK);
        }

        throw new EntityNotFoundExeption("Aluguel ID: " + id + " não encontrado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRentById(@PathVariable Long id){

        Optional<Boolean> isDeleted = rentalService.deleteRentalById(id);

        if(isDeleted.isEmpty()){
            throw new EntityNotFoundExeption("Aluguel ID: " + id + " não encontrado");
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }
}
