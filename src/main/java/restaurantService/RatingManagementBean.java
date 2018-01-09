package restaurantService;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import businessobject.Address;
import businessobject.Rating;
import businessobject.Restaurant;

@Stateless
public class RatingManagementBean implements IRating {

	@PersistenceContext(name = "RestaurantPU")
	private EntityManager em;
	
	@Override
	public List<Rating> getRatingsFromRestaurant() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Rating> getAllRatings() {
		try {
			return em.createQuery("FROM Rating").getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Restaurant> getRestaurants() {
		try {
			return em.createQuery("FROM Restaurant").getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void insertRating(int stars, String comment, Restaurant restaurant, Address address) {
		Rating rating = new Rating(stars, comment, address, restaurant);
		em.persist(rating);
	}

	@Override
	public Restaurant getRestaurant(String name) {
		try {
			Query query = em.createQuery("FROM Restaurant r WHERE r.name_restaurant=:name_restaurant");
			query.setParameter("name_restaurant", name);
			
			Restaurant restaurant = (Restaurant)query.getSingleResult();

			return restaurant;
		} catch (Exception e) {
			return null;
		}
		
	}

}
