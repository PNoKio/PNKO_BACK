package PNoKio.Server.service;

import PNoKio.Server.domain.Category;
import PNoKio.Server.domain.Store;
import PNoKio.Server.repository.CategoryRepository;
import PNoKio.Server.repository.StoreRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CategoryServiceImplTest {
    final String storeName1 = "블루포트";
    final String storeName2 = "카페드림";
    final String storeBranch1 = "상명대점";
    final String getStoreBranch2 = "불광점";

    @Autowired CategoryRepository categoryRepository;
    @Autowired CategoryService categoryService;
    @Autowired StoreRepository storeRepository;
    @Autowired StoreService storeService;

    @Test
//    @Rollback(false)
    public void 스토어_카테고리_연관관계(){
        //given
        Store store1 = new Store(storeName1, storeBranch1);
        Store store2 = new Store(storeName2, getStoreBranch2);

        Category coffee = new Category("커피");
        Category tea = new Category("티");

        Long storeId1 = storeService.saveStore(store1);


        //when
        categoryService.createCategory(coffee, storeId1);
        categoryService.createCategory(tea, storeId1);

        //then
        Store findStore = storeRepository.findById(storeId1).get();

        assertThat(findStore.getCategories().size()).isEqualTo(2); //store1의 카테고리 수는 2 개이다.

        categoryRepository.delete(coffee);
//        assertThat(findStore.getCategories().size()).isEqualTo(1); //store1의 카테고리 수는 2 개이다.
        System.out.println(categoryRepository.findAll().size());
    }
}