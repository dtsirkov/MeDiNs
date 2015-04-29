package database.pojo;

// Generated Apr 29, 2015 9:23:50 PM by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * CaseInfo generated by hbm2java
 */
public class CaseInfo implements java.io.Serializable {

	private Integer id;
	private Enumerations enumerationsByStatus2;
	private MedicalReport medicalReport;
	private Persons persons;
	private Policies policies;
	private Enumerations enumerationsByStatus1;
	private Date caseDate;
	private String referanceNumber1;
	private String referanceNumber2;
	private Integer responsiblePerson;
	private Integer createdBy;
	private Integer lastUpdateBy;
	private Float franchise;
	private Set<Services> serviceses = new HashSet<Services>(0);
	private Set<Documentation> documentations = new HashSet<Documentation>(0);
	private Set<Invoices> invoiceses = new HashSet<Invoices>(0);

	public CaseInfo() {
	}

	public CaseInfo(Persons persons, Date caseDate, String referanceNumber1) {
		this.persons = persons;
		this.caseDate = caseDate;
		this.referanceNumber1 = referanceNumber1;
	}

	public CaseInfo(Enumerations enumerationsByStatus2,
			MedicalReport medicalReport, Persons persons, Policies policies,
			Enumerations enumerationsByStatus1, Date caseDate,
			String referanceNumber1, String referanceNumber2,
			Integer responsiblePerson, Integer createdBy, Integer lastUpdateBy,
			Float franchise, Set<Services> serviceses,
			Set<Documentation> documentations, Set<Invoices> invoiceses) {
		this.enumerationsByStatus2 = enumerationsByStatus2;
		this.medicalReport = medicalReport;
		this.persons = persons;
		this.policies = policies;
		this.enumerationsByStatus1 = enumerationsByStatus1;
		this.caseDate = caseDate;
		this.referanceNumber1 = referanceNumber1;
		this.referanceNumber2 = referanceNumber2;
		this.responsiblePerson = responsiblePerson;
		this.createdBy = createdBy;
		this.lastUpdateBy = lastUpdateBy;
		this.franchise = franchise;
		this.serviceses = serviceses;
		this.documentations = documentations;
		this.invoiceses = invoiceses;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Enumerations getEnumerationsByStatus2() {
		return this.enumerationsByStatus2;
	}

	public void setEnumerationsByStatus2(Enumerations enumerationsByStatus2) {
		this.enumerationsByStatus2 = enumerationsByStatus2;
	}

	public MedicalReport getMedicalReport() {
		return this.medicalReport;
	}

	public void setMedicalReport(MedicalReport medicalReport) {
		this.medicalReport = medicalReport;
	}

	public Persons getPersons() {
		return this.persons;
	}

	public void setPersons(Persons persons) {
		this.persons = persons;
	}

	public Policies getPolicies() {
		return this.policies;
	}

	public void setPolicies(Policies policies) {
		this.policies = policies;
	}

	public Enumerations getEnumerationsByStatus1() {
		return this.enumerationsByStatus1;
	}

	public void setEnumerationsByStatus1(Enumerations enumerationsByStatus1) {
		this.enumerationsByStatus1 = enumerationsByStatus1;
	}

	public Date getCaseDate() {
		return this.caseDate;
	}

	public void setCaseDate(Date caseDate) {
		this.caseDate = caseDate;
	}

	public String getReferanceNumber1() {
		return this.referanceNumber1;
	}

	public void setReferanceNumber1(String referanceNumber1) {
		this.referanceNumber1 = referanceNumber1;
	}

	public String getReferanceNumber2() {
		return this.referanceNumber2;
	}

	public void setReferanceNumber2(String referanceNumber2) {
		this.referanceNumber2 = referanceNumber2;
	}

	public Integer getResponsiblePerson() {
		return this.responsiblePerson;
	}

	public void setResponsiblePerson(Integer responsiblePerson) {
		this.responsiblePerson = responsiblePerson;
	}

	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getLastUpdateBy() {
		return this.lastUpdateBy;
	}

	public void setLastUpdateBy(Integer lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public Float getFranchise() {
		return this.franchise;
	}

	public void setFranchise(Float franchise) {
		this.franchise = franchise;
	}

	public Set<Services> getServiceses() {
		return this.serviceses;
	}

	public void setServiceses(Set<Services> serviceses) {
		this.serviceses = serviceses;
	}

	public Set<Documentation> getDocumentations() {
		return this.documentations;
	}

	public void setDocumentations(Set<Documentation> documentations) {
		this.documentations = documentations;
	}

	public Set<Invoices> getInvoiceses() {
		return this.invoiceses;
	}

	public void setInvoiceses(Set<Invoices> invoiceses) {
		this.invoiceses = invoiceses;
	}

}
