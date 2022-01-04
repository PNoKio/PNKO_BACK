package PNoKio.Server.service;

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

@Service
@Transactional
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService{

    private final OwnerRepository ownerRepository;
    private final StoreRepository storeRepository;

    @Override
    public void addStore(StoreDto storeDto, OwnerDto ownerDto) {
        Owner owner = ownerRepository.findByEmail(ownerDto.getEmail()).get();
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
