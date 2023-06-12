package br.com.system.carrental.repositories;

import br.com.system.carrental.models.ClientModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<ClientModel, Long> {

    Optional<ClientModel> findByCpf(String cpf);
    Optional<ClientModel> findByCnhNumber(String cnhNumber);

}
