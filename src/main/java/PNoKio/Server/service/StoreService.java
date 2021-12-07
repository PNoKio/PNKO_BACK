package PNoKio.Server.service;

import PNoKio.Server.domain.Category;
import PNoKio.Server.domain.Store;

import java.util.List;

public interface StoreService {
    Long saveStore(Store store);
    void addCategory(Long storeId, Long categoryId);
    void removeCategory(Long storeId, Long categoryId);
    List<Category> showCategories(Long storeId);
}