package PNoKio.Server.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String categoryName;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "category")
    private List<Item> items;

    @Builder
    public Category(String categoryName) {
        this.categoryName = categoryName;
        items = new ArrayList<Item>();
    }

    public void setStore(Store store){
        this.store = store;
    }

    public void addItem(Item item){
        items.add(item);
        item.setCategory(this);
    }

    public void removeItem(Long itemId) {
        // 제거 알고리즘
    }
}