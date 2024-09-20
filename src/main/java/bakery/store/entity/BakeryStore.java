package bakery.store.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Entity
@Data
public class BakeryStore {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bakeryStoreId;
	private String bakeryStoreName;
	private String bakeryStoreAddress;
	private String bakeryStoreCity;
	private String bakeryStoreState;
	private String bakeryStoreZip;
	private String bakeryStorePhone;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "bakery_store_menu_item", joinColumns = @JoinColumn(name = "bakery_store_id"), inverseJoinColumns = @JoinColumn(name = "menu_item_id"))
	private Set<MenuItem> menuItem = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "bakeryStore", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Employee> employee = new HashSet<>();
	
	
	
	
	
}
