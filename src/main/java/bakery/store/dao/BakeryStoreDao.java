package bakery.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import bakery.store.entity.BakeryStore;

public interface BakeryStoreDao extends JpaRepository<BakeryStore, Long> {

}


