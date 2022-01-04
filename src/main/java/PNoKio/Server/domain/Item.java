package PNoKio.Server.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "price")
    private int price;

    @Column(name = "item_status")
    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Builder
    public Item(Long id, String itemName, int price, ItemStatus status, Category category) {
        this.id = id;
        this.itemName = itemName;
        this.price = price;
        this.status = status;
        this.category = category;
    }

    private void setCategory(Category category) {
        this.category = category;
    }

    private void addItem(){
        this.category.getItems().add(this);
    }

    public static Item createItem(Category category, String itemName, int price){
        Item item = new Item();
        item.setCategory(category);
        item.itemName=itemName;
        item.price=price;
        item.status=ItemStatus.ON_SALE;
        item.addItem();
        return item;
    }
}
