package br.com.system.carrental.models;

import br.com.system.carrental.dtos.client.ClientRequestDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@Table(name = "tb_clients")
public class ClientModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "sex")
    private String sex;

    @Column(name = "cpf", nullable = false, unique = true, length = 11)
    private String cpf;

    @Column(name = "cnhnumber", nullable = false, unique = true, length = 11)
    private String cnhNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fkaddress")
    private AddressModel address;

    public ClientModel(ClientRequestDTO clientRequestDTO, AddressModel address) {
        this.name = clientRequestDTO.getName();
        this.age = clientRequestDTO.getAge();
        this.sex = clientRequestDTO.getSex();
        this.cpf = clientRequestDTO.getCpf();
        this.cnhNumber = clientRequestDTO.getCnhNumber();
        this.address = address;
    }
}
