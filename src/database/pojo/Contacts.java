package database.pojo;

// Generated Apr 30, 2015 6:48:37 PM by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

/**
 * Contacts generated by hbm2java
 */
public class Contacts implements java.io.Serializable {

	private Integer id;
	private Enumerations enumerationsByPrefered;
	private Enumerations enumerationsByActive;
	private Enumerations enumerationsByType;
	private String address;
	private String zip;
	private String city;
	private String country;
	private String phone;
	private String mobile;
	private String email;
	private Set<Organizations> organizationses = new HashSet<Organizations>(0);
	private Set<Persons> personses = new HashSet<Persons>(0);

	public Contacts() {
	}

	public Contacts(String country) {
		this.country = country;
	}

	public Contacts(Enumerations enumerationsByPrefered,
			Enumerations enumerationsByActive, Enumerations enumerationsByType,
			String address, String zip, String city, String country,
			String phone, String mobile, String email,
			Set<Organizations> organizationses, Set<Persons> personses) {
		this.enumerationsByPrefered = enumerationsByPrefered;
		this.enumerationsByActive = enumerationsByActive;
		this.enumerationsByType = enumerationsByType;
		this.address = address;
		this.zip = zip;
		this.city = city;
		this.country = country;
		this.phone = phone;
		this.mobile = mobile;
		this.email = email;
		this.organizationses = organizationses;
		this.personses = personses;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Enumerations getEnumerationsByPrefered() {
		return this.enumerationsByPrefered;
	}

	public void setEnumerationsByPrefered(Enumerations enumerationsByPrefered) {
		this.enumerationsByPrefered = enumerationsByPrefered;
	}

	public Enumerations getEnumerationsByActive() {
		return this.enumerationsByActive;
	}

	public void setEnumerationsByActive(Enumerations enumerationsByActive) {
		this.enumerationsByActive = enumerationsByActive;
	}

	public Enumerations getEnumerationsByType() {
		return this.enumerationsByType;
	}

	public void setEnumerationsByType(Enumerations enumerationsByType) {
		this.enumerationsByType = enumerationsByType;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Organizations> getOrganizationses() {
		return this.organizationses;
	}

	public void setOrganizationses(Set<Organizations> organizationses) {
		this.organizationses = organizationses;
	}

	public Set<Persons> getPersonses() {
		return this.personses;
	}

	public void setPersonses(Set<Persons> personses) {
		this.personses = personses;
	}

}
