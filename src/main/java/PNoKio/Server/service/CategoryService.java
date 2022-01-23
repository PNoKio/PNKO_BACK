package PNoKio.Server.service;

import PNoKio.Server.domain.Category;
import PNoKio.Server.dto.CategoryDto;
import PNoKio.Server.dto.CategoryNameDto;
import PNoKio.Server.dto.CategoryUpdateDto;

import java.util.List;

public interface CategoryService {
    void addCategory(Long storeId, CategoryDto categoryDto);
    void updateCategory(Long storeId,Long CategoryId, CategoryUpdateDto categoryUpdateDto);
    void removeCategory(Long categoryId);
    List<CategoryNameDto> findAllCategoryName(Long storeId);
    List<Category> findAllCategory(Long storeId);
}
