package web.forms;

import java.util.ArrayList;

import property_pckg.PropertyManager;
import web.classes.Activity;
import web.classes.Domain;
import web.views.AbstractView;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.ListSelect;

public class ActivitySelectionForm extends Form{

	private static final long serialVersionUID = 1L;

	private Domain domain;
	private ListSelect listSelect;

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
		listSelect = new ListSelect(propertyManager.getButtonDtl(domain.getLabel()));
		// Show 10 items and a scrollbar if there are more
		listSelect.setRows(10);
		listSelect.setNullSelectionAllowed(false);

		String label;
		for(int i = 0; i < activities.size(); i++){
			label = activities.get(i).getLabel();
			listSelect.addItem(label);
			listSelect.setItemCaption(label, propertyManager.getLabelDtl(label));
		}

		listSelect.addValueChangeListener(new Property.ValueChangeListener() {	

			private static final long serialVersionUID = 1L;

			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty() != null && event.getProperty().getValue() != null){
					String activity = event.getProperty().getValue().toString();
					navigator.navigateTo(activity);
				}
			}
		});

		layout.addComponent(listSelect);

		setSizeUndefined();

		return layout;
	}

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	public ListSelect getListSelect() {
		return listSelect;
	}

	public void setListSelect(ListSelect listSelect) {
		this.listSelect = listSelect;
	}

}

