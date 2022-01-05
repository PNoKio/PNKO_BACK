package PNoKio.Server.service;

import PNoKio.Server.dto.CategoryDto;
import PNoKio.Server.dto.CategoryUpdateDto;

public interface CategoryService {
    void addCategory(Long storeId, CategoryDto categoryDto);
    void updateCategory(Long storeId,Long CategoryId, CategoryUpdateDto categoryUpdateDto);
    void removeCategory(Long categoryId);
}
