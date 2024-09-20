package bakery.store.controller.model;

import bakery.store.entity.MenuItem;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BakeryStoreMenuItem {
	
	private Long menuItemId;
	private String menuItemName;
	private String menuItemQuantity;
	private String menuItemPrice;
	private String menuItemSpecialNote;
	
	public BakeryStoreMenuItem (MenuItem menuItem) {
		setMenuItemId(menuItem.getMenuItemId());
		setMenuItemName(menuItem.getMenuItemName());
		setMenuItemQuantity(menuItem.getMenuItemQuantity());
		setMenuItemPrice(menuItem.getMenuItemPrice());
		setMenuItemSpecialNote(menuItem.getMenuItemSpecialNote());
		
		
	}


}
