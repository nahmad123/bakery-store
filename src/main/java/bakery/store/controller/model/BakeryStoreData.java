package bakery.store.controller.model;

import java.util.HashSet;
import java.util.Set;
import bakery.store.entity.BakeryStore;
import bakery.store.entity.Employee;
import bakery.store.entity.MenuItem;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class BakeryStoreData {

	private Long bakeryStoreId;
	private String bakeryStoreName;
	private String bakeryStoreAddress;
	private String bakeryStoreCity;
	private String bakeryStoreState;
	private String bakeryStoreZip;
	private String bakeryStorePhone;
	private Set<BakeryStoreMenuItem> menuItem = new HashSet<>();
	private Set<BakeryStoreEmployee> employees = new HashSet<>();	
	
	public BakeryStoreData (BakeryStore bakeryStore) {
		bakeryStoreId = bakeryStore.getBakeryStoreId();
		bakeryStoreName = bakeryStore.getBakeryStoreName();
		bakeryStoreAddress = bakeryStore.getBakeryStoreAddress();
		bakeryStoreCity = bakeryStore.getBakeryStoreCity();
		bakeryStoreState = bakeryStore.getBakeryStoreState();
		bakeryStoreZip = bakeryStore.getBakeryStoreZip();
		bakeryStorePhone = bakeryStore.getBakeryStorePhone();
		
		for (MenuItem menuItem : bakeryStore.getMenuItem()) {
			menuItem.add(new BakeryStoreMenuItem (menuItem));
		}
		
		for(Employee employee : bakeryStore.getEmployee()) {
			employees.add(new BakeryStoreEmployee (employee));
		}
	}
		
	}
				
	
