package PNoKio.Server.service;

import PNoKio.Server.argumentresolver.SessionDto;
import PNoKio.Server.domain.Store;
import PNoKio.Server.dto.StoreDto;
import PNoKio.Server.dto.StoreUpdateDto;

import java.util.List;

public interface StoreService {
    List<Store> findStores();
    void addStore(StoreDto storeDto, SessionDto sessionDto);
    void removeStore(StoreDto storeDto);
    void UpdateStore(Long storeId,StoreUpdateDto storeUpdateDto);
}
