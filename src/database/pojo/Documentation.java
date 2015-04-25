package database.pojo;

// Generated Apr 4, 2015 7:32:07 PM by Hibernate Tools 4.0.0

/**
 * Documentation generated by hbm2java
 */
public class Documentation implements java.io.Serializable {

	private int id;
	private CaseInfo caseInfo;
	private Enumerations enumerations;
	private String storagePath;

	public Documentation() {
	}

	public Documentation(int id, CaseInfo caseInfo, Enumerations enumerations) {
		this.id = id;
		this.caseInfo = caseInfo;
		this.enumerations = enumerations;
	}

	public Documentation(int id, CaseInfo caseInfo, Enumerations enumerations,
			String storagePath) {
		this.id = id;
		this.caseInfo = caseInfo;
		this.enumerations = enumerations;
		this.storagePath = storagePath;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CaseInfo getCaseInfo() {
		return this.caseInfo;
	}

	public void setCaseInfo(CaseInfo caseInfo) {
		this.caseInfo = caseInfo;
	}

	public Enumerations getEnumerations() {
		return this.enumerations;
	}

	public void setEnumerations(Enumerations enumerations) {
		this.enumerations = enumerations;
	}

	public String getStoragePath() {
		return this.storagePath;
	}

	public void setStoragePath(String storagePath) {
		this.storagePath = storagePath;
	}

}