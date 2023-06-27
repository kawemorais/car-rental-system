package br.com.system.carrental.services.car;

import br.com.system.carrental.dtos.car.CarDTO;
import br.com.system.carrental.dtos.car.CarRequestDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface CarService {
    List<CarDTO> listAllCars();

    Optional<CarDTO> findCarById(Long id);

    CarDTO createCar(CarRequestDTO carRequestDTO);

    Optional<CarDTO> updateCarById(Long id, CarRequestDTO carRequestDTO);

    Optional<?> deleteCarById(Long id);

    Optional<?> setPhotoUrlToCar(Long id, MultipartFile file) throws IOException;
}
