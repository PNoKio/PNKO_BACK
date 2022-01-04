package PNoKio.Server.domain;

import PNoKio.Server.dto.StoreUpdateDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Long id;

    @Column(name = "store_name")
    private String storeName;

    @Column(name = "branch")
    private String branch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @OneToMany(mappedBy = "store")
    private List<Category> categories = new ArrayList<>();

    @Builder
    public Store(String storeName, String branch) {
        this.storeName = storeName;
        this.branch = branch;
        categories = new ArrayList<>();
    }

    private void setOwner(Owner owner){
        this.owner = owner;
    }
    private void addStore(){
        this.owner.getStoreList().add(this);
    }
    public void remove(){
        this.owner.getStoreList().remove(this);
    }

    public void update(StoreUpdateDto storeUpdateDto){
        this.storeName=storeUpdateDto.getStoreName();
        this.branch=storeUpdateDto.getBranch();
    }

    public static Store createStore(Owner owner, String storeName, String branch){
        Store store = new Store();
        store.setOwner(owner);
        store.storeName=storeName;
        store.branch=branch;
        store.addStore();
        return store;
    }
}
