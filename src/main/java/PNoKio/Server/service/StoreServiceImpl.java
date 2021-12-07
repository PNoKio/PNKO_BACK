package PNoKio.Server.service;

import PNoKio.Server.domain.Category;
import PNoKio.Server.domain.Store;
import PNoKio.Server.repository.CategoryRepository;
import PNoKio.Server.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreServiceImpl implements StoreService{

    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public Long saveStore(Store store) {
        storeRepository.save(store);
        return store.getId();
    }

    @Override
    public void addCategory(Long storeId, Long categoryId) {
        // 엔티티 조회
        Store store = storeRepository.findById(storeId).get();
        Category category = categoryRepository.findById(categoryId).get();

        store.addCategory(category);
    }

    @Override
    public void removeCategory(Long storeId, Long categoryId) {
        Store store = storeRepository.findById(storeId).get();
        store.removeCategory(categoryId);
    }

    @Override
    public List<Category> showCategories(Long storeId) {
        Store store = storeRepository.findById(storeId).get();
        return store.getCategories();
    }


}
