package PNoKio.Server.domain;

import PNoKio.Server.dto.CategoryDto;
import PNoKio.Server.dto.CategoryUpdateDto;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    private String categoryName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "category")
    private List<Item> items = new ArrayList<>();

    private void setStore(Store store){
        this.store =store;
    }

    private void addCategory(){
        this.store.getCategories().add(this);
    }

    public void change(CategoryUpdateDto categoryUpdateDto){
        this.categoryName=categoryUpdateDto.getCategoryName();
    }

    public static Category createCategory(Store store, String categoryName){
        Category category = new Category();
        category.setStore(store);
        category.addCategory();
        category.categoryName=categoryName;
        return category;
    }

}