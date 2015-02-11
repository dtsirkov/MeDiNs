package pojo.classes;

// Generated May 18, 2014 7:33:38 PM by Hibernate Tools 4.0.0

import java.util.Date;

/**
 * Invoices generated by hbm2java
 */
public class Invoices implements java.io.Serializable {

	private Integer id;
	private MedicalCases medicalCases;
	private int number;
	private Date date;
	private int invoicedBy;
	private int invoicedTo;
	private float policyExcess;
	private float dueAmount;
	private String storagePath;

	public Invoices() {
	}

	public Invoices(MedicalCases medicalCases, int number, Date date,
			int invoicedBy, int invoicedTo, float policyExcess,
			float dueAmount, String storagePath) {
		this.medicalCases = medicalCases;
		this.number = number;
		this.date = date;
		this.invoicedBy = invoicedBy;
		this.invoicedTo = invoicedTo;
		this.policyExcess = policyExcess;
		this.dueAmount = dueAmount;
		this.storagePath = storagePath;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public MedicalCases getMedicalCases() {
		return this.medicalCases;
	}

	public void setMedicalCases(MedicalCases medicalCases) {
		this.medicalCases = medicalCases;
	}

	public int getNumber() {
		return this.number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Date getDate() {
		return this.date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getInvoicedBy() {
		return this.invoicedBy;
	}

	public void setInvoicedBy(int invoicedBy) {
		this.invoicedBy = invoicedBy;
	}

	public int getInvoicedTo() {
		return this.invoicedTo;
	}

	public void setInvoicedTo(int invoicedTo) {
		this.invoicedTo = invoicedTo;
	}

	public float getPolicyExcess() {
		return this.policyExcess;
	}

	public void setPolicyExcess(float policyExcess) {
		this.policyExcess = policyExcess;
	}

	public float getDueAmount() {
		return this.dueAmount;
	}

	public void setDueAmount(float dueAmount) {
		this.dueAmount = dueAmount;
	}

	public String getStoragePath() {
		return this.storagePath;
	}

	public void setStoragePath(String storagePath) {
		this.storagePath = storagePath;
	}

}