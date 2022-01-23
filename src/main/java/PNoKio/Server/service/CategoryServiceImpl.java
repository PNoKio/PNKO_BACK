package PNoKio.Server.service;

import PNoKio.Server.domain.Category;
import PNoKio.Server.domain.Store;
import PNoKio.Server.dto.CategoryDto;
import PNoKio.Server.dto.CategoryNameDto;
import PNoKio.Server.dto.CategoryUpdateDto;
import PNoKio.Server.exception.DuplicateCategoryNameInStore;
import PNoKio.Server.repository.CategoryRepository;
import PNoKio.Server.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final StoreRepository storeRepository;


    @Override
    public void addCategory(Long storeId, CategoryDto categoryDto) {
        Store store = storeRepository.findById(storeId).get();

        categoryRepository.findByCategoryNameAndStoreId(storeId,categoryDto.getCategoryName()).ifPresent(
                a -> {
                    throw new DuplicateCategoryNameInStore("이미 가게안에 같은 이름의 카테고리가 있습니다.");
                }
        );
        Category category = Category.createCategory(store, categoryDto.getCategoryName());
        categoryRepository.save(category);
    }

    @Override
    public void updateCategory(Long storeId ,Long categoryId, CategoryUpdateDto categoryUpdateDto) {
        categoryRepository.findByCategoryNameAndStoreId(storeId, categoryUpdateDto.getCategoryName()).ifPresent(
                a -> {
                    throw new DuplicateCategoryNameInStore("이미 가게안에 같은 이름의 카테고리가 있습니다.");
                }
        );
        Category category = categoryRepository.findById(categoryId).get();
        category.change(categoryUpdateDto);
    }

    @Override
    public void removeCategory(Long categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public List<CategoryNameDto> findAllCategoryName(Long storeId) {
        Store store = storeRepository.findById(storeId).get();
        List<Category> categories = store.getCategories();
        return categories.stream().map(a -> new CategoryNameDto(a.getCategoryName()))
                .collect(Collectors.toList());
    }
}
