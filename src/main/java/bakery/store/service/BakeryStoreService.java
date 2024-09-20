package bakery.store.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bakery.store.controller.model.BakeryStoreData;
import bakery.store.controller.model.BakeryStoreEmployee;
import bakery.store.controller.model.BakeryStoreMenuItem;
import bakery.store.dao.BakeryStoreDao;
import bakery.store.dao.EmployeeDao;
import bakery.store.dao.MenuItemDao;
import bakery.store.entity.BakeryStore;
import bakery.store.entity.Employee;
import bakery.store.entity.MenuItem;




@Service
public class BakeryStoreService {
	@Autowired
	private BakeryStoreDao bakeryStoreDao;
	@Autowired
	private EmployeeDao employeeDao;
	@Autowired
	private MenuItemDao menuItemDao;

	@Transactional(readOnly = false)
	public BakeryStoreData saveBakeryStore(BakeryStoreData bakeryStoreData) {
		Long bakeryStoreId = bakeryStoreData.getBakeryStoreId();
		BakeryStore bakeryStore = findOrCreateBakeryStore(bakeryStoreId);

		copyBakeryStoreFields(bakeryStore, bakeryStoreData);
		return new BakeryStoreData(bakeryStoreDao.save(bakeryStore));
	}

	private void copyBakeryStoreFields(BakeryStore bakeryStore, BakeryStoreData bakeryStoreData) {
		bakeryStore.setBakeryStoreAddress(bakeryStoreData.getBakeryStoreAddress());
		bakeryStore.setBakeryStoreCity(bakeryStoreData.getBakeryStoreCity());
		bakeryStore.setBakeryStoreId(bakeryStoreData.getBakeryStoreId());
		bakeryStore.setBakeryStoreName(bakeryStoreData.getBakeryStoreName());
		bakeryStore.setBakeryStorePhone(bakeryStoreData.getBakeryStorePhone());
		bakeryStore.setBakeryStoreState(bakeryStoreData.getBakeryStoreState());
		bakeryStore.setBakeryStoreZip(bakeryStoreData.getBakeryStoreZip());

	}

	private BakeryStore findOrCreateBakeryStore(Long bakeryStoreId) {
		if (Objects.isNull(bakeryStoreId)) {
			return new BakeryStore();
		} else {
			return findBakeryStoreById(bakeryStoreId);
		}
	}
			
	private BakeryStore findBakeryStoreById(Long bakeryStoreId) {
		return bakeryStoreDao.findById(bakeryStoreId)
				.orElseThrow(() -> new NoSuchElementException("Bakery store with ID=" + bakeryStoreId + " was not found."));
	}


	public BakeryStoreData retrieveBakeryStoreById(Long bakeryStoreId) {
		// rebuild
		return new BakeryStoreData (findBakeryStoreById (bakeryStoreId));
	}
	 //Employee
	private void copyEmployeeFields(Employee employee, BakeryStoreEmployee bakeryStoreEmployee) {
		employee.setEmployeeFirstName(bakeryStoreEmployee.getEmployeeFirstName());
		employee.setEmployeeLastName(bakeryStoreEmployee.getEmployeeLastName());
		employee.setEmployeeId(bakeryStoreEmployee.getEmployeeId());
		employee.setEmployeeJobTitle(bakeryStoreEmployee.getEmployeeJobTitle());
		employee.setEmployeePhone(bakeryStoreEmployee.getEmployeePhone());
	}
	
	private Employee findOrCreateEmployee(Long bakeryStoreId, Long employeeId) {
		if (Objects.isNull(employeeId)) {
			return new Employee();
		}
		return findEmployeeById(bakeryStoreId, employeeId);
	}
	
	private Employee findEmployeeById(Long bakeryStoreId, Long employeeId) {
		Employee employee = employeeDao.findById(employeeId)
				.orElseThrow(() -> new NoSuchElementException("Employee with ID=" + employeeId + " was not found."));

		if (employee.getBakeryStore().getBakeryStoreId() != bakeryStoreId) {
			throw new IllegalArgumentException("The employee with ID=" + employeeId
					+ " is not employed by the bakery store with ID=" + bakeryStoreId + ".");

		}
		return employee;
	}
	
