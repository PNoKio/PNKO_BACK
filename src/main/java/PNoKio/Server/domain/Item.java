package PNoKio.Server.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String itemName;
    private int price;
    private ItemStatus status;

    @ManyToOne
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

    public void setCategory(Category category) {
        this.category = category;
    }
}
