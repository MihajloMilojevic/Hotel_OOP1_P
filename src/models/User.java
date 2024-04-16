/**
 * 
 */
package models;

import java.util.Date;

import models.enums.Gender;
import models.enums.UserRole;

public abstract class User extends Model {

	protected String name;
	protected String surname;
	protected Gender gender;
	protected Date birthdate;
	protected String phone;
	protected String address;
	protected String username;
	protected String password;
	protected UserRole role;

	public User(UserRole role) {
		this.role = role;
		this.name = "";
		this.surname = "";
		this.gender = Gender.MALE;
		this.birthdate = new Date();
		this.phone = "";
		this.address = "";
		this.username = "";
		this.password = "";
	}
	/**
	 * @param name
	 * @param surname
	 * @param gender
	 * @param birthdate
	 * @param phone
	 * @param address
	 * @param username
	 * @param password
	 */
	public User(UserRole role, String name, String surname, String gender, Date birthdate, String phone, String address,
			String username, String password) {
		this.role = role;
		this.name = name;
		this.surname = surname;
		this.gender = Gender.valueOf(gender);
		this.birthdate = birthdate;
		this.phone = phone;
		this.address = address;
		this.username = username;
		this.password = password;
	}

	/**
	 * @param name
	 * @param surname
	 * @param gender
	 * @param birthdate
	 * @param phone
	 * @param address
	 * @param username
	 * @param password
	 */
	public User(UserRole role, String name, String surname, Gender gender, Date birthdate, String phone, String address,
			String username, String password) {
		this.role = role;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.birthdate = birthdate;
		this.phone = phone;
		this.address = address;
		this.username = username;
		this.password = password;
	}

	@Override
	public Object get(String key) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		switch (key) {
		case "name":
			return (Object) getName();
		case "surname":
			return (Object) getSurname();
		case "gender":
			return (Object) getGender();
		case "birthdate":
			return (Object) getBirthdate();
		case "phone":
			return (Object) getPhone();
		case "address":
			return (Object) getAddress();
		case "username":
			return (Object) getUsername();
		case "password":
			return (Object) getPassword();
		case "role":
			return (Object) getRole();
		default:
			throw new IllegalArgumentException(key + " is not a valid key");
		}
	}

	@Override
	public void set(String key, Object value) throws IllegalArgumentException {
		switch (key) {
		case "name":
			setName((String) value);
			break;
		case "surname":
			setSurname((String) value);
			break;
		case "gender":
			setGender((Gender) value);
			break;
		case "birthdate":
			setBirthdate((Date) value);
			break;
		case "phone":
			setPhone((String) value);
			break;
		case "address":
			setAddress((String) value);
			break;
		case "username":
			setUsername((String) value);
			break;
		case "password":
			setPassword((String) value);
			break;
		default:
			throw new IllegalArgumentException(key + " is not a valid key");
		}
	}

	@Override
	public String toString() {
		return String.join(";", new String[] { getRole().toString(), getName(), getSurname(), getGender().toString(),
				getBirthdate().toString(), getPhone(), getAddress(), getUsername(), getPassword() });
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		User user = (User) obj;
		return (
				this.getRole().equals(user.getRole()) &&
				this.getUsername().equals(user.getUsername()) &&
				this.getPassword().equals(user.getPassword()) &&
				this.getRole().equals(user.getRole()) &&
				this.getName().equals(user.getName()) &&
				this.getSurname().equals(user.getSurname()) &&
				this.getGender().equals(user.getGender()) &&
				this.getBirthdate().equals(user.getBirthdate()) &&
				this.getPhone().equals(user.getPhone()) &&
				this.getAddress().equals(user.getAddress())
			);
	}

	/**
	 * @return the role
	 */
	public UserRole getRole() {
		return role;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @return the gender
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * @return the birthdate
	 */
	public Date getBirthdate() {
		return birthdate;
	}

	/**
	 * @param birthdate the birthdate to set
	 */
	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
