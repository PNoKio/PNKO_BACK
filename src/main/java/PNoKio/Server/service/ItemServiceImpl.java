package PNoKio.Server.service;

import PNoKio.Server.domain.Category;
import PNoKio.Server.domain.Item;
import PNoKio.Server.domain.Store;
import PNoKio.Server.dto.ItemDto;
import PNoKio.Server.dto.ItemReturn;
import PNoKio.Server.dto.ItemUpdateDto;
import PNoKio.Server.exception.DuplicateItemName;
import PNoKio.Server.repository.CategoryRepository;
import PNoKio.Server.repository.ItemRepository;
import PNoKio.Server.repository.StoreRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemServiceImpl implements ItemService{

    private final CategoryRepository categoryRepository;
    private final ItemRepository itemRepository;
    private final StoreRepository storeRepository;

    @Override
    public Item addItem(Long categoryId, ItemDto itemDto){
        Category category = categoryRepository.findById(categoryId).get();
        findDuplicateItemName(category, itemDto);
        Item item = Item.createItem(category, itemDto.getItemName(), itemDto.getPrice());
        return itemRepository.save(item);
    }

    @Override
    public Item updateItem(Long categoryId, Long itemId, ItemUpdateDto itemUpdateDto) {
        Category category = categoryRepository.findById(categoryId).get();
        findDuplicateItemName(category, itemUpdateDto);
        Item item = itemRepository.findById(itemId).get();
        item.change(itemUpdateDto);
        return item;
    }

    private void findDuplicateItemName(Category category, ItemDto itemDto) {
        List<Item> items = category.getItems();
        for(Item item: items){
            if(item.getItemName().equals(itemDto.getItemName())){
                throw new DuplicateItemName("이미 있는 상품 이름입니다.");
            }
        }
    }

    @Override
    public void removeItem(Long itemId) {
        itemRepository.deleteById(itemId);
    }

    @Override
    public List<ItemReturn> findItems(Long storeId) {
        Store store = storeRepository.findById(storeId).get();
        List<Category> categories = store.getCategories();
        return categories.stream().map(ItemReturn::new).collect(Collectors.toList());
    }
}
