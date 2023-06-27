package br.com.system.carrental.dtos.client;

import br.com.system.carrental.models.AddressModel;
import br.com.system.carrental.models.ClientModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
public class ClientDTO {

    private Long id;

    private String name;

    private Integer age;

    private String sex;

    private String cpf;

    private String cnhNumber;

    private AddressModel address;

    public ClientDTO(ClientModel clientModel){
        BeanUtils.copyProperties(clientModel, this);
    }
}
