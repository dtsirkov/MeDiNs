package pojo_classes;

// Generated May 18, 2014 7:33:38 PM by Hibernate Tools 4.0.0

/**
 * PersonOrganizationLink generated by hbm2java
 */
public class PersonOrganizationLink implements java.io.Serializable {

	private Integer id;
	private Organizations organizations;
	private Persons persons;

	public PersonOrganizationLink() {
	}

	public PersonOrganizationLink(Organizations organizations, Persons persons) {
		this.organizations = organizations;
		this.persons = persons;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Organizations getOrganizations() {
		return this.organizations;
	}

	public void setOrganizations(Organizations organizations) {
		this.organizations = organizations;
	}

	public Persons getPersons() {
		return this.persons;
	}

	public void setPersons(Persons persons) {
		this.persons = persons;
	}

}
