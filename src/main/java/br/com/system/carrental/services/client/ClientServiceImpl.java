package br.com.system.carrental.services.client;

import br.com.system.carrental.dtos.client.ClientDTO;
import br.com.system.carrental.dtos.client.ClientRequestDTO;
import br.com.system.carrental.exception.EntityNotFoundExeption;
import br.com.system.carrental.exception.SomePropertyAlreadyInUseException;
import br.com.system.carrental.models.AddressModel;
import br.com.system.carrental.models.ClientModel;
import br.com.system.carrental.repositories.ClientRepository;
import br.com.system.carrental.services.address.AddressService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService{

    private final ClientRepository clientRepository;
    private final AddressService addressService;

    public ClientServiceImpl(ClientRepository clientRepository, AddressService addressService) {
        this.clientRepository = clientRepository;
        this.addressService = addressService;
    }

    @Override
    public List<ClientDTO> listAllClients() {
        List<ClientModel> clients = clientRepository.findAll();
        List<ClientDTO> clientDTOList = new ArrayList<>();

        clients.forEach(clientModel -> clientDTOList.add(new ClientDTO(clientModel)));

        return clientDTOList;
    }

    @Override
    public Optional<ClientDTO> findClientById(Long id) {
        Optional<ClientModel> client = clientRepository.findById(id);
        if (client.isPresent()){
            return Optional.of(new ClientDTO(client.get()));
        }

        return Optional.empty();
    }

    @Override
    public ClientDTO createClient(ClientRequestDTO clientRequestDTO) {

        if(isCpfNumberAlreadyUsed(clientRequestDTO.getCpf())) {
            throw new SomePropertyAlreadyInUseException("Este CPF já esta em uso");
        }
        if(isCnhNumberAlreadyUsed(clientRequestDTO.getCnhNumber())) {
            throw new SomePropertyAlreadyInUseException("Esta CNH já esta em uso");
        }

        AddressModel address = addressService.genarateAddress(clientRequestDTO.getCep(), clientRequestDTO.getHouseNumber(), clientRequestDTO.getAddressComplement());

        ClientModel client = clientRepository.save(new ClientModel(clientRequestDTO, address));

        return new ClientDTO(client);
    }

    @Override
    public Optional<ClientDTO> updateClientById(Long id, ClientRequestDTO clientRequestDTO) {

        Optional<ClientModel> client = clientRepository.findById(id);

        if(client.isEmpty()){
            throw new EntityNotFoundExeption("O client id " + id + " não foi encontrado");
        }

        ClientModel newClient = client.get();

        if(!newClient.getCpf().equals(clientRequestDTO.getCpf()) || !newClient.getCnhNumber().equals(clientRequestDTO.getCnhNumber())){
            if(isCpfNumberAlreadyUsed(clientRequestDTO.getCpf())) {
                throw new SomePropertyAlreadyInUseException("Este CPF já esta em uso");
            }
            if(isCnhNumberAlreadyUsed(clientRequestDTO.getCnhNumber())) {
                throw new SomePropertyAlreadyInUseException("Esta CNH já esta em uso");
            }
        }

        newClient.setName(clientRequestDTO.getName());
        newClient.setAge(clientRequestDTO.getAge());
        newClient.setSex(clientRequestDTO.getSex());
        newClient.setCpf(clientRequestDTO.getCpf());

        if(clientRequestDTO.getCep() != null){
            AddressModel address = addressService.genarateAddress(clientRequestDTO.getCep(), clientRequestDTO.getHouseNumber(), clientRequestDTO.getAddressComplement());
            newClient.setAddress(address);
        }

        newClient.getAddress().setHouseNumber(clientRequestDTO.getHouseNumber());
        newClient.getAddress().setComplement(clientRequestDTO.getAddressComplement());

        clientRepository.save(newClient);

        return Optional.of(new ClientDTO(newClient));
    }

    @Override
    public Optional<Boolean> deleteClientById(Long id) {
        Optional<ClientModel> client = clientRepository.findById(id);

        if(client.isPresent()){
            clientRepository.deleteById(client.get().getId());
            return Optional.of(true);
        }

        throw new EntityNotFoundExeption("O client id " + id + " não foi encontrado");
    }

    private boolean isCpfNumberAlreadyUsed(String cpfNumber){
        return this.clientRepository.findByCpf(cpfNumber).isPresent();
    }

    private boolean isCnhNumberAlreadyUsed(String cnhNumber){
        return this.clientRepository.findByCnhNumber(cnhNumber).isPresent();
    }
}
