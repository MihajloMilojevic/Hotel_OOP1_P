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
public class Admin extends Employee {
	public static final UserRole ROLE = UserRole.ADMIN;

	public Admin() {
		super(ROLE);
	}
	/**
	 * @param role
	 * @param name
	 * @param surname
	 * @param gender
	 * @param birthdate
	 * @param phone
	 * @param address
	 * @param username
	 * @param password
	 * @param levelOfProfessionalEducation
	 * @param yearsOfWorkExperience
	 * @param salary
	 */
	public Admin(String name, String surname, String gender, Date birthdate, String phone, String address,
			String username, String password, String levelOfProfessionalEducation, int yearsOfWorkExperience,
			double salary) {
		super(ROLE, name, surname, gender, birthdate, phone, address, username, password, levelOfProfessionalEducation,
				yearsOfWorkExperience, salary);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param role
	 * @param name
	 * @param surname
	 * @param gender
	 * @param birthdate
	 * @param phone
	 * @param address
	 * @param username
	 * @param password
	 * @param levelOfProfessionalEducation
	 * @param yearsOfWorkExperience
	 * @param salary
	 */
	public Admin(String name, String surname, Gender gender, Date birthdate, String phone, String address,
			String username, String password, String levelOfProfessionalEducation, int yearsOfWorkExperience,
			double salary) {
		super(ROLE, name, surname, gender, birthdate, phone, address, username, password, levelOfProfessionalEducation,
				yearsOfWorkExperience, salary);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Admin clone() {
        return new Admin(
        		this.getName(), 
        		this.getSurname(), 
        		this.getGender(),
        		this.getBirthdate(), 
        		this.getPhone(), 
        		this.getAddress(), 
        		this.getUsername(), 
        		this.getPassword(), 
        		this.getLevelOfProfessionalEducation(), 
        		this.getYearsOfWorkExperience(), 
        		this.getSalary()
        	);
    }
}
