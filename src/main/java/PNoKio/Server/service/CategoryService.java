package PNoKio.Server.service;

import PNoKio.Server.domain.Category;
import PNoKio.Server.domain.Item;
import PNoKio.Server.domain.Store;

import java.util.List;

public interface CategoryService {
    Long createCategory(Category category, Long storeId);
//    void removeCategory(Long categoryId);
//    List<Category> findCategories()
}
