package br.com.system.carrental.controllers;

import br.com.system.carrental.dtos.car.CarDTO;
import br.com.system.carrental.dtos.car.CarRequestDTO;
import br.com.system.carrental.exception.EntityNotFoundExeption;
import br.com.system.carrental.services.car.CarServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/car")
public class CarController {

    private final CarServiceImpl carService;

    public CarController(CarServiceImpl carService) {
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<List<CarDTO>> findAllCars(){
        List<CarDTO> cars = carService.listAllCars();
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CarDTO> findCarById(@PathVariable Long id){

        Optional<CarDTO> car = carService.findCarById(id);

        if(car.isPresent()){
            return new ResponseEntity<>(car.get(), HttpStatus.OK);
        }

        throw new EntityNotFoundExeption("Carro ID: " + id + " não encontrado");
    }

    @PostMapping
    public ResponseEntity<CarDTO> createCar(@RequestBody @Valid CarRequestDTO carRequestDTO){

        CarDTO car = carService.createCar(carRequestDTO);

        return new ResponseEntity<>(car, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CarDTO> updateCarById(@PathVariable Long id,
                                                @RequestBody @Valid CarRequestDTO carRequestDTO){

        Optional<CarDTO> car = carService.updateCarById(id, carRequestDTO);

        if(car.isPresent()){
            return new ResponseEntity<>(car.get(), HttpStatus.OK);
        }

        throw new EntityNotFoundExeption("Carro ID: " + id + " não encontrado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCarById(@PathVariable Long id) {

        Optional<Boolean> isDeleted = carService.deleteCarById(id);

        if (isDeleted.isEmpty()) {
            throw new EntityNotFoundExeption("Carro ID: " + id + " não encontrado");
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/photo")
    public ResponseEntity<?> uploadPhoto(@RequestParam Long carId, @RequestParam MultipartFile file) throws IOException {
        Optional<?> optCar = carService.setPhotoUrlToCar(carId, file);

        return new ResponseEntity<>(optCar, HttpStatus.OK);
    }
}
