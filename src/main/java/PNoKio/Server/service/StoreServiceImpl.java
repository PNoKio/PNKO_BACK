package PNoKio.Server.service;

import PNoKio.Server.argumentresolver.SessionDto;
import PNoKio.Server.domain.Owner;
import PNoKio.Server.domain.Store;
import PNoKio.Server.dto.OwnerDto;
import PNoKio.Server.dto.StoreDto;
import PNoKio.Server.dto.StoreUpdateDto;
import PNoKio.Server.exception.DuplicateStoreAndBranch;
import PNoKio.Server.repository.OwnerRepository;
import PNoKio.Server.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService{

    private final OwnerRepository ownerRepository;
    private final StoreRepository storeRepository;

    @Override
    public List<Store> findStores() {
        return storeRepository.findAll();
    }

    @Override
    public void addStore(StoreDto storeDto, SessionDto sessionDto) {
        Owner owner = ownerRepository.findByEmail(sessionDto.getEmail()).get();
        Store store = Store.createStore(owner, storeDto.getStoreName(), storeDto.getBranch());
        storeRepository.findByStoreNameAndStoreBranch(storeDto.getStoreName(), storeDto.getBranch()).ifPresent(
                a -> {
                    throw new DuplicateStoreAndBranch("같은 이름의 상호 지점이 있습니다.");
                }
        );
        storeRepository.save(store);
    }

    @Override
    public void removeStore(StoreDto storeDto) {
        Store store = storeRepository.findByStoreNameAndStoreBranch(storeDto.getStoreName(), storeDto.getBranch()).get();
        store.remove();
        storeRepository.delete(store);
    }

    @Override
    public void UpdateStore(Long storeId, StoreUpdateDto storeUpdateDto) {
        Store store = storeRepository.findById(storeId).get();
        storeRepository.findByStoreNameAndStoreBranch(storeUpdateDto.getStoreName(), storeUpdateDto.getBranch()).ifPresent(
                a -> {
                    throw new DuplicateStoreAndBranch("같은 이름의 상호 지점이 있습니다.");
                }
        );
        store.update(storeUpdateDto);
    }


}
