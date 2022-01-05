package PNoKio.Server.service;

import PNoKio.Server.domain.Category;
import PNoKio.Server.domain.Item;
import PNoKio.Server.domain.Owner;
import PNoKio.Server.domain.Store;
import PNoKio.Server.dto.*;
import PNoKio.Server.exception.DuplicateItemName;
import PNoKio.Server.repository.CategoryRepository;
import PNoKio.Server.repository.ItemRepository;
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
class ItemServiceImplTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    OwnerService ownerService;

    @Autowired
    StoreService storeService;

    @Autowired
    StoreRepository storeRepository;


    @Test
    void addItemSuccess(){
        Owner owner1 = makeOwner("kim", "rkdlem48", "asdf");
        ownerService.create(new OwnerDto(owner1.getEmail(),owner1.getOwnerName(), owner1.getPassword()));
        storeService.addStore(new StoreDto("스타벅스", "상명")
                ,new OwnerDto(owner1.getEmail(),owner1.getOwnerName(), owner1.getPassword()));
        Store store = storeRepository.findByStoreNameAndStoreBranch("스타벅스", "상명").get();
        categoryService.addCategory(store.getId(),new CategoryDto("커피"));

        Category coffee = categoryRepository.findByCategoryNameAndStoreId(store.getId(), "커피").get();

        itemService.addItem(coffee.getId(),new ItemDto("아메리카노",1000));

        List<ItemReturn> items = itemService.findItems(store.getId());
        ItemReturn itemReturn = items.get(0);
        List<SpecificItem> items1 = itemReturn.getItems();
        SpecificItem specificItem = items1.get(0);

        assertThat(itemReturn.getCategoryName()).isEqualTo("커피");
        assertThat(items.size()).isEqualTo(1);
        assertThat(specificItem.getItemName()).isEqualTo("아메리카노");
        assertThat(specificItem.getPrice()).isEqualTo(1000);


    }
    @Test
    void addItemFailDuplicateItemName(){
        Owner owner1 = makeOwner("kim", "rkdlem48", "asdf");
        ownerService.create(new OwnerDto(owner1.getEmail(),owner1.getOwnerName(), owner1.getPassword()));
        storeService.addStore(new StoreDto("스타벅스", "상명")
                ,new OwnerDto(owner1.getEmail(),owner1.getOwnerName(), owner1.getPassword()));
        Store store = storeRepository.findByStoreNameAndStoreBranch("스타벅스", "상명").get();
        categoryService.addCategory(store.getId(),new CategoryDto("커피"));

        Category coffee = categoryRepository.findByCategoryNameAndStoreId(store.getId(), "커피").get();

        itemService.addItem(coffee.getId(),new ItemDto("아메리카노",1000));

        assertThatThrownBy(() ->         itemService.addItem(coffee.getId(),new ItemDto("아메리카노",1000)))
                .isInstanceOf(DuplicateItemName.class);
    }
    @Test
    void updateItemSuccess(){
        Owner owner1 = makeOwner("kim", "rkdlem48", "asdf");
        ownerService.create(new OwnerDto(owner1.getEmail(),owner1.getOwnerName(), owner1.getPassword()));
        storeService.addStore(new StoreDto("스타벅스", "상명")
                ,new OwnerDto(owner1.getEmail(),owner1.getOwnerName(), owner1.getPassword()));
        Store store = storeRepository.findByStoreNameAndStoreBranch("스타벅스", "상명").get();
        categoryService.addCategory(store.getId(),new CategoryDto("커피"));

        Category coffee = categoryRepository.findByCategoryNameAndStoreId(store.getId(), "커피").get();

        Item item = itemService.addItem(coffee.getId(), new ItemDto("아메리카노", 1000));


        itemService.updateItem(coffee.getId(),item.getId(),new ItemUpdateDto("카페라떼", 2000));

        Item updateResult = itemRepository.findById(item.getId()).get();

        assertThat(updateResult.getItemName()).isEqualTo("카페라떼");
        assertThat(updateResult.getPrice()).isEqualTo(2000);

    }

    @Test
    void updateItemFailDuplicateName(){
        Owner owner1 = makeOwner("kim", "rkdlem48", "asdf");
        ownerService.create(new OwnerDto(owner1.getEmail(),owner1.getOwnerName(), owner1.getPassword()));
        storeService.addStore(new StoreDto("스타벅스", "상명")
                ,new OwnerDto(owner1.getEmail(),owner1.getOwnerName(), owner1.getPassword()));
        Store store = storeRepository.findByStoreNameAndStoreBranch("스타벅스", "상명").get();
        categoryService.addCategory(store.getId(),new CategoryDto("커피"));

        Category coffee = categoryRepository.findByCategoryNameAndStoreId(store.getId(), "커피").get();

        Item item1 = itemService.addItem(coffee.getId(), new ItemDto("아메리카노", 1000));
        Item item2 = itemService.addItem(coffee.getId(), new ItemDto("카페라떼", 2000));
        assertThatThrownBy(() -> itemService.updateItem(coffee.getId(),item1.getId(),new ItemUpdateDto("카페라떼", 2000)))
                .isInstanceOf(DuplicateItemName.class);

    }

    @Test
    void findAllItemAndCategory(){
        Owner owner1 = makeOwner("kim", "rkdlem48", "asdf");
        ownerService.create(new OwnerDto(owner1.getEmail(),owner1.getOwnerName(), owner1.getPassword()));
        storeService.addStore(new StoreDto("스타벅스", "상명")
                ,new OwnerDto(owner1.getEmail(),owner1.getOwnerName(), owner1.getPassword()));
        Store store = storeRepository.findByStoreNameAndStoreBranch("스타벅스", "상명").get();
        categoryService.addCategory(store.getId(),new CategoryDto("커피"));

        Category coffee = categoryRepository.findByCategoryNameAndStoreId(store.getId(), "커피").get();

        Item item1 = itemService.addItem(coffee.getId(), new ItemDto("아메리카노", 1000));
        Item item2 = itemService.addItem(coffee.getId(), new ItemDto("카페라떼", 2000));

        List<ItemReturn> items = itemService.findItems(store.getId());

        ItemReturn itemReturn = items.get(0);
        String categoryName = itemReturn.getCategoryName();
        List<SpecificItem> items1 = itemReturn.getItems();
        assertThat(items.size()).isEqualTo(1);
        assertThat(categoryName).isEqualTo("커피");
        assertThat(items1.size()).isEqualTo(2);


    }



    private Owner makeOwner(String ownerName,String email, String password) {
        Owner owner = Owner.builder()
                .ownerName(ownerName)
                .email(email)
                .password(password)
                .build();
        return owner;
    }

}