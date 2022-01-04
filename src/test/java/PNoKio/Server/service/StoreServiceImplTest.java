package PNoKio.Server.service;

import PNoKio.Server.domain.Owner;
import PNoKio.Server.domain.Store;
import PNoKio.Server.dto.OwnerDto;
import PNoKio.Server.dto.StoreDto;
import PNoKio.Server.dto.StoreUpdateDto;
import PNoKio.Server.exception.DuplicateStoreAndBranch;
import PNoKio.Server.repository.OwnerRepository;
import PNoKio.Server.repository.StoreRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class StoreServiceImplTest {

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    StoreService storeservice;

    @Autowired
    OwnerService ownerService;

    @Autowired
    OwnerRepository ownerRepository;
    @Test
    void addStoreSuccessTest(){
        Owner owner1 = makeOwner("kim", "rkdlem48", "asdf");
        ownerService.create(new OwnerDto(owner1.getEmail(),owner1.getOwnerName(),owner1.getPassword()));
        storeservice.addStore(new StoreDto("스타벅스", "상명")
                ,new OwnerDto(owner1.getEmail(),owner1.getOwnerName(), owner1.getPassword()));
        Store store = storeRepository.findByStoreNameAndStoreBranch("스타벅스", "상명").get();
        Owner owner = ownerRepository.findByEmail("rkdlem48").get();
        assertThat(store.getOwner()).isEqualTo(owner);
        assertThat(store.getStoreName()).isEqualTo("스타벅스");
        assertThat(store.getBranch()).isEqualTo("상명");
    }
    @Test
    void addStoreFailDuplicateStoreNameAndBranchTest(){
        Owner owner1 = makeOwner("kim", "rkdlem48", "asdf");
        ownerService.create(new OwnerDto(owner1.getEmail(),owner1.getOwnerName(),owner1.getPassword()));
        storeservice.addStore(new StoreDto("스타벅스", "상명")
                ,new OwnerDto(owner1.getEmail(),owner1.getOwnerName(), owner1.getPassword()));
        assertThatThrownBy(() -> storeservice.addStore(new StoreDto("스타벅스", "상명")
                ,new OwnerDto(owner1.getEmail(),owner1.getOwnerName(), owner1.getPassword()))).isInstanceOf(DuplicateStoreAndBranch.class);
    }

    @Test
    void removeStore(){
        Owner owner1 = makeOwner("kim", "rkdlem48", "asdf");
        ownerService.create(new OwnerDto(owner1.getEmail(),owner1.getOwnerName(),owner1.getPassword()));
        storeservice.addStore(new StoreDto("스타벅스", "상명")
                ,new OwnerDto(owner1.getEmail(),owner1.getOwnerName(), owner1.getPassword()));


        List<Store> all = storeRepository.findAll();
        assertThat(all.size()).isEqualTo(1);



        storeservice.removeStore(new StoreDto("스타벅스","상명"));



        all = storeRepository.findAll();
        assertThat(all.size()).isEqualTo(0);
    }

    @Test
    void updateSuccessStore(){
        Owner owner1 = makeOwner("kim", "rkdlem48", "asdf");
        ownerService.create(new OwnerDto(owner1.getEmail(),owner1.getOwnerName(),owner1.getPassword()));
        storeservice.addStore(new StoreDto("스타벅스", "상명")
                ,new OwnerDto(owner1.getEmail(),owner1.getOwnerName(), owner1.getPassword()));

        Long storeId = storeRepository.findByStoreNameAndStoreBranch("스타벅스", "상명").get().getId();

        storeservice.UpdateStore(storeId,new StoreUpdateDto("스타벅스","국민대"));
    }
    @Test
    void updateFailStore(){
        Owner owner1 = makeOwner("kim", "rkdlem48", "asdf");
        ownerService.create(new OwnerDto(owner1.getEmail(),owner1.getOwnerName(),owner1.getPassword()));

        storeservice.addStore(new StoreDto("스타벅스", "상명")
                ,new OwnerDto(owner1.getEmail(),owner1.getOwnerName(), owner1.getPassword()));
        storeservice.addStore(new StoreDto("스타벅스", "국민")
                ,new OwnerDto(owner1.getEmail(),owner1.getOwnerName(), owner1.getPassword()));

        Long storeId = storeRepository.findByStoreNameAndStoreBranch("스타벅스", "상명").get().getId();

        assertThatThrownBy(() -> storeservice.UpdateStore(storeId,new StoreUpdateDto("스타벅스","국민")))
        .isInstanceOf(DuplicateStoreAndBranch.class);
    }

    private Owner makeOwner(String ownerName,String email, String password) {
        Owner owner = Owner.builder()
                .ownerName(ownerName)
                .email(email)
                .password(password)
                .build();
        return owner;
    }
}
