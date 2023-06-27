package br.com.system.carrental.services.rental;

import br.com.system.carrental.dtos.rental.RentalDTO;
import br.com.system.carrental.dtos.rental.RentalRequestDTO;
import br.com.system.carrental.exception.CarAlreadyRentedException;
import br.com.system.carrental.exception.EntityNotFoundExeption;
import br.com.system.carrental.models.Car;
import br.com.system.carrental.models.ClientModel;
import br.com.system.carrental.models.RentalModel;
import br.com.system.carrental.models.UserModel;
import br.com.system.carrental.models.enums.RentStatus;
import br.com.system.carrental.repositories.CarRepository;
import br.com.system.carrental.repositories.ClientRepository;
import br.com.system.carrental.repositories.RentalRepository;
import br.com.system.carrental.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RentalServiceImpl implements RentalService{

    private final RentalRepository rentalRepository;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;

    public RentalServiceImpl(RentalRepository rentalRepository, ClientRepository clientRepository, UserRepository userRepository, CarRepository carRepository) {
        this.rentalRepository = rentalRepository;
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
    }

    @Override
    public List<RentalDTO> listAllRents() {
        List<RentalModel> rentalModelList = rentalRepository.findAll();

        List<RentalDTO> rentalDTOList = new ArrayList<>();

        rentalModelList.forEach(rentalModel -> rentalDTOList.add(new RentalDTO(rentalModel)));

        return rentalDTOList;
    }

    @Override
    public Optional<RentalDTO> findRentalById(Long id) {
        Optional<RentalModel> rent = rentalRepository.findById(id);
        if (rent.isPresent()){
            return Optional.of(new RentalDTO(rent.get()));
        }

        return Optional.empty();
    }

    @Override
    public RentalDTO createRental(RentalRequestDTO rentalRequestDTO) {
        
        Long userId = rentalRequestDTO.getFkUser();
        Long clientId = rentalRequestDTO.getFkClient();
        Long carId = rentalRequestDTO.getFkCar();

        Optional<UserModel> user = userRepository.findById(userId);
        Optional<ClientModel> client = clientRepository.findById(clientId);
        Optional<Car> car = carRepository.findById(carId);

        if (user.isEmpty()){
            throw new EntityNotFoundExeption("Usuario ID:" + userId + " não encontrado");
        } else if(client.isEmpty()){
            throw new EntityNotFoundExeption("Client ID:" + clientId + " não encontrado");
        } else if (car.isEmpty()) {
            throw new EntityNotFoundExeption("Carro ID:" + carId + " não encontrado");
        }

        LocalDateTime rentStartDateTime = rentalRequestDTO.getRentStartDateTime();
        LocalDateTime rentFinishDateTime = rentalRequestDTO.getRentFinishDateTime();

        BigDecimal rentTotalPrice = calculateRentPrice(rentStartDateTime, rentFinishDateTime, car.get().getRentPrice());
        
        RentalModel newRent = new RentalModel();
        
        newRent.setRentStartDateTime(rentalRequestDTO.getRentStartDateTime());
        newRent.setRentFinishDateTime(rentalRequestDTO.getRentFinishDateTime());
        newRent.setRentTotalPrice(rentTotalPrice);
        newRent.setFkUser(user.get());
        newRent.setFkClient(client.get());
        newRent.setFkCar(car.get());

        RentalModel rentalSave = rentalRepository.save(newRent);

        return new RentalDTO(rentalSave);
    }

    @Override
    public Optional<RentalDTO> updateRentalById(Long id, RentalRequestDTO rentalRequestDTO) {

        RentalModel rent = rentalRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundExeption("Aluguel ID:" + id + " não encontrado"));

        Long userId = rentalRequestDTO.getFkUser();
        Long clientId = rentalRequestDTO.getFkClient();
        Long carId = rentalRequestDTO.getFkCar();

        Optional<UserModel> user = userRepository.findById(userId);
        Optional<ClientModel> client = clientRepository.findById(clientId);
        Optional<Car> car = carRepository.findById(carId);

        if (user.isEmpty()){
            throw new EntityNotFoundExeption("Usuario ID:" + userId + " não encontrado");
        } else if(client.isEmpty()){
            throw new EntityNotFoundExeption("Client ID:" + clientId + " não encontrado");
        } else if (car.isEmpty()) {
            throw new EntityNotFoundExeption("Carro ID:" + carId + " não encontrado");
        }

        if(car.get().getRentStatus().equals(RentStatus.RENTED.name())){
            throw new CarAlreadyRentedException("O carro ID:" + id + " já esta alugado");
        }

        LocalDateTime rentStartDateTime = rentalRequestDTO.getRentStartDateTime();
        LocalDateTime rentFinishDateTime = rentalRequestDTO.getRentFinishDateTime();

        BigDecimal rentTotalPrice = calculateRentPrice(rentStartDateTime, rentFinishDateTime, car.get().getRentPrice());

        rent.setRentStartDateTime(rentStartDateTime);
        rent.setRentFinishDateTime(rentFinishDateTime);
        rent.setRentTotalPrice(rentTotalPrice);
        rent.setFkUser(user.get());
        rent.setFkClient(client.get());
        rent.setFkCar(car.get());

        RentalModel rentSave = rentalRepository.save(rent);

        return Optional.of(new RentalDTO(rentSave));
    }

    @Override
    public Optional<Boolean> deleteRentalById(Long id) {
        Optional<RentalModel> rent = rentalRepository.findById(id);

        if(rent.isPresent()){
            rentalRepository.deleteById(rent.get().getId());
            return Optional.of(true);
        }

        throw new EntityNotFoundExeption("O aluguel id " + id + " não foi encontrado");
    }

    private BigDecimal calculateRentPrice(LocalDateTime startDateTime, LocalDateTime finishDateTime,
                                          BigDecimal rentPrice){

        BigDecimal rentDuration = new BigDecimal((Duration.between(startDateTime, finishDateTime).toMinutes()))
                .divide(new BigDecimal(60), 2, RoundingMode.DOWN);

        return rentDuration.multiply(rentPrice);
    }
}
