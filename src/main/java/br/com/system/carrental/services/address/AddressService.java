package br.com.system.carrental.services.address;

import br.com.system.carrental.dtos.address.AddressViaCepDTO;
import br.com.system.carrental.models.AddressModel;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final ViaCepService cepService;

    public AddressService(ViaCepService cepService) {
        this.cepService = cepService;
    }


    public AddressModel genarateAddress(String cep, String houseNumber, String addressComplement) {
        AddressViaCepDTO addressViaCep = cepService.getAddressViaCep(cep);

        AddressModel address = new AddressModel();

        address.setStreet(addressViaCep.getLogradouro());
        address.setHouseNumber(houseNumber);
        address.setDistrict(addressViaCep.getBairro());
        address.setCity(addressViaCep.getLocalidade());
        address.setUf(addressViaCep.getUf());
        address.setComplement(addressComplement);

        return address;
    }
}
