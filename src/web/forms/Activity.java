package web.forms;

import web.views.AbstractView;

public class Activity{

	private String label;
	private AbstractView abstractView;

	public Activity(){}

	public Activity(Activity activity){
		setLabel(activity.getLabel());
		setAbstractView(activity.getAbstractView());
	}

	public Activity(AbstractView abstractView){
		setLabel(abstractView.getLabel());
		setAbstractView(abstractView);
	}

	public Activity(String label){
		setLabel(label);
	}

	public Activity(String label, AbstractView abstractView){
		setLabel(label);
		setAbstractView(abstractView);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public AbstractView getAbstractView() {
		return abstractView;
	}

	public void setAbstractView(AbstractView abstractView) {
		this.abstractView = abstractView;
	}

}
