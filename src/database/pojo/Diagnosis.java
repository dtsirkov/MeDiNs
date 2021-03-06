package database.pojo;

// Generated Apr 30, 2015 6:48:37 PM by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

/**
 * Diagnosis generated by hbm2java
 */
public class Diagnosis implements java.io.Serializable {

	private Integer id;
	private String mkb;
	private String name;
	private String description;
	private Set<MedicalReport> medicalReports = new HashSet<MedicalReport>(0);

	public Diagnosis() {
	}

	public Diagnosis(String name) {
		this.name = name;
	}

	public Diagnosis(String mkb, String name, String description,
			Set<MedicalReport> medicalReports) {
		this.mkb = mkb;
		this.name = name;
		this.description = description;
		this.medicalReports = medicalReports;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMkb() {
		return this.mkb;
	}

	public void setMkb(String mkb) {
		this.mkb = mkb;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<MedicalReport> getMedicalReports() {
		return this.medicalReports;
	}

	public void setMedicalReports(Set<MedicalReport> medicalReports) {
		this.medicalReports = medicalReports;
	}

}
