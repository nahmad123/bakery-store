package bakery.store.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
public class MenuItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long menuItemId;

	private String menuItemName;
	private String menuItemSpecialNote;
	private String menuItemQuantity;
	private String menuItemPrice;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(mappedBy = "menuItems", cascade = CascadeType.PERSIST)
	private Set<BakeryStore> bakeryStore = new HashSet<>();

	
}
	


