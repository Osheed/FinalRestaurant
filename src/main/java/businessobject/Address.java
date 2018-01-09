package businessobject;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
	
	private String street;
	private int postcode;
	private String city;
	private String country;
	
	//Constructors
	public Address(){
		
	}
	
	//Getters-Setters
	public Address(String street, int postcode, String city, String country) {
		super();
		this.street = street;
		this.postcode = postcode;
		this.city = city;
		this.country = country;
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

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
}
