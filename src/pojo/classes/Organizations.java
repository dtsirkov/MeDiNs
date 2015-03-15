package pojo.classes;

// Generated May 18, 2014 7:33:38 PM by Hibernate Tools 4.0.0

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
	private Set personOrganizationLinks = new HashSet(0);
	private Set organizationContactLinks = new HashSet(0);
	private Set policiesesForInsuaranceCompany = new HashSet(0);
	private Set policiesesForAssistanceCompany = new HashSet(0);

	public Organizations() {
	}

	public Organizations(Enumerations enumerations, String name) {
		this.enumerations = enumerations;
		this.name = name;
	}

	public Organizations(Enumerations enumerations, String name,
			String logo, String iban, String bankDetails,
			Set personOrganizationLinks, Set organizationContactLinks,
			Set policiesesForInsuaranceCompany,
			Set policiesesForAssistanceCompany) {
		this.enumerations = enumerations;
		this.name = name;
		this.logo = logo;
		this.iban = iban;
		this.bankDetails = bankDetails;
		this.personOrganizationLinks = personOrganizationLinks;
		this.organizationContactLinks = organizationContactLinks;
		this.policiesesForInsuaranceCompany = policiesesForInsuaranceCompany;
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

	public Set getPersonOrganizationLinks() {
		return this.personOrganizationLinks;
	}

	public void setPersonOrganizationLinks(Set personOrganizationLinks) {
		this.personOrganizationLinks = personOrganizationLinks;
	}

	public Set getOrganizationContactLinks() {
		return this.organizationContactLinks;
	}

	public void setOrganizationContactLinks(Set organizationContactLinks) {
		this.organizationContactLinks = organizationContactLinks;
	}

	public Set getPoliciesesForInsuaranceCompany() {
		return this.policiesesForInsuaranceCompany;
	}

	public void setPoliciesesForInsuaranceCompany(
			Set policiesesForInsuaranceCompany) {
		this.policiesesForInsuaranceCompany = policiesesForInsuaranceCompany;
	}

	public Set getPoliciesesForAssistanceCompany() {
		return this.policiesesForAssistanceCompany;
	}

	public void setPoliciesesForAssistanceCompany(
			Set policiesesForAssistanceCompany) {
		this.policiesesForAssistanceCompany = policiesesForAssistanceCompany;
	}

}
