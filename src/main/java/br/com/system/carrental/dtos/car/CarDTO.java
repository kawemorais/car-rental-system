package br.com.system.carrental.dtos.car;

import br.com.system.carrental.models.Car;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CarDTO {

    private Long id;

    private String carModel;

    private String carBrand;

    private Integer fabricationYear;

    private String plateNumber;

    private BigDecimal rentPrice;

    private String photoUrl;

    public CarDTO(Car car){
        BeanUtils.copyProperties(car, this);
    }

}
