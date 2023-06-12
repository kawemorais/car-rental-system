package br.com.system.carrental.services.address;

import br.com.system.carrental.dtos.address.AddressViaCepDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "via-cep-consumer", url = "https://viacep.com.br/ws/")
public interface ViaCepService {

    @GetMapping(value = "/{cep}/json")
    AddressViaCepDTO getAddressViaCep(@PathVariable("cep") String cep);

}
