package pojo.classes;

// Generated May 18, 2014 7:33:38 PM by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * MedicalCases generated by hbm2java
 */
public class MedicalCases implements java.io.Serializable {

	private Integer id;
	private Enumerations enumerationsByStatus2;
	private CaseInfo caseInfo;
	private Persons persons;
	private Policies policies;
	private Enumerations enumerationsByStatus1;
	private Date caseDate;
	private int referanceNumber1;
	private int referanceNumber2;
	private Set invoiceses = new HashSet(0);

	public MedicalCases() {
	}

	public MedicalCases(Enumerations enumerationsByStatus2, CaseInfo caseInfo,
			Persons persons, Policies policies,
			Enumerations enumerationsByStatus1, Date caseDate,
			int referanceNumber1, int referanceNumber2) {
		this.enumerationsByStatus2 = enumerationsByStatus2;
		this.caseInfo = caseInfo;
		this.persons = persons;
		this.policies = policies;
		this.enumerationsByStatus1 = enumerationsByStatus1;
		this.caseDate = caseDate;
		this.referanceNumber1 = referanceNumber1;
		this.referanceNumber2 = referanceNumber2;
	}

	public MedicalCases(Enumerations enumerationsByStatus2, CaseInfo caseInfo,
			Persons persons, Policies policies,
			Enumerations enumerationsByStatus1, Date caseDate,
			int referanceNumber1, int referanceNumber2, Set invoiceses) {
		this.enumerationsByStatus2 = enumerationsByStatus2;
		this.caseInfo = caseInfo;
		this.persons = persons;
		this.policies = policies;
		this.enumerationsByStatus1 = enumerationsByStatus1;
		this.caseDate = caseDate;
		this.referanceNumber1 = referanceNumber1;
		this.referanceNumber2 = referanceNumber2;
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

	public CaseInfo getCaseInfo() {
		return this.caseInfo;
	}

	public void setCaseInfo(CaseInfo caseInfo) {
		this.caseInfo = caseInfo;
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

	public int getReferanceNumber1() {
		return this.referanceNumber1;
	}

	public void setReferanceNumber1(int referanceNumber1) {
		this.referanceNumber1 = referanceNumber1;
	}

	public int getReferanceNumber2() {
		return this.referanceNumber2;
	}

	public void setReferanceNumber2(int referanceNumber2) {
		this.referanceNumber2 = referanceNumber2;
	}

	public Set getInvoiceses() {
		return this.invoiceses;
	}

	public void setInvoiceses(Set invoiceses) {
		this.invoiceses = invoiceses;
	}

}