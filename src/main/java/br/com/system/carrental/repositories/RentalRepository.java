package br.com.system.carrental.repositories;

import br.com.system.carrental.models.RentalModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<RentalModel, Long> {
}
