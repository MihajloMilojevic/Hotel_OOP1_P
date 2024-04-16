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
public class Receptionist extends Employee {
	public static final UserRole ROLE = UserRole.RECEPTIONIST;

	public Receptionist() {
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
	public Receptionist(String name, String surname, String gender, Date birthdate, String phone,
			String address, String username, String password, String levelOfProfessionalEducation,
			int yearsOfWorkExperience, double salary) {
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
	public Receptionist(String name, String surname, Gender gender, Date birthdate, String phone,
			String address, String username, String password, String levelOfProfessionalEducation,
			int yearsOfWorkExperience, double salary) {
		super(ROLE, name, surname, gender, birthdate, phone, address, username, password, levelOfProfessionalEducation,
				yearsOfWorkExperience, salary);
		// TODO Auto-generated constructor stub
	}
	@Override
	public Receptionist clone() {
		return new Receptionist(getName(), getSurname(), getGender(), getBirthdate(), getPhone(), getAddress(),
				getUsername(), getPassword(), getLevelOfProfessionalEducation(), getYearsOfWorkExperience(),
				getSalary());
	}
}
