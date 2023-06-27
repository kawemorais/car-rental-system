package br.com.system.carrental.controllers;

import br.com.system.carrental.dtos.client.ClientDTO;
import br.com.system.carrental.dtos.client.ClientRequestDTO;
import br.com.system.carrental.exception.EntityNotFoundExeption;
import br.com.system.carrental.services.client.ClientServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/api/v1/client")
public class ClientController {

    private final ClientServiceImpl clientService;

    public ClientController(ClientServiceImpl clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<ClientDTO>> findAllClients(){
        List<ClientDTO> clientList = clientService.listAllClients();
        return new ResponseEntity<>(clientList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> findClientById(@PathVariable Long id){
        Optional<ClientDTO> user = clientService.findClientById(id);

        if (user.isPresent()){
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }

        throw new EntityNotFoundExeption("Usuário ID: " + id + " não encontrado");
    }

    @PostMapping
    public ResponseEntity<ClientDTO> createClient(@RequestBody @Valid ClientRequestDTO clientRequestDTO){
        ClientDTO client = clientService.createClient(clientRequestDTO);
        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClientById(@PathVariable Long id,
                                                          @RequestBody @Valid ClientRequestDTO clientRequestDTO){

        Optional<ClientDTO> clientDTO = clientService.updateClientById(id, clientRequestDTO);

        if(clientDTO.isPresent()) {
            return new ResponseEntity<>(clientDTO.get(), HttpStatus.OK);
        }

        throw new EntityNotFoundExeption("Usuário ID: " + id + " não encontrado");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteClientById(@PathVariable Long id){

        Optional<Boolean> isDeleted = clientService.deleteClientById(id);

        if(isDeleted.isEmpty()){
            throw new EntityNotFoundExeption("Usuário ID: " + id + " não encontrado");
        }

        return new ResponseEntity<>(HttpStatus.OK);

    }
}
