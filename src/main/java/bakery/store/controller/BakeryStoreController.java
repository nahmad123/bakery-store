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
import bakery.store.service.BakeryStoreService;
import lombok.extern.slf4j.Slf4j;



@RestController
@RequestMapping("/bakery_store")
@Slf4j
public class BakeryStoreController {
	@Autowired
	private BakeryStoreService bakeryStoreService;

	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public BakeryStoreData insertBakeryStore(@RequestBody BakeryStoreData bakeryStoreData) {
		log.info("Creating Bakery store {}", bakeryStoreData);
		return bakeryStoreService.saveBakeryStore(bakeryStoreData);
	}
	
	@PutMapping("/{bakeryStoreId}")
	public BakeryStoreData updateBakeryStoreData(@PathVariable Long bakeryStoreId, @RequestBody BakeryStoreData bakeryStoreData) {
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
	
	
	
}
