package web.forms;

import java.util.ArrayList;

import web.classes.Activity;
import web.classes.Domain;
import web.classes.PropertyManager;
import web.views.AbstractView;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.ListSelect;

import dao.classes.DaoIntrfc;

public class ActivitySelectionForm extends Form{

	private static final long serialVersionUID = 1L;

	private Domain domain;
	private ListSelect activityList;

	public ActivitySelectionForm(AbstractView view, Domain domain) {

		super(view, domain.getLabel(), new HorizontalLayout());
		setDomain(domain);
		setCompositionRoot(getLayout());

	}

	public Layout buildLayout(String mode) {

		//get main layout
		HorizontalLayout layout = (HorizontalLayout)getLayout();
		//get propertyManager
		PropertyManager propertyManager = getPropertyManager();
		//get activities by domain
		final ArrayList<Activity> activities = getDomain().getActivities();
		//get naviagator
		final Navigator navigator = getNavigator();

		// Create the selection component
		activityList = new ListSelect(propertyManager.getButtonDtl(domain.getLabel()));
		// Show 10 items and a scrollbar if there are more
		activityList.setRows(10);
		activityList.setNullSelectionAllowed(false);

		String label;
		for(int i = 0; i < activities.size(); i++){
			label = activities.get(i).getLabel();
			activityList.addItem(label);
			activityList.setItemCaption(label, propertyManager.getLabelDtl(label));
		}

		activityList.addValueChangeListener(new Property.ValueChangeListener() {	

			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty() != null && event.getProperty().getValue() != null){
					String activity = event.getProperty().getValue().toString();
					navigator.navigateTo(activity);
				}
			}
		});

		layout.addComponent(activityList);

		setSizeUndefined();

		return layout;
	}

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	public ListSelect getActvityList() {
		return activityList;
	}

	public void setActvityList(ListSelect actvityList) {
		this.activityList = actvityList;
	}

}

