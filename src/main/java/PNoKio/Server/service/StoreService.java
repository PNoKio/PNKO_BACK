package PNoKio.Server.service;

import PNoKio.Server.domain.Category;
import PNoKio.Server.domain.Owner;
import PNoKio.Server.domain.Store;

import java.util.List;

public interface StoreService {
    Long saveStore(Store store);
    void addCategory(Long storeId, Category category);
    void removeCategory(Long storeId, Long categoryId);
    List<Category> showCategories(Long storeId);
}