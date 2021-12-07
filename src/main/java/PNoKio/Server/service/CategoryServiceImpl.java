package PNoKio.Server.service;

import PNoKio.Server.domain.Category;
import PNoKio.Server.domain.Store;
import PNoKio.Server.repository.CategoryRepository;
import PNoKio.Server.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;
    private final StoreRepository storeRepository;

    @Override
    public Long createCategory(Category category, Long storeId) {
        Store store = storeRepository.findById(storeId).get();
        category.setStore(store);

        categoryRepository.save(category);
        return category.getId();
    }
}
