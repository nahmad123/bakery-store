package bakery.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import bakery.store.entity.MenuItem;

public interface MenuItemDao extends JpaRepository<MenuItem, Long>{

}
