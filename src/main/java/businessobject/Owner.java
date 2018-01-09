package businessobject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name="Owner")
public class Owner {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name="lastname")
	private String lastname;
	@Column(name="firstname")
	private String firstname;
	@Column(name="password")
	private String password;
	@Column(name="phone")
	private String phone;
	@Column(name="email")
	private String email;
	
	// relations : FetchType Lazy by default
	@OneToMany(mappedBy="owner",cascade = CascadeType.ALL)
	@OrderBy("name_restaurant")
	List<Restaurant> restaurants;

	//constructors
	public Owner(){
		this.restaurants = new ArrayList<Restaurant>();
	}

	public Owner(String lastname, String firstname, String password, String phone, String email) {
		super();
		this.lastname = lastname;
		this.firstname = firstname;
		this.password = password;
		this.phone = phone;
		this.email = email;
		this.restaurants = new ArrayList<Restaurant>();
	}
	
	//helper methods
	public void addRestaurant(Restaurant restaurant){
		restaurant.setOwner(this);
		this.restaurants.add(restaurant);
	}

	//getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	@Override
	public String toString() {
		return "Owner [id=" + id + ", lastname=" + lastname + ", firstname=" + firstname + ", password=" + password
				+ ", phone=" + phone + ", email=" + email + ", restaurants=" + restaurants + "]";
	}
}
