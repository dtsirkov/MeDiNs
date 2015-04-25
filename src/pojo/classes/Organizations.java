package pojo.classes;

// Generated Apr 4, 2015 7:32:07 PM by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

/**
 * Organizations generated by hbm2java
 */
public class Organizations implements java.io.Serializable {

	private Integer number;
	private Enumerations enumerations;
	private String name;
	private String logo;
	private String iban;
	private String bankDetails;
	private Set<Policies> policiesesForInsuaranceCompany = new HashSet<Policies>(
			0);
	private Set<Persons> personses = new HashSet<Persons>(0);
	private Set<Contacts> contactses = new HashSet<Contacts>(0);
	private Set<Policies> policiesesForAssistanceCompany = new HashSet<Policies>(
			0);

	public Organizations() {
	}

	public Organizations(Enumerations enumerations, String name) {
		this.enumerations = enumerations;
		this.name = name;
	}

	public Organizations(Enumerations enumerations, String name, String logo,
			String iban, String bankDetails,
			Set<Policies> policiesesForInsuaranceCompany,
			Set<Persons> personses, Set<Contacts> contactses,
			Set<Policies> policiesesForAssistanceCompany) {
		this.enumerations = enumerations;
		this.name = name;
		this.logo = logo;
		this.iban = iban;
		this.bankDetails = bankDetails;
		this.policiesesForInsuaranceCompany = policiesesForInsuaranceCompany;
		this.personses = personses;
		this.contactses = contactses;
		this.policiesesForAssistanceCompany = policiesesForAssistanceCompany;
	}

	public Integer getNumber() {
		return this.number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Enumerations getEnumerations() {
		return this.enumerations;
	}

	public void setEnumerations(Enumerations enumerations) {
		this.enumerations = enumerations;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogo() {
		return this.logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getIban() {
		return this.iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getBankDetails() {
		return this.bankDetails;
	}

	public void setBankDetails(String bankDetails) {
		this.bankDetails = bankDetails;
	}

	public Set<Policies> getPoliciesesForInsuaranceCompany() {
		return this.policiesesForInsuaranceCompany;
	}

	public void setPoliciesesForInsuaranceCompany(
			Set<Policies> policiesesForInsuaranceCompany) {
		this.policiesesForInsuaranceCompany = policiesesForInsuaranceCompany;
	}

	public Set<Persons> getPersonses() {
		return this.personses;
	}

	public void setPersonses(Set<Persons> personses) {
		this.personses = personses;
	}

	public Set<Contacts> getContactses() {
		return this.contactses;
	}

	public void setContactses(Set<Contacts> contactses) {
		this.contactses = contactses;
	}

	public Set<Policies> getPoliciesesForAssistanceCompany() {
		return this.policiesesForAssistanceCompany;
	}

	public void setPoliciesesForAssistanceCompany(
			Set<Policies> policiesesForAssistanceCompany) {
		this.policiesesForAssistanceCompany = policiesesForAssistanceCompany;
	}

}
