package PNoKio.Server.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Owner extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id")
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "ownerName")
    private String ownerName;

    @Column(name = "password")
    private String password;


    @OneToMany(mappedBy = "owner")
    private List<Store> storeList = new ArrayList<>();


    @Builder
    public Owner(String email, String ownerName, String password){
        this.email=email;
        this.ownerName=ownerName;
        this.password=password;
    }
}
