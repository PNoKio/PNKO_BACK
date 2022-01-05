package PNoKio.Server.service;

import PNoKio.Server.domain.Category;
import PNoKio.Server.domain.Owner;
import PNoKio.Server.domain.Store;
import PNoKio.Server.dto.CategoryDto;
import PNoKio.Server.dto.CategoryUpdateDto;
import PNoKio.Server.dto.OwnerDto;
import PNoKio.Server.dto.StoreDto;
import PNoKio.Server.exception.DuplicateCategoryNameInStore;
import PNoKio.Server.repository.CategoryRepository;
import PNoKio.Server.repository.StoreRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CategoryServiceTest {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    StoreService storeService;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    OwnerService ownerService;

    @Test
    void addCategorySuccess(){
        Owner owner1 = makeOwner("kim", "rkdlem48", "asdf");
        ownerService.create(new OwnerDto(owner1.getEmail(),owner1.getOwnerName(), owner1.getPassword()));
        storeService.addStore(new StoreDto("스타벅스", "상명")
                ,new OwnerDto(owner1.getEmail(),owner1.getOwnerName(), owner1.getPassword()));
        Store store = storeRepository.findByStoreNameAndStoreBranch("스타벅스", "상명").get();
        categoryService.addCategory(store.getId(),new CategoryDto("커피"));
        Category coffee = categoryRepository.findByCategoryNameAndStoreId(store.getId(), "커피").get();
        assertThat(store.getId()).isEqualTo(coffee.getStore().getId());
        assertThat(coffee.getCategoryName()).isEqualTo("커피");
    }
    @Test
    void addCategoryDuplicateCategoryNameFail(){
        Owner owner1 = makeOwner("kim", "rkdlem48", "asdf");
        ownerService.create(new OwnerDto(owner1.getEmail(),owner1.getOwnerName(), owner1.getPassword()));
        storeService.addStore(new StoreDto("스타벅스", "상명")
                ,new OwnerDto(owner1.getEmail(),owner1.getOwnerName(), owner1.getPassword()));
        Store store = storeRepository.findByStoreNameAndStoreBranch("스타벅스", "상명").get();
        categoryService.addCategory(store.getId(),new CategoryDto("커피"));
        assertThatThrownBy(() ->         categoryService.addCategory(store.getId(),new CategoryDto("커피")))
                .isInstanceOf(DuplicateCategoryNameInStore.class);
    }

    @Test
    void updateCategoryNameSuccess(){
        Owner owner1 = makeOwner("kim", "rkdlem48", "asdf");
        ownerService.create(new OwnerDto(owner1.getEmail(),owner1.getOwnerName(), owner1.getPassword()));
        storeService.addStore(new StoreDto("스타벅스", "상명")
                ,new OwnerDto(owner1.getEmail(),owner1.getOwnerName(), owner1.getPassword()));
        Store store = storeRepository.findByStoreNameAndStoreBranch("스타벅스", "상명").get();
        categoryService.addCategory(store.getId(),new CategoryDto("커피"));
        Category category = categoryRepository.findByCategoryNameAndStoreId(store.getId(), "커피").get();
        categoryService.updateCategory(store.getId(),category.getId(),new CategoryUpdateDto("음료"));
        assertThat(category.getCategoryName()).isEqualTo("음료");
    }

    @Test
    void updateCategoryNameDuplicateFail(){
        Owner owner1 = makeOwner("kim", "rkdlem48", "asdf");
        ownerService.create(new OwnerDto(owner1.getEmail(),owner1.getOwnerName(), owner1.getPassword()));
        storeService.addStore(new StoreDto("스타벅스", "상명")
                ,new OwnerDto(owner1.getEmail(),owner1.getOwnerName(), owner1.getPassword()));

        Store store = storeRepository.findByStoreNameAndStoreBranch("스타벅스", "상명").get();
        categoryService.addCategory(store.getId(),new CategoryDto("커피"));
        Category category = categoryRepository.findByCategoryNameAndStoreId(store.getId(), "커피").get();
        assertThatThrownBy(() ->         categoryService.updateCategory(store.getId(),category.getId()
                ,new CategoryUpdateDto("커피"))).isInstanceOf(DuplicateCategoryNameInStore.class);
    }

    @Test
    void removeCategory(){
        Owner owner1 = makeOwner("kim", "rkdlem48", "asdf");
        ownerService.create(new OwnerDto(owner1.getEmail(),owner1.getOwnerName(), owner1.getPassword()));
        storeService.addStore(new StoreDto("스타벅스", "상명")
                ,new OwnerDto(owner1.getEmail(),owner1.getOwnerName(), owner1.getPassword()));
        Store store = storeRepository.findByStoreNameAndStoreBranch("스타벅스", "상명").get();
        categoryService.addCategory(store.getId(),new CategoryDto("커피"));
        List<Category> all = categoryRepository.findAll();
        assertThat(all.size()).isEqualTo(1L);
        categoryService.removeCategory(1L);
        all = categoryRepository.findAll();
        assertThat(all.size()).isEqualTo(0);

    }

    private Owner makeOwner(String ownerName, String email, String password) {
        Owner owner = Owner.builder()
                .ownerName(ownerName)
                .email(email)
                .password(password)
                .build();
        return owner;
    }
}