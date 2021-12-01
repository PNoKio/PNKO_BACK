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
public class Store {
    @Id
    @GeneratedValue
    @Column(name = "store_id")
    private Long id;

    private String storeName;
    private String branch;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "owner_id")
//    private Owner owner;

    @OneToMany(mappedBy = "store")
    private List<Category> categories;

    @Builder
    public Store(String storeName, String branch) {
        this.storeName = storeName;
        this.branch = branch;
        categories = new ArrayList<>();
    }

    public void addCategory(Category category) {
        categories.add(category);
        category.setStore(this);
    }

    public void removeCategory(Long categoryId) {
        // 카테고리 제거 알고리즘
    }
}
