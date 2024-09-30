package bakery.store.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import bakery.store.controller.model.BakeryStoreData;
import bakery.store.controller.model.BakeryStoreEmployee;
import bakery.store.controller.model.BakeryStoreMenuItem;
import bakery.store.entity.Employee;
import bakery.store.service.BakeryStoreService;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("/bakery_store")
@Slf4j
public class BakeryStoreController {

	// Bakery Store ****************
	@Autowired
	private BakeryStoreService bakeryStoreService;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public BakeryStoreData insertBakeryStore(@RequestBody BakeryStoreData bakeryStoreData) {
		log.info("Creating Bakery store {}", bakeryStoreData);
		return bakeryStoreService.saveBakeryStore(bakeryStoreData);
	}

	@PutMapping("/{bakeryStoreId}")
	public BakeryStoreData updateBakeryStoreData(@PathVariable Long bakeryStoreId,
			@RequestBody BakeryStoreData bakeryStoreData) {
		bakeryStoreData.setBakeryStoreId(bakeryStoreId);
		log.info("Updating bakery store {}", bakeryStoreData);
		return bakeryStoreService.saveBakeryStore(bakeryStoreData);

	}

	@GetMapping
	public List<BakeryStoreData> retrieveAllBakeryStores() {
		log.info("Retrieving all bakery stores");
		return bakeryStoreService.retrieveAllBakeryStores();

	}

	@GetMapping("/{bakeryStoreId}")
	public BakeryStoreData retrieveBakeryStoreById(@PathVariable Long bakeryStoreId) {
		log.info("Retrieving bakery store with ID={}", bakeryStoreId);

		return bakeryStoreService.retrieveBakeryStoreById(bakeryStoreId);
	
	}

	@DeleteMapping("/{bakeryStoreId}")
	public Map<String, String> deleteBakeryStoreById(@PathVariable Long bakeryStoreId) {
		log.info("Deleting bakery store with ID={}", bakeryStoreId);

		bakeryStoreService.deleteBakeryStoreById(bakeryStoreId);
		return Map.of("message", "Bakery store with ID=" + bakeryStoreId + " deleted.");
	}

	
	// Employee ******************
	@PostMapping("/{bakeryStoreId}/employee")
	@ResponseStatus(code = HttpStatus.CREATED)
	public BakeryStoreEmployee addEmployeeToBakeryStore(@PathVariable Long bakeryStoreId,
			@RequestBody BakeryStoreEmployee bakeryStoreEmployee) {
		log.info("Adding employee {} to bakery store with ID={}", bakeryStoreEmployee, bakeryStoreId);

		return bakeryStoreService.saveEmployee(bakeryStoreId, bakeryStoreEmployee);
	}
	
	@PutMapping("/{bakeryStoreId}/employee/{employeeId}")
	public BakeryStoreEmployee updateBakeryStoreEmployee (@PathVariable Long employeeId, @PathVariable Long bakeryStoreId, @RequestBody BakeryStoreEmployee bakeryStoreEmployee) {
		bakeryStoreEmployee.setEmployeeId(employeeId);
		log.info("Updating bakery store employee {}", bakeryStoreEmployee);
		return bakeryStoreService.saveEmployee(bakeryStoreId, bakeryStoreEmployee);

	}
	@GetMapping("/employee/{employeeId}")
	public BakeryStoreEmployee findEmployeeById(@PathVariable Long employeeId) {
		log.info("Retrieving employee with ID={}", employeeId);

		return bakeryStoreService.retrieveEmployeeById (employeeId);
	}
	@DeleteMapping("/{bakeryStoreId}/employee/{employeeId}")
	public Map<String, String> deleteEmployeeById(@PathVariable Long employeeId, @PathVariable Long bakeryStoreId) {
		log.info("Deleting bakery store employee with ID={}", employeeId);

		bakeryStoreService.deleteEmployeeById(employeeId);
		return Map.of("message", "Bakery store employee with ID=" + employeeId + " deleted.");
	}

	
	// Menu Item ******************
	@PostMapping("/{bakeryStoreId}/menuItem")
	@ResponseStatus(code = HttpStatus.CREATED)
	public BakeryStoreMenuItem addMenuItemToBakeryStore(@PathVariable Long bakeryStoreId, @RequestBody BakeryStoreMenuItem bakeryStoreMenuItem) {
		log.info("Adding menu item {} to the menu at Bakery store with ID={}", bakeryStoreMenuItem, bakeryStoreId);
		
		return bakeryStoreService.saveMenuItem(bakeryStoreId, bakeryStoreMenuItem);
	}

	@DeleteMapping("/{bakeryStoreId}/menuItem/{menuItemId}")
	public Map<String, String> deleteMenuItemById(@PathVariable Long menuItemId) {
		log.info("Deleting bakery store menu item with ID={}", menuItemId);

		bakeryStoreService.deleteMenuItemById(menuItemId);
		return Map.of("message", "Bakery store menu item with ID=" + menuItemId + " deleted.");
	}

}
