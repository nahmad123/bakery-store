package bakery.store.controller.model;

import bakery.store.entity.Employee;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class BakeryStoreEmployee {

	 private Long employeeId;
	 private String employeeFirstName;
	 private String employeeLastName;
	 private String employeePhone;
	 private String employeeJobTitle;
	 
	 public BakeryStoreEmployee(Employee employee) {
		 employeeId = employee.getEmployeeId();
		 employeeFirstName = employee.getEmployeeFirstName();
		 employeeLastName = employee.getEmployeeLastName();
		 employeePhone = employee.getEmployeePhone();
		 employeeJobTitle = employee.getEmployeeJobTitle();
		 
	 }

	
}
