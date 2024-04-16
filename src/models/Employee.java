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
public abstract class Employee extends User {
	protected String levelOfProfessionalEducation;
	protected int yearsOfWorkExperience;
	protected double salary;

	public Employee(UserRole role) {
		super(role);
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
	public Employee(UserRole role, String name, String surname, String gender, Date birthdate, String phone, String address,
			String username, String password, String levelOfProfessionalEducation, int yearsOfWorkExperience, double salary) {
		super(role, name, surname, gender, birthdate, phone, address, username, password);
		this.levelOfProfessionalEducation = levelOfProfessionalEducation;
		this.yearsOfWorkExperience = yearsOfWorkExperience;
		this.salary = salary;
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
	public Employee(UserRole role, String name, String surname, Gender gender, Date birthdate, String phone, String address,
			String username, String password, String levelOfProfessionalEducation, int yearsOfWorkExperience, double salary) {
		super(role, name, surname, gender, birthdate, phone, address, username, password);
		this.levelOfProfessionalEducation = levelOfProfessionalEducation;
		this.yearsOfWorkExperience = yearsOfWorkExperience;
		this.salary = salary;
	}
	
	@Override
	public Object get(String key) throws IllegalArgumentException {
		switch (key) {
		case "levelOfProfessionalEducation":
			return getLevelOfProfessionalEducation();
		case "yearsOfWorkExperience":
			return getYearsOfWorkExperience();
		case "salary":
			return getSalary();
		default:
			return super.get(key);
		}
	}
	@Override
	public void set(String key, Object value) throws IllegalArgumentException {
		switch (key) {
		case "levelOfProfessionalEducation":
			setLevelOfProfessionalEducation((String) value);
			break;
		case "yearsOfWorkExperience":
			setYearsOfWorkExperience((int) value);
			break;
		case "salary":
			setSalary((double) value);
			break;
		default:
			super.set(key, value);
			break;
		}
	}

	public String toString() {
		return String.join(";", new String[] {
			super.toString(),
			getLevelOfProfessionalEducation(),
			Integer.toString(getYearsOfWorkExperience()),
			Double.toString(getSalary())
		});
	}
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}
		Employee employee = (Employee) obj;
		return super.equals(employee)
				&& getLevelOfProfessionalEducation().equals(employee.getLevelOfProfessionalEducation())
				&& getYearsOfWorkExperience() == employee.getYearsOfWorkExperience()
				&& getSalary() == employee.getSalary();
	}
	/**
	 * @return the levelOfProfessionalEducation
	 */
	public String getLevelOfProfessionalEducation() {
		return levelOfProfessionalEducation;
	}

	/**
	 * @param levelOfProfessionalEducation the levelOfProfessionalEducation to set
	 */
	public void setLevelOfProfessionalEducation(String levelOfProfessionalEducation) {
		this.levelOfProfessionalEducation = levelOfProfessionalEducation;
	}

	/**
	 * @return the yearsOfWorkExperience
	 */
	public int getYearsOfWorkExperience() {
		return yearsOfWorkExperience;
	}

	/**
	 * @param yearsOfWorkExperience the yearsOfWorkExperience to set
	 */
	public void setYearsOfWorkExperience(int yearsOfWorkExperience) {
		this.yearsOfWorkExperience = yearsOfWorkExperience;
	}

	/**
	 * @return the salary
	 */
	public double getSalary() {
		return salary;
	}

	/**
	 * @param salary the salary to set
	 */
	public void setSalary(double salary) {
		this.salary = salary;
	}

}
