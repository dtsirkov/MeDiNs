package beans;

import java.util.Map;

import pojo.classes.Enumerations;
import pojo.classes.Services;



import com.sun.istack.internal.NotNull;

import annotations.MyColumn;
import annotations.MyTable;

@MyTable(caption = "", popupCaption = "service", isColumnCollapsingAllowed = true, height = 300)
public class ServiceBean { 

	private static Map<Enumerations, String> typeEnum;

	@NotNull
	@MyColumn(name = "name", isVisible = true, width = 150)
	private String name;

	@NotNull
	@MyColumn(name = "type", isVisible = true, width = 150)
	private String type;

	@MyColumn(name = "description", isVisible = true)
	private String description;

	@MyColumn(name = "performedBy", isVisible = true, isCollapsed = true)
	private String performedBy;

	@MyColumn(name = "location", isVisible = true, isCollapsed = true)
	private String location;

	@MyColumn(name = "specialist", isVisible = true, isCollapsed = true)
	private String specialist;

	@MyColumn(name = "priceDue", isVisible = true, isVisibleByUser = false)
	private String priceDue;

	@MyColumn(name = "priceExcess", isVisible = true, isVisibleByUser = false)
	private String priceExcess;

	public ServiceBean() {
	}

	public ServiceBean(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public ServiceBean(Services service) {

		String type = (service.getType() == null ? "" : service.getType());
		Map<String, String> codeDisplayMap = ComboxBean.getCodeDisplayMap(typeEnum);

		this.setName(service.getName() == null ? "" : service.getName());
		this.setType(codeDisplayMap.get(type));
		this.setDescription(service.getDescription() == null ? "" : service.getDescription());
		this.setPerformedBy(service.getPerformedBy() == null ? "" : service.getPerformedBy());
		this.setSpecialist(service.getSpecialist() == null ? "" : service.getSpecialist());
		this.setLocation(service.getServiceLocation() == null ? "" : service.getServiceLocation());
		this.setPriceDue(service.getPriceDue()  == null ? "0.0" :  service.getPriceDue().toString());
		this.setPriceExcess(service.getPriceExcess()  == null ? "0.0" :  service.getPriceExcess().toString());	
	}

	public static Map<Enumerations, String> getTypeEnum() {
		return typeEnum;
	}

	public static void setTypeEnum(Map<Enumerations, String> typeEnum) {
		ServiceBean.typeEnum = typeEnum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPerformedBy() {
		return performedBy;
	}

	public void setPerformedBy(String performedBy) {
		this.performedBy = performedBy;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSpecialist() {
		return specialist;
	}

	public void setSpecialist(String specialist) {
		this.specialist = specialist;
	}

	public String getPriceDue() {
		return priceDue;
	}

	public void setPriceDue(String priceDue) {
		this.priceDue = priceDue;
	}

	public String getPriceExcess() {
		return priceExcess;
	}

	public void setPriceExcess(String priceExcess) {
		this.priceExcess = priceExcess;
	}

	public Services createPersistenceObject() {

		Services service = new Services();

		Map<String, String> displayCodeMap = ComboxBean.getDisplayCodeMap(typeEnum);

		service.setName(name);
		service.setType(displayCodeMap.get(type));
		service.setDescription(description);
		service.setPerformedBy(performedBy);
		service.setSpecialist(specialist);
		service.setServiceLocation(location);
		service.setPriceDue(Float.valueOf(priceDue));
		service.setPriceExcess(Float.valueOf(priceExcess));

		return service;
	}

}

