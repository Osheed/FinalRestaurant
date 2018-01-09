package restaurantService;

import java.util.List;

import businessobject.Menu;
import businessobject.Owner;
import businessobject.Restaurant;


public interface IManagement {

	//For the Owner
	public List<Owner> getOwners();
	public Owner getOwner(String email);
	public Owner login(String email, String password);
	public void registerOwner(String lastname, String firstname,String password,String phone, String email);
	
	//For the Restaurant
	public void registerRestaurant(String address, String country, String restaurantName, int postcode,Owner owner_id);
	public List<Restaurant> getRestaurants();
	public Restaurant getRestaurant(String name);
	public void removeRestaurant(long restId);
	public void updateRestaurant(Restaurant rest, String name, String address, int postcode, String country);
	
	//For the Menus
	public List<Menu> getMenus();
	public void addMenu(String name, String desc, float price, Restaurant restaurant);
	public void updateMenu(Menu menu, String name, String desc, float price, Restaurant restaurant);
	public void removeMenu(long menuId);
	public Menu getMenu(String name);
	
}
