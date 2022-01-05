package PNoKio.Server.repository;

import PNoKio.Server.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {


    @Query("select c from Category c join fetch c.store where c.store.id = :storeId and c.categoryName= :categoryName")
    Optional<Category> findByCategoryNameAndStoreId(@Param("storeId") Long StoreId
            , @Param("categoryName") String categoryName);
}
