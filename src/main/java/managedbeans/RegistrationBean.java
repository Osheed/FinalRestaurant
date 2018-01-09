package managedbeans;

import java.util.List;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import businessobject.Menu;
import businessobject.Owner;
import businessobject.Rating;
import businessobject.Restaurant;
import restaurantService.IManagement;

public class RegistrationBean {

	//NavigationRule
	private String navigateTo;
	
	//ManageData
	private String ownerTitleLabel;
	private List<String> restaurantNames;
	
	//Objects
	private IManagement manager;
	private Owner owner;
	private Menu menu;
	private Restaurant restaurant;
	private String sourceRestaurantName;
	
	//Data for new User
	private String lastname;
	private String firstname;
	private String password;
	private String phone;
	private String confirmPassword;
	private String email;
	
	//Data for RegisterUser
	private String emailLogin;
	private String passwordLogin;
	
	//Data for Restaurant
	private List<Restaurant> restaurants;
	private String addressR;
	private String countryR;
	private String nameR;
	private int postcodeR;
	
	//Data for Menu
	private List<Menu> menus;
	private String nameM;
	private String descriptionM;
	private float priceM;
	private Restaurant restaurantM;
		
	//Informations
	private String loginInformation="";
	private String registerInformation="";
	private String newRestaurantInformation="";
	private String manageDataInformation="";
	private String manageMenuInformation="";
	
	@PostConstruct
	public void initialize() throws NamingException {
		// use JNDI to inject reference to bank EJB
		InitialContext ctx = new InitialContext();
		manager = (IManagement) ctx.lookup("java:global/TP12-WEB-EJB-PC-EPC-E-0.0.1-SNAPSHOT/RestaurantManagementBean!restaurantService.IManagement");
				
		restaurants = manager.getRestaurants();
		System.out.println("Test initialise in RegistrationBean,Test initialise in RegistrationBean");
	}
	
