package PNoKio.Server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
public class OwnerDto {


    @NotEmpty
    @Email
    private String email;
    @NotEmpty
    private String ownerName;
    @NotEmpty
    private String password;

}
