package br.com.system.carrental.dtos.address;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressViaCepDTO {

    private String logradouro;

    private String bairro;

    private String localidade;

    private String uf;

    private String houseNumber = null;

    private String addressComplement = null;

}
