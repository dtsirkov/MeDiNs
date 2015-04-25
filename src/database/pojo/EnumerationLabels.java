package database.pojo;

// Generated Apr 4, 2015 7:32:07 PM by Hibernate Tools 4.0.0

/**
 * EnumerationLabels generated by hbm2java
 */
public class EnumerationLabels implements java.io.Serializable {

	private Integer id;
	private Enumerations enumerations;
	private String language;
	private String label;

	public EnumerationLabels() {
	}

	public EnumerationLabels(Enumerations enumerations, String language,
			String label) {
		this.enumerations = enumerations;
		this.language = language;
		this.label = label;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Enumerations getEnumerations() {
		return this.enumerations;
	}

	public void setEnumerations(Enumerations enumerations) {
		this.enumerations = enumerations;
	}

	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getLabel() {
		return this.label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}