package web.beans;

import com.sun.istack.internal.NotNull;

import database.pojo.Diagnosis;

import web.components.table.generated.annotations.MyColumn;
import web.components.table.generated.annotations.MyTable;

@MyTable(caption = "", popupCaption = "Diagnosis", isColumnCollapsingAllowed = true, height = 300)
public class DiagnosisBean {

	private Integer id;

	@NotNull
	@MyColumn(name = "mkb", isVisible = true, width = 100)
	private String mkb;

	@NotNull
	@MyColumn(name = "name", isVisible = true)
	private String name;

	@MyColumn(name = "description", isVisible = true, isCollapsed = true)
	private String description;

	public DiagnosisBean(int id, String mkb, String name) {
		this.id = id;
		this.mkb = mkb;
		this.name = name;
	}

	public DiagnosisBean(){}

	public DiagnosisBean(Diagnosis diagnosis){

		this.setId(diagnosis.getId());
		this.setMkb(diagnosis.getMkb() == null ? "" : diagnosis.getMkb());
		this.setName(diagnosis.getName() == null ? "" : diagnosis.getName());
		this.setDescription(diagnosis.getDescription() == null ? "" : diagnosis.getDescription());

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMkb() {
		return mkb;
	}

	public void setMkb(String mkb) {
		this.mkb = mkb;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Diagnosis createPersistenceObject() {

		Diagnosis diagnosis = new Diagnosis();

		if(id != null)
			diagnosis.setId(id);
		diagnosis.setMkb(mkb);
		diagnosis.setName(name);
		diagnosis.setDescription(description);

		return diagnosis;
	}

}
