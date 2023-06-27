package br.com.system.carrental.services.car;

import br.com.system.carrental.dtos.car.CarDTO;
import br.com.system.carrental.dtos.car.CarRequestDTO;
import br.com.system.carrental.exception.EntityNotFoundExeption;
import br.com.system.carrental.exception.InvalidFabricationYearException;
import br.com.system.carrental.exception.SomePropertyAlreadyInUseException;
import br.com.system.carrental.models.Car;
import br.com.system.carrental.repositories.CarRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final AmazonS3service amazonService;

    public CarServiceImpl(CarRepository carRepository, AmazonS3service amazonService) {
        this.carRepository = carRepository;
        this.amazonService = amazonService;
    }


    @Override
    public List<CarDTO> listAllCars() {
        List<Car> cars = carRepository.findAll();

        List<CarDTO> carDTOList = new ArrayList<>();

        cars.forEach(car -> carDTOList.add(new CarDTO(car)));

        return carDTOList;
    }

    @Override
    public Optional<CarDTO> findCarById(Long id) {
        Optional<Car> car = carRepository.findById(id);

        if(car.isPresent()){
            return Optional.of(new CarDTO(car.get()));
        }

        return Optional.empty();

    }

    @Override
    public CarDTO createCar(CarRequestDTO carRequestDTO) {
        boolean checkPlateNumber = isPlateNumberAlreadyUsed(carRequestDTO.getPlateNumber());

        if(checkPlateNumber){
            throw new SomePropertyAlreadyInUseException("Esta placa de carro ja esta sendo utilizada");
        }

        if(checkFabricationYear(carRequestDTO.getFabricationYear())){
            throw new InvalidFabricationYearException("Ano de fabricação invalido");
        }

        Car car = carRepository.save(new Car(carRequestDTO));

        return new CarDTO(car);
    }

    @Override
    public Optional<CarDTO> updateCarById(Long id, CarRequestDTO carRequestDTO) {
        Optional<Car> car = carRepository.findById(id);

        if(car.isEmpty()){
            throw new EntityNotFoundExeption("O carro id " + id + " não foi encontrado");
        }

        Car newCar = car.get();

        if(!newCar.getPlateNumber().equals(carRequestDTO.getPlateNumber())){
            if(isPlateNumberAlreadyUsed(carRequestDTO.getPlateNumber())){
                throw new SomePropertyAlreadyInUseException("Esta placa de carro ja esta sendo utilizada");
            }
        }

        if(checkFabricationYear(carRequestDTO.getFabricationYear())){
            throw new InvalidFabricationYearException("Ano de fabricação invalido");
        }

        newCar.setCarModel(carRequestDTO.getCarModel());
        newCar.setCarBrand(carRequestDTO.getCarBrand());
        newCar.setFabricationYear(carRequestDTO.getFabricationYear());
        newCar.setPlateNumber(carRequestDTO.getPlateNumber());
        newCar.setRentPrice(carRequestDTO.getRentPrice());

        carRepository.save(newCar);

        return Optional.of(new CarDTO(newCar));
    }

    @Override
    public Optional<Boolean> deleteCarById(Long id) {
        Optional<Car> car = carRepository.findById(id);

        if(car.isPresent()){
            carRepository.deleteById(car.get().getId());
            return Optional.of(true);
        }

        throw new EntityNotFoundExeption("O carro id " + id + " não foi encontrado");

    }

    @Override
    public Optional<?> setPhotoUrlToCar(Long id, MultipartFile file) throws IOException {

        Optional<Car> car = carRepository.findById(id);

        if(car.isPresent()){

            if(!car.get().getPhotoUrl().equals("")){
                String nameFileToBeRemoved = car.get().getPhotoUrl().replace("https://car-rental-system-bucket.s3.amazonaws.com/", "");
                amazonService.deleteImageOnS3Bucket(nameFileToBeRemoved);
            }

            String publicUrl = amazonService.saveImageOnS3Bucket(file);

            car.get().setPhotoUrl(publicUrl);

            carRepository.save(car.get());

            return Optional.of(car);
        }

        return Optional.empty();
    }

    private boolean isPlateNumberAlreadyUsed(String plateNumber){
        return this.carRepository.findByPlateNumber(plateNumber).isPresent();
    }

    private boolean checkFabricationYear(Integer fabricationYear){
        int getYear = LocalDate.now().getYear();
        return fabricationYear > getYear;
    }
}
