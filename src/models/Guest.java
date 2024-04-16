/**
 * 
 */
package models;

import java.util.Date;

import models.enums.Gender;
import models.enums.UserRole;

/**
 * 
 */
public final class Guest extends User {
	public static final UserRole ROLE = UserRole.GUEST;

	public Guest() {
		super(ROLE);
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
	public Guest(String name, String surname, String gender, Date birthdate, String phone, String address,
			String username, String password) {
		super(ROLE, name, surname, gender, birthdate, phone, address, username, password);
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
	public Guest(String name, String surname, Gender gender, Date birthdate, String phone, String address,
			String username, String password) {
		super(ROLE, name, surname, gender, birthdate, phone, address, username, password);
	}
	
	@Override
	public Guest clone() {
        return new Guest(
        	getName(),
        	getSurname(),
        	getGender(),
        	getBirthdate(),
        	getPhone(),
        	getAddress(),
        	getUsername(),
        	getPassword()
    	);
	}
}