	/*
	 *Manage Login, Logout and Registration 
	 */
	public String login(){
		try {
			if (isEmptyLoginData()) {
				this.loginInformation = "Please insert all fields";
				navigateTo = "welcomePage";
			} 
			Owner ownerTemp = this.manager.login(this.emailLogin, this.passwordLogin);

			if(ownerTemp == null){
				this.loginInformation = "Wrong username or password";
				navigateTo = "welcomePage";
			}
			else {
				owner = ownerTemp;
				navigateTo = "manageData";
				System.out.println( "In the login method i have "+owner.getFirstname()+" "+owner.getLastname());
				ownerTitleLabel = "Welcome "+owner.getFirstname()+" "+owner.getLastname();
				lastname = owner.getLastname();
				firstname = owner.getFirstname();
				phone = owner.getFirstname();
				email= owner.getFirstname();
				restaurants = getRestaurants();
				isRestaurantInDB();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.emailLogin = "";
		return this.navigateTo;
	}
	public String toRateRestaurants(){
		if(restaurants.size() == 0){
		loginInformation = "No Available Restaurant to Rate";
			return navigateTo = "welcomePage";
		}
		return navigateTo = "rateRestaurant";
	}
	
	public String logout(){
		this.owner = null;
		this.restaurant = null;
		this.restaurants = null;
		//resetValueMenuNull();
		//resetValueNull();
		return "welcomePage";
	}
	
	private boolean isEmptyLoginData(){
		if(this.emailLogin.isEmpty() || this.emailLogin.trim().equals("") || this.passwordLogin.isEmpty() || this.passwordLogin.trim().equals("")) {return true;}
		return false;
	}
	
	public boolean duplicateEmail(){
		Owner check = manager.getOwner(email);
		System.out.println("Methodd duplicateEmail : Value for check : "+check+".");
		if(check!=null)return true;
		return false;
	}
	
	public String registration(){
		try{
			if(registrationEmptyValues()){
				registerInformation = "Please insert all field to register";
				navigateTo = "register";
				return navigateTo;
			}
			if(!password.equals(confirmPassword)){
				registerInformation = "Both passwords must be the same";
				navigateTo ="register";
				return navigateTo;
			}
			if(duplicateEmail()){
				registerInformation ="Another user uses this email";
				email = "";
				navigateTo = "register";
				return navigateTo;
			}else{
				manager.registerOwner(this.lastname, this.firstname, this.password, this.phone, this.email);
				loginInformation = "You are Register Successfully - Enter your credentials";	
				navigateTo = "welcomePage";
				cleanRegistrationForm();
			}
		}
		catch(Exception e){
			System.out.println("Registration Bean - Registration failed");
			e.printStackTrace();
		}
		return navigateTo;
	}
	
	public boolean registrationEmptyValues(){
		List<String> values = new ArrayList<String>();
		values.add(this.email);
		values.add(this.firstname);
		values.add(this.lastname);
		values.add(this.password);
		values.add(this.confirmPassword);
		values.add(this.phone);
		
		for(String value: values){
			if(value.isEmpty() || value.trim().equals("")||value.contains(" ")){
				System.out.println("il y a dans values : "+value +".");
				return true;
			}
		}
		return false;
	}
	
	
	/*
	 *Clean the views after usage 
	 */
	public void cleanRegistrationForm() {
		this.lastname = "";
		this.firstname = "";
		this.phone = "";
		this.email = "";
	}
	
	/*
	 *set the current restaurant to null for adding a new one 
	 */
	public String addRestaurantPage() {
		this.restaurant = null;
		navigateTo = "addRestaurant";
		return navigateTo;
	}
	
	/*
	 * Check if lists are empty
	 */	
	public Boolean isMenuInDB(){
		if(menus.size() == 0){
			manageMenuInformation = "You need to Add a Menu to see this list";
			return false;
		}
		return true;
	}
	
	private void resetValueRestaurantNull(){
		this.nameR = "";
		this.addressR = "";
		this.postcodeR = 0;
		this.countryR = "";
	}

	private void resetValueMenuNull(){
		this.nameM = "";
		this.descriptionM = "";
		this.priceM = 0f;
		this.menu = null;
		this.loginInformation = "";
	}
	
	/*
	 * Managing the Restaurants
	 */
	public String registerNewRestaurant(){
		if(this.restaurant != null) {
			manager.updateRestaurant(this.restaurant, this.nameR, this.addressR, this.postcodeR, this.countryR);
			newRestaurantInformation = "Restaurant Successfully Updated";
			resetValueRestaurantNull();
		} else {
			manager.registerRestaurant(addressR, countryR, nameR, postcodeR, this.owner);
			newRestaurantInformation = "New Restaurant Successfully Created ";
			resetValueRestaurantNull();
		}	
		
		navigateTo = "manageData";
		//restaurants.clear();
		return navigateTo;
	}

	//this method updates the list of the restaurants (names) for the combobox
	public void updateRestaurants(ValueChangeEvent event) {
		this.sourceRestaurantName = (String)event.getNewValue();
    	
	    List<Restaurant> rest = getRestaurants();
	    this.restaurantNames.clear();
	    this.restaurantNames = new ArrayList<String>();
		for (Restaurant r : rest) {
			this.restaurantNames.add(r.getName_restaurant());
		}
    }
	
	public String editRestaurant(Restaurant r){
		this.restaurant = r;

		// set data on variables of the menu to display
		this.nameR = r.getName_restaurant();
		this.addressR = r.getAddress();
		this.postcodeR = r.getPostcode();
		this.countryR = r.getCountry();

		navigateTo = "addRestaurant";
		return navigateTo;
	}

	public String removeRestaurant(Restaurant r){
		this.manager.removeRestaurant(r.getId());
		return null;
	}
	
	/*
	 * Managing the Menus
	 */
	public String saveMenu(){
		//get restaurant from combo
		Restaurant r = manager.getRestaurant(this.sourceRestaurantName);
		if (menu != null) {
			manager.updateMenu(menu, this.nameM, this.descriptionM, this.priceM, r);
			resetValueMenuNull();
		}else{
			manager.addMenu(this.nameM, this.descriptionM, this.priceM, r);
			resetValueMenuNull();
			//menus = getMenus();
			this.menus = this.manager.getMenus();
		}

		navigateTo = "manageMenus";
		return navigateTo;
	}

	public String editMenu(Menu m){
		// specific menu selected in the list or user wants to add new menu
		this.menu = m;

		// set data on variables of the menu to display
		this.nameM = m.getName();
		this.descriptionM = m.getDescription();
		this.priceM = m.getPrice();

		navigateTo = "addMenu";
		return navigateTo;
	}
	
	public String removeMenu(Menu m){
		this.manager.removeMenu(m.getId());
		return null;
	}
	
    /*
     * NavigationRule: Method to navigate 
     */
	public String details(){
		// get restaurants
		List<Restaurant> restaurantList = manager.getRestaurants();
		this.restaurantNames = new ArrayList<String>();
		for (Restaurant r : restaurantList) {
			this.restaurantNames.add(r.getName_restaurant());
		}
		
		System.out.println("value of is RestaurantinDB() : "+isRestaurantInDB());
		if(isRestaurantInDB()){
			navigateTo = "manageMenus";
		}
		else{
			navigateTo = "manageData";
		}
		return navigateTo;
	}
	
	public Boolean isRestaurantInDB(){
		System.out.println("Is Restaurant in the DB size : "+restaurants.size());
		if(restaurants.size() == 0){
			System.out.println("The size of the Restaurants in DB : "+restaurants.size());
			manageDataInformation = "You need to Add a Restaurant to see this list";
			return false;
		}
		manageDataInformation = "";
		return true;
	}
	
    public String getNavigateTo() {
		return navigateTo;
	}
    
	public void setNavigateTo(String navigateTo) {
		this.navigateTo = navigateTo;
	}
	
	public String cancel(){
		// delete data from variables
		resetValueMenuNull();
		return navigateTo = "manageMenus";
	}
	
	public String returnRegister(){
		navigateTo = "welcomePage";
		return navigateTo;
	}
	
	/*
	 * Getters & Setters
	 */
	public List<Restaurant> getRestaurants() {
		this.restaurants = this.manager.getRestaurants();
		List<Restaurant> ownersRestaurants = new ArrayList<>(); 
		for (Restaurant restaurant : restaurants) {
			if(restaurant.getOwner().getId().equals(this.owner.getId())) {
				ownersRestaurants.add(restaurant);
			}
		}
		return ownersRestaurants;
	}

	public void setRestaurants(List<Restaurant> restaurants) {
		this.restaurants = restaurants;
	}
	
	public List<Menu> getMenus() {
		this.menus = this.manager.getMenus();
		/*List<Menu> restaurantsMenu = new ArrayList<>();
		for(Menu menu : menus){
			if(menu.getRestaurant().getId().equals(this.restaurant.getId())){
				restaurantsMenu.add(menu);
			}
		}
		return restaurantsMenu;*/
		return this.menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
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
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailLogin() {
		return emailLogin;
	}

	public void setEmailLogin(String emailLogin) {
		this.emailLogin = emailLogin;
	}

	public String getPasswordLogin() {
		return passwordLogin;
	}

	public void setPasswordLogin(String passwordLogin) {
		this.passwordLogin = passwordLogin;
	}

	public String getLoginInformation() {
		return loginInformation;
	}

	public void setLoginInformation(String loginInformation) {
		this.loginInformation = loginInformation;
	}

	public String getOwnerTitleLabel() {
		return ownerTitleLabel;
	}

	public void setOwnerTitleLabel(String ownerTitleLabel) {
		this.ownerTitleLabel = ownerTitleLabel;
	}

	public String getNewRestaurantInformation() {
		return newRestaurantInformation;
	}

	public void setNewRestaurantInformation(String newRestaurantInformation) {
		this.newRestaurantInformation = newRestaurantInformation;
	}

	public String getRegisterInformation() {
		return registerInformation;
	}

	public void setRegisterInformation(String registerInformation) {
		this.registerInformation = registerInformation;
	}

	public String getAddressR() {
		return addressR;
	}

	public void setAddressR(String addressR) {
		this.addressR = addressR;
	}

	public String getCountryR() {
		return countryR;
	}

	public void setCountryR(String countryR) {
		this.countryR = countryR;
	}

	public String getNameR() {
		return nameR;
	}

	public void setNameR(String nameR) {
		this.nameR = nameR;
	}

	public int getPostcodeR() {
		return postcodeR;
	}

	public void setPostcodeR(int postcodeR) {
		this.postcodeR = postcodeR;
	}

	public IManagement getManager() {
		return manager;
	}

	public void setManager(IManagement manager) {
		this.manager = manager;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}
	
	public String getNameM() {
		return nameM;
	}

	public void setNameM(String nameM) {
		this.nameM = nameM;
	}

	public String getDescriptionM() {
		return descriptionM;
	}

	public void setDescriptionM(String descriptionM) {
		this.descriptionM = descriptionM;
	}

	public float getPriceM() {
		return priceM;
	}

	public void setPriceM(float priceM) {
		this.priceM = priceM;
	}
	
	public Restaurant getRestaurantM() {
		return restaurantM;
	}

	public void setRestaurantM(Restaurant restaurantM) {
		this.restaurantM = restaurantM;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public String getManageDataInformation() {
		return manageDataInformation;
	}

	public void setManageDataInformation(String manageDataInformation) {
		this.manageDataInformation = manageDataInformation;
	}

	public List<String> getRestaurantNames() {
		return restaurantNames;
	}

	public void setRestaurantNames(List<String> restaurantNames) {
		this.restaurantNames = restaurantNames;
	}

	public String getSourceRestaurantName() {
		return sourceRestaurantName;
	}

	public void setSourceRestaurantName(final String sourceRestaurantName) {
		this.sourceRestaurantName = sourceRestaurantName;
	}

	public String getManageMenuInformation() {
		return manageMenuInformation;
	}

	public void setManageMenuInformation(String manageMenuInformation) {
		this.manageMenuInformation = manageMenuInformation;
	}
		
}
