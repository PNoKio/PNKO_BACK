package PNoKio.Server.service;

import PNoKio.Server.dto.OwnerDto;
import PNoKio.Server.dto.StoreDto;
import PNoKio.Server.dto.StoreUpdateDto;

public interface StoreService {
    void addStore(StoreDto storeDto, OwnerDto ownerDto);
    void removeStore(StoreDto storeDto);
    void UpdateStore(Long storeId,StoreUpdateDto storeUpdateDto);
}
