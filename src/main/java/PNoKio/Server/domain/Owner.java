package PNoKio.Server.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity
@NoArgsConstructor
@Getter
public class Owner extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "owner_id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "ownerName")
    private String ownerName;

    @Column(name = "password")
    private String password;



    @Builder
    public Owner(String email, String ownerName, String password){

        this.email=email;
        this.ownerName=ownerName;
        this.password=password;
    }
}
