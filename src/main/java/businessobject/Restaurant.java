package businessobject;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;


@Entity
@Table(name="Restaurant")
public class Restaurant {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name="name_restaurant")
	private String name_restaurant;
	@Column(name="address")
	private String address;
	@Column(name="postcode")
	private int postcode;
	@Column(name="country")
	private String country;

	// relations : FetchType Eager by default
	@ManyToOne(cascade = CascadeType.ALL)
	private Owner owner;
	
	// add relations : FetchType Lazy by default
	@OneToMany (mappedBy = "restaurant", cascade = CascadeType.ALL)
	List<Menu> menus;
	

	//constructors
	public Restaurant(){
		this.menus = new ArrayList<Menu>();
		
	}

	public Restaurant(String name_restaurant, String address, int postcode, String country, Owner owner) {
		super();
		this.name_restaurant = name_restaurant;
		this.address = address;
		this.postcode = postcode;
		this.country = country;
		this.owner = owner;
		this.menus = new ArrayList<Menu>();
		
	}

	public Restaurant(String name_restaurant, String address, int postcode, String country) {
		this.name_restaurant = name_restaurant;
		this.address = address;
		this.postcode = postcode;
		this.country = country;
		this.menus = new ArrayList<Menu>();
		
	}
	
	//add objects to lists
	public void addMenu(Menu menu){
		menu.setRestaurant(this);
		this.menus.add(menu);
	}
	
	
	//getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName_restaurant() {
		return name_restaurant;
	}

	public void setName_restaurant(String name_restaurant) {
		this.name_restaurant = name_restaurant;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getPostcode() {
		return postcode;
	}

	public void setPostcode(int postcode) {
		this.postcode = postcode;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "Restaurant [id=" + id + ", name_restaurant=" + name_restaurant + ", address=" + address + ", postcode="
				+ postcode + ", country=" + country + ", owner=" + owner + ", menus=" + menus 
				+ "]";
	}
}