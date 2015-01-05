package web.Forms;

import java.util.ArrayList;

public class Domain {
	
	private String name;
	private ArrayList<Object> activities;
	
	public Domain(){}
	
	public Domain(Domain domain){
		setName(domain.getName());
		setActivities(domain.getActivities());
	}
	
	public Domain(String name){
		setName(name);
	}
	
	public Domain(String name, ArrayList<Object> activities){
		setName(name);
		setActivities(activities);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Object> getActivities() {
		return activities;
	}

	public void setActivities(ArrayList<Object> activities) {
		this.activities = activities;
	}
	
	public void addActivity(Object object){
		activities.add(object);
	}

}
