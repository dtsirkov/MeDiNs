package web.classes;

import java.util.ArrayList;


public class Domain {
	
	private String label;
	private ArrayList<Activity> activities;
	
	public Domain(){}
	
	public Domain(Domain domain){
		setLabel(domain.getLabel());
		setActivities(domain.getActivities());
	}
	
	public Domain(String label){
		setLabel(label);
		activities = new ArrayList<Activity>();
	}
	
	public Domain(String name, ArrayList<Activity> activities){
		setLabel(name);
		setActivities(activities);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public ArrayList<Activity> getActivities() {
		return activities;
	}

	public void setActivities(ArrayList<Activity> activities) {
		this.activities = activities;
	}
	
	public void addActivity(Activity activity){
		activities.add(activity);
		activity.setDomain(this);
		activity.getAbstractActivityView().setDomain(this);
	}

}
