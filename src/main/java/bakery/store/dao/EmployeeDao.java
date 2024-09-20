package bakery.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import bakery.store.entity.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Long>{

}
