package br.com.system.carrental.models;

import br.com.system.carrental.dtos.car.CarRequestDTO;
import br.com.system.carrental.models.enums.RentStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_cars")
@Data
@NoArgsConstructor
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "carmodel", nullable = false)
    private String carModel;

    @Column(name = "carbrand", nullable = false)
    private String carBrand;

    @Column(name = "fabricationyear", nullable = false)
    private Integer fabricationYear;

    @Column(name = "platenumber", nullable = false, unique = true)
    private String plateNumber;

    @Column(name = "rentprice", nullable = false)
    private BigDecimal rentPrice;

    @Column(name = "rentstatus", nullable = false)
    private String rentStatus;

    @Column(name = "photourl")
    private String photoUrl;

    public Car (CarRequestDTO carRequestDTO){
        BeanUtils.copyProperties(carRequestDTO, this);
        this.rentStatus = RentStatus.AVAILABLE.toString();
    }
}