	@Transactional(readOnly = false)
	public BakeryStoreEmployee saveEmployee(Long bakeryStoreId, BakeryStoreEmployee bakeryStoreEmployee) {
		BakeryStore bakeryStore = findBakeryStoreById(bakeryStoreId);
		Long employeeId = bakeryStoreEmployee.getEmployeeId();
		Employee employee = findOrCreateEmployee(bakeryStoreId, employeeId);
		
		copyEmployeeFields(employee, bakeryStoreEmployee);
		
		employee.setBakeryStore(bakeryStore);
		bakeryStore.getEmployee().add(employee);
		
		Employee dbEmployee = employeeDao.save(employee);
		
		return new BakeryStoreEmployee(dbEmployee);
	}
	//menu item
	
	private void copyMenuItemFields(MenuItem menutItem, BakeryStoreMenuItem bakeryStoreMenuItem) {
		menuItem.setMenuItemId(bakeryStoreMenuItem.getMenuItemId());
		menuItem.setMenuItemName(bakeryStoreMenuItem.getMenuItemName());
		menuItem.setMenuItemQuantity(bakeryStoreMenuItem.getMenuItemQuantity());
		menuItem.setMenuItemPrice(bakeryStoreMenuItem.getMenuItemPrice());
		menuItem.setMenuItemSpecialNote(bakeryStoreMenuItem.getMenuItemSpecialNote());
	}
	
	private MenuItem findOrCreateMenuItem(Long BakeryStoreId, Long menuItemId) {
		if (Objects.isNull(menuItemId)) {
			return new MenuItem();
		}
		return findMenuItemById(bakeryStoreId, menuItemId);
	}

	private MenuItem findMenuItemById(Long bakeryStoreId, Long menuItemId) {
		MenuItem menuItem = menuItemDao.findById(menuItemId)
				.orElseThrow(() -> new NoSuchElementException("Menu Item with ID=" + menuItemId + " was not found."));

		boolean found = false;

		for (BakeryStore bakeryStore : menuItem.getBakeryStore()) {
			if (bakeryStore.getBakeryStoreId() == bakeryStoreId) {
				break;
			}
		}
		
		if (!found) {
			throw new IllegalArgumentException(
					"The menu item with ID=" + menuItemId + " is not available in the bakery store with ID=" + bakeryStoreId);
		}
		return menuItem;
	}
	
	@Transactional (readOnly = true)
	public List<BakeryStoreMenuItem> retrieveAllBakeryStoreMenuItems() {
		List<BakeryStoreMenuItem> bakeryStoresMenuItems = bakeryStoreDao.findAll();
		
		List<BakeryStoreMenuItem> result = new LinkedList<>();
		
		for(BakeryStoreMenuItem bakeryStoreMenuItem : bakeryStoreMenuItems) {
			BakeryStoreMenuItem psd = new BakeryStoreMenuItem (bakeryStoreMenuItem);
			
			psd.getMenuItems().clear();
			psd.getEmployees().clear();
			
			result.add(psd);
		}
		
		return result;
	
	@Transactional(readOnly = true)
	public List<BakeryStoreData> retrieveAllBakeryStores() {
		List<BakeryStore> bakeryStores = bakeryStoreDao.findAll();
		
		List<BakeryStoreData> result = new LinkedList<>();
		
		for(BakeryStore bakeryStore : bakeryStores) {
			BakeryStoreData psd = new BakeryStoreData (bakeryStore);
			
			psd.getMenuItem().clear();
			psd.getEmployees().clear();
			
			result.add(psd);
		}
		
		return result;
	}
	
	@Transactional(readOnly = false)
	public void deleteBakeryStoreById(Long bakeryStoreId ) {
		BakeryStore bakeryStore = findBakeryStoreById(bakeryStoreId);
		bakeryStoreDao.delete(bakeryStore);
	}
	@Transactional(readOnly = false)
	public void deleteEmployeeById(Long employeeId ) {
		Employee employee = findEmployeeById(employeeId);
		employeeDao.delete(employee);
	}
	private Employee findEmployeeById(Long employeeId) {
		return employeeDao.findById(employeeId)
				.orElseThrow(() -> new NoSuchElementException("Employee with ID=" + employeeId + " was not found."));
	}
	
	@Transactional(readOnly = false)
	public void deleteMenuItemById(Long menuItemId ) {
		MenuItem menuItem = findMenuItemById(menuItemId);
		menuItemDao.delete(menuItem);	
}

private MenuItem findMenuItemById(Long menuItemId) {
	return menuItemDao.findById(menuItemId)
			.orElseThrow(() -> new NoSuchElementException("Menu Item with ID=" + menuItemId + " was not found."));
}
	
}
	
	
	