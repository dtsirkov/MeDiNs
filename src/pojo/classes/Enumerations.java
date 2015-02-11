package pojo.classes;

// Generated May 18, 2014 7:33:38 PM by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

/**
 * Enumerations generated by hbm2java
 */
public class Enumerations implements java.io.Serializable {

	private String code;
	private EnumerationTypes enumerationTypes;
	private Set personsesForTitle = new HashSet(0);
	private Set deceaseses = new HashSet(0);
	private Set contactsesForPrefered = new HashSet(0);
	private Set medicalCasesesForStatus2 = new HashSet(0);
	private Set personsesForRole = new HashSet(0);
	private Set personsesForSex = new HashSet(0);
	private Set enumerationLabelses = new HashSet(0);
	private Set contactsesForActive = new HashSet(0);
	private Set documentations = new HashSet(0);
	private Set organizationses = new HashSet(0);
	private Set personsesForJobTitle = new HashSet(0);
	private Set contactsesForType = new HashSet(0);
	private Set treatmentses = new HashSet(0);
	private Set medicalCasesesForStatus1 = new HashSet(0);
	private Set medicamentses = new HashSet(0);

	public Enumerations() {
	}

	public Enumerations(String code, EnumerationTypes enumerationTypes) {
		this.code = code;
		this.enumerationTypes = enumerationTypes;
	}

	public Enumerations(String code, EnumerationTypes enumerationTypes,
			Set personsesForTitle, Set deceaseses, Set contactsesForPrefered,
			Set medicalCasesesForStatus2, Set personsesForRole,
			Set personsesForSex, Set enumerationLabelses,
			Set contactsesForActive, Set documentations, Set organizationses,
			Set personsesForJobTitle, Set contactsesForType, Set treatmentses,
			Set medicalCasesesForStatus1, Set medicamentses) {
		this.code = code;
		this.enumerationTypes = enumerationTypes;
		this.personsesForTitle = personsesForTitle;
		this.deceaseses = deceaseses;
		this.contactsesForPrefered = contactsesForPrefered;
		this.medicalCasesesForStatus2 = medicalCasesesForStatus2;
		this.personsesForRole = personsesForRole;
		this.personsesForSex = personsesForSex;
		this.enumerationLabelses = enumerationLabelses;
		this.contactsesForActive = contactsesForActive;
		this.documentations = documentations;
		this.organizationses = organizationses;
		this.personsesForJobTitle = personsesForJobTitle;
		this.contactsesForType = contactsesForType;
		this.treatmentses = treatmentses;
		this.medicalCasesesForStatus1 = medicalCasesesForStatus1;
		this.medicamentses = medicamentses;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public EnumerationTypes getEnumerationTypes() {
		return this.enumerationTypes;
	}

	public void setEnumerationTypes(EnumerationTypes enumerationTypes) {
		this.enumerationTypes = enumerationTypes;
	}

	public Set getPersonsesForTitle() {
		return this.personsesForTitle;
	}

	public void setPersonsesForTitle(Set personsesForTitle) {
		this.personsesForTitle = personsesForTitle;
	}

	public Set getDeceaseses() {
		return this.deceaseses;
	}

	public void setDeceaseses(Set deceaseses) {
		this.deceaseses = deceaseses;
	}

	public Set getContactsesForPrefered() {
		return this.contactsesForPrefered;
	}

	public void setContactsesForPrefered(Set contactsesForPrefered) {
		this.contactsesForPrefered = contactsesForPrefered;
	}

	public Set getMedicalCasesesForStatus2() {
		return this.medicalCasesesForStatus2;
	}

	public void setMedicalCasesesForStatus2(Set medicalCasesesForStatus2) {
		this.medicalCasesesForStatus2 = medicalCasesesForStatus2;
	}

	public Set getPersonsesForRole() {
		return this.personsesForRole;
	}

	public void setPersonsesForRole(Set personsesForRole) {
		this.personsesForRole = personsesForRole;
	}

	public Set getPersonsesForSex() {
		return this.personsesForSex;
	}

	public void setPersonsesForSex(Set personsesForSex) {
		this.personsesForSex = personsesForSex;
	}

	public Set getEnumerationLabelses() {
		return this.enumerationLabelses;
	}

	public void setEnumerationLabelses(Set enumerationLabelses) {
		this.enumerationLabelses = enumerationLabelses;
	}

	public Set getContactsesForActive() {
		return this.contactsesForActive;
	}

	public void setContactsesForActive(Set contactsesForActive) {
		this.contactsesForActive = contactsesForActive;
	}

	public Set getDocumentations() {
		return this.documentations;
	}

	public void setDocumentations(Set documentations) {
		this.documentations = documentations;
	}

	public Set getOrganizationses() {
		return this.organizationses;
	}

	public void setOrganizationses(Set organizationses) {
		this.organizationses = organizationses;
	}

	public Set getPersonsesForJobTitle() {
		return this.personsesForJobTitle;
	}

	public void setPersonsesForJobTitle(Set personsesForJobTitle) {
		this.personsesForJobTitle = personsesForJobTitle;
	}

	public Set getContactsesForType() {
		return this.contactsesForType;
	}

	public void setContactsesForType(Set contactsesForType) {
		this.contactsesForType = contactsesForType;
	}

	public Set getTreatmentses() {
		return this.treatmentses;
	}

	public void setTreatmentses(Set treatmentses) {
		this.treatmentses = treatmentses;
	}

	public Set getMedicalCasesesForStatus1() {
		return this.medicalCasesesForStatus1;
	}

	public void setMedicalCasesesForStatus1(Set medicalCasesesForStatus1) {
		this.medicalCasesesForStatus1 = medicalCasesesForStatus1;
	}

	public Set getMedicamentses() {
		return this.medicamentses;
	}

	public void setMedicamentses(Set medicamentses) {
		this.medicamentses = medicamentses;
	}

}