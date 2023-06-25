package br.com.system.carrental.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "tb_rental")
public class RentalModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "rentstartdatetime", nullable = false)
    private LocalDateTime rentStartDateTime;

    @Column(name = "rentfinishdatetime", nullable = false)
    private LocalDateTime rentFinishDateTime;

    @Column(name = "renttotalprice", nullable = false)
    private BigDecimal rentTotalPrice;

    @OneToOne
    @JoinColumn(name = "fkuser")
    private UserModel fkUser;

    @OneToOne
    @JoinColumn(name = "fkclient")
    private ClientModel fkClient;

    @OneToOne
    @JoinColumn(name = "fkcar")
    private Car fkCar;

}
