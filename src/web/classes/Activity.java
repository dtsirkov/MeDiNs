package web.classes;

import web.activities.AbstractActivityView;

public class Activity{

	private String label;
	private Domain domain;
	private AbstractActivityView abstractActivityView;

	public Activity(){}

	public Activity(Activity activity){
		setLabel(activity.getLabel());
		setDomain(activity.getDomain());
		setAbstractActivityView(activity.getAbstractActivityView());
	}

	public Activity(AbstractActivityView abstractActivityView){
		setLabel(abstractActivityView.getLabel());
		setAbstractActivityView(abstractActivityView);
	}

	public Activity(String label){
		setLabel(label);
	}

	public Activity(String label, AbstractActivityView abstractActivityView){
		setLabel(label);
		setAbstractActivityView(abstractActivityView);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	public AbstractActivityView getAbstractActivityView() {
		return abstractActivityView;
	}

	public void setAbstractActivityView(AbstractActivityView abstractActivityView) {
		this.abstractActivityView = abstractActivityView;
	}

}
