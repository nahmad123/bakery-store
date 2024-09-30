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

	// Bakery Store *******************
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
		return bakeryStoreDao.findById(bakeryStoreId).orElseThrow(
				() -> new NoSuchElementException("Bakery store with ID=" + bakeryStoreId + " was not found."));
	}

	public BakeryStoreData retrieveBakeryStoreById(Long bakeryStoreId) {
		return new BakeryStoreData(findBakeryStoreById(bakeryStoreId));

	}

	@Transactional(readOnly = true)
	public List<BakeryStoreData> retrieveAllBakeryStores() {
		List<BakeryStore> bakeryStores = bakeryStoreDao.findAll();

		List<BakeryStoreData> result = new LinkedList<>();

		for (BakeryStore bakeryStore : bakeryStores) {
			BakeryStoreData bsd = new BakeryStoreData(bakeryStore);
			// bsd is bakery store data
			bsd.getMenuItems().clear();
			bsd.getEmployees().clear();

			result.add(bsd);
		}

		return result;
	}

	@Transactional(readOnly = false)
	public void deleteBakeryStoreById(Long bakeryStoreId) {
		BakeryStore bakeryStore = findBakeryStoreById(bakeryStoreId);
		bakeryStoreDao.delete(bakeryStore);
	}

	// Employee ***************************
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
	public BakeryStoreEmployee retrieveEmployeeById(Long employeeId) {
		return new BakeryStoreEmployee(findEmployeeById(employeeId));

	}
	private Employee findEmployeeById(Long employeeId) {
		Employee employee = employeeDao.findById(employeeId)
				.orElseThrow(() -> new NoSuchElementException("Employee with ID=" + employeeId + " was not found."));
	return employee;
	}
	
	@Transactional(readOnly = false)
	public void deleteEmployeeById(Long employeeId) {
		Employee employee = findEmployeeById(employeeId);
		employeeDao.delete(employee);
	}

	// menu item ****************************
	@Transactional
	public BakeryStoreMenuItem saveMenuItem(Long bakeryStoreId, BakeryStoreMenuItem bakeryStoreMenuItem) {
		BakeryStore bakeryStore = findBakeryStoreById(bakeryStoreId);
		Long menuItemId = bakeryStoreMenuItem.getMenuItemId();
		MenuItem menuItem = findOrCreateMenuItem(bakeryStoreId, menuItemId);

		copyMenuItemFields(menuItem, bakeryStoreMenuItem);

		menuItem.getBakeryStore().add(bakeryStore);
		bakeryStore.getMenuItems().add(menuItem);
		

		MenuItem dbMenuItem = menuItemDao.save(menuItem);

		return new BakeryStoreMenuItem(dbMenuItem);
	}

	private void copyMenuItemFields(MenuItem menuItem, BakeryStoreMenuItem bakeryStoreMenuItem) {
		menuItem.setMenuItemId(bakeryStoreMenuItem.getMenuItemId());
		menuItem.setMenuItemName(bakeryStoreMenuItem.getMenuItemName());
		menuItem.setMenuItemQuantity(bakeryStoreMenuItem.getMenuItemQuantity());
		menuItem.setMenuItemPrice(bakeryStoreMenuItem.getMenuItemPrice());
		menuItem.setMenuItemSpecialNote(bakeryStoreMenuItem.getMenuItemSpecialNote());
	}

	private MenuItem findMenuItemById(Long menuItemId) {
		return menuItemDao.findById(menuItemId)
				.orElseThrow(() -> new NoSuchElementException("Menu Item with ID=" + menuItemId + " was not found."));
	}

	private MenuItem findOrCreateMenuItem(Long bakeryStoreId, Long menuItemId) {
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
			throw new IllegalArgumentException("The menu item with ID=" + menuItemId
					+ " is not available in the bakery store with ID=" + bakeryStoreId);
		}
		return menuItem;
	}

	@Transactional(readOnly = true)
	public List<BakeryStoreMenuItem> retrieveAllMenuItems() {
		List<MenuItem> menuItems = menuItemDao.findAll();

		List<BakeryStoreMenuItem> result = new LinkedList<>();

		for (MenuItem menuItem : menuItems) {
			BakeryStoreMenuItem bsmi = new BakeryStoreMenuItem(menuItem);
			// bsmi is bakerystore menu item
			
			result.add(bsmi);
		}

		return result;
	}

	@Transactional(readOnly = false)
	public void deleteMenuItemById(Long menuItemId) {
		MenuItem menuItem = findMenuItemById(menuItemId);
		
		for (BakeryStore bakeryStore : menuItem.getBakeryStore()){
			bakeryStore.getMenuItems().remove(menuItem);
			
			// .remove(entity) is imp to break the join relationship so a delete
			// can be performed.
		}
		
		menuItemDao.delete(menuItem);
		
			}	

	}


