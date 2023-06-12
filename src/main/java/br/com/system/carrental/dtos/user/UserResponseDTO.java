package br.com.system.carrental.dtos.user;

import br.com.system.carrental.models.UserModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
public class UserResponseDTO {

    private Long id;

    private String name;

    private String username;

    private String password;

    public UserResponseDTO(UserModel userModel){
        BeanUtils.copyProperties(userModel, this);
    }

}
