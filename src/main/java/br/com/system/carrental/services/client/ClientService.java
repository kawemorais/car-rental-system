package br.com.system.carrental.services.client;

import br.com.system.carrental.dtos.client.ClientDTO;
import br.com.system.carrental.dtos.client.ClientRequestDTO;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    List<ClientDTO> listAllClients();

    Optional<ClientDTO> findClientById(Long id);

    ClientDTO createClient(ClientRequestDTO clientRequestDTO);

    Optional<ClientDTO> updateClientById(Long id, ClientRequestDTO clientRequestDTO);

    Optional<?> deleteClientById(Long id);
}
