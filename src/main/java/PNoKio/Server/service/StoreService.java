package PNoKio.Server.service;

import PNoKio.Server.domain.Store;
import PNoKio.Server.dto.OwnerDto;
import PNoKio.Server.dto.StoreDto;
import PNoKio.Server.dto.StoreUpdateDto;

import java.util.List;

public interface StoreService {
    List<Store> findStores();
    void addStore(StoreDto storeDto, OwnerDto ownerDto);
    void removeStore(StoreDto storeDto);
    void UpdateStore(Long storeId,StoreUpdateDto storeUpdateDto);
}
