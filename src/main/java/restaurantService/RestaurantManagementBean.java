package restaurantService;

import java.util.List;

import javax.ejb.Stateful;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import businessobject.Menu;
import businessobject.Owner;
import businessobject.Rating;
import businessobject.Restaurant;

@Stateful
public class RestaurantManagementBean implements IManagement {

	@PersistenceContext(name = "RestaurantPU")
	private EntityManager em;
	
	@Override
	public Owner login(String email, String password) {
		try {
			Query query = em.createQuery("FROM Owner o WHERE o.email=:email AND o.password=:password");
			query.setParameter("email", email);
			query.setParameter("password", password);
			
			Owner owner = (Owner)query.getSingleResult();

			return owner;
		} catch (Exception e) {
			System.out.println("RestaurantManagementBean - login failed");
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public void registerOwner( String lastname, String firstname,String password,String phone, String email  ){
		try{
		System.out.println("RestaurantManagementBean - registerOwner");
		
		Owner new_Owner = new Owner(lastname, firstname, password, phone, email);
		em.persist(new_Owner);
		System.out.println("RestaurantManagementBean - Register Owner");
		}catch (Exception e){
			System.out.println("RestaurantManagementBean - registerOwner failed");
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Owner> getOwners() {
		try {
			System.out.println("RestaurantBean - getOwners");
			return em.createQuery("FROM Owner").getResultList();
		} catch (Exception e) {
			System.out.println("RestaurantBean - getOwners failed");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Owner getOwner(String email) {
		try {
			Query query = em.createQuery("FROM Owner o WHERE o.email=:email");
			query.setParameter("email", email);
			
			Owner owner = (Owner)query.getSingleResult();
			System.out.println("RestaurantManagementBean - getOwner");
			return owner;
		} catch (Exception e) {
			System.out.println("RestaurantManagementBean - getOwner failed");
			e.printStackTrace();
			return null;
		}	
	}
	
	@Override
	public void registerRestaurant(String address, String country, String restaurantName, int postcode, Owner owner ) {
		try{
			//LazyLoading to not charge all the related tables just owner 
			owner = em.find(Owner.class, owner.getId());
			Restaurant new_Restaurant = new Restaurant(restaurantName, address, postcode, country, owner);
			em.persist(new_Restaurant);
			System.out.println("RestaurantManagementBean - registerRestaurant");
		}catch (Exception e){
			System.out.println("RestaurantManagementBean - registerRestaurant failed");
			e.printStackTrace();
		}
		
	}
	
	@Override
	public List<Restaurant> getRestaurants() {
		try {
			System.out.println("RestaurantManagementBean - getRestaurants");
			return em.createQuery("FROM Restaurant").getResultList();
		} catch (Exception e) {
			System.out.println("RestaurantManagementBean - getRestaurants failed");
			return null;
		}
	}

	@Override
	public Restaurant getRestaurant(String name) {
		try {
			Query query = em.createQuery("FROM Restaurant r WHERE r.name_restaurant=:name_restaurant");
			query.setParameter("name_restaurant", name);
			
			Restaurant restaurant = (Restaurant)query.getSingleResult();
			System.out.println("RestaurantManagementBean - getRestaurant");
			return restaurant;
		} catch (Exception e) {
			System.out.println("RestaurantManagementBean - getRestaurant failed");
			return null;
		}
	}
	
	@Override
	public void updateRestaurant(Restaurant rest, String name, String address, int postcode, String country) {
		rest.setName_restaurant(name);
		rest.setAddress(address);
		rest.setPostcode(postcode);
		rest.setCountry(country);
		
		rest.setOwner(rest.getOwner());
		System.out.println("RestaurantManagementBean - updateRestaurant");
		em.merge(rest);
	}
	
	@Override
	public void removeRestaurant(long restaurantId) {
		Query query = em.createQuery("FROM Restaurant r WHERE r.id=:restaurantId");
		query.setParameter("restaurantId", restaurantId);
		Restaurant rest = (Restaurant)query.getSingleResult();

		em.remove(rest);
	}

	@Override
	public void addMenu(String name, String desc, float price, Restaurant restaurant) {
		System.out.println("RestaurantManagementBean - addMenu");
		Menu menu = new Menu(name, desc, price, restaurant);		
		em.persist(menu);
	}
	
	@Override
	public List<Menu> getMenus() {
		try {
			System.out.println("RestaurantManagementBean - getMenus");
			return em.createQuery("FROM Menu").getResultList();
		} catch (Exception e) {
			System.out.println("RestaurantManagementBean - getMenus failed");
			return null;
		}
	}
	
	@Override
	public Menu getMenu(String name) {
		try {
			Query query = em.createQuery("FROM Menu m WHERE m.name=:name");
			query.setParameter("name", name);
			Menu menu = (Menu)query.getSingleResult();
			System.out.println("RestaurantManagementBean - getMenu");
			return menu;
		} catch (Exception e) {
			System.out.println("RestaurantManagementBean - getMenu failed");
			return null;
		}
	}

	@Override
	public void updateMenu(Menu menu, String name, String desc, float price, Restaurant rest) {
		menu.setName(name);
		menu.setDescription(desc);
		menu.setPrice(price);
		menu.setRestaurant(rest);
		System.out.println("RestaurantManagementBean - updateMenu");
		em.merge(menu);
	}
	
	@Override
	public void removeMenu(long menuId) {
		Query query = em.createQuery("FROM Menu m WHERE m.id=:menuId");
		query.setParameter("menuId", menuId);
		Menu menu = (Menu)query.getSingleResult();
		System.out.println("RestaurantManagementBean - removeMenu");
		em.remove(menu);
	}
	

}
