package PNoKio.Server.repository;

import PNoKio.Server.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query("select s from Store s where s.storeName= :storeName and s.branch= :branch")
    Optional<Store> findByStoreNameAndStoreBranch(@Param("storeName") String storeName, @Param("branch") String branch);

    @Query("select s from Store s where s.id= :storeId")
    Optional<Store> findCategoryAndItemByStoreId(@Param("storeId") Long storeId);
}