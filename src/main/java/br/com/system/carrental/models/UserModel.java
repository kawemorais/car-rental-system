package br.com.system.carrental.models;

import br.com.system.carrental.dtos.UserRequestDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Data
@Entity
@NoArgsConstructor
@Table(name="tb_users")
public class UserModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    public UserModel(UserRequestDTO userRequestDTO){
        BeanUtils.copyProperties(userRequestDTO, this);
    }
}
