package businessobject;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Rating")
public class Rating {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name="amount_stars")
	private int amount_stars;
	@Column(name="comment")
	private String comment;
	
	//Relations
	@Embedded
	private Address address;
	
	@ManyToOne
	private Restaurant restaurant;

	public Rating(int amount_stars, String comment, Address address, Restaurant restaurant) {
		super();
		this.amount_stars = amount_stars;
		this.comment = comment;
		this.address = address;
		this.restaurant = restaurant;
	}
	
	public Rating(){
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getAmount_stars() {
		return amount_stars;
	}

	public void setAmount_stars(int amount_stars) {
		this.amount_stars = amount_stars;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	
}
