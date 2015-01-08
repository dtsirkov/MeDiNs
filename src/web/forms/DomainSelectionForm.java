package web.forms;

import java.util.ArrayList;

import property_pckg.PropertyManager;
import web.classes.Activity;
import web.classes.Domain;
import web.views.AbstractView;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Button.ClickEvent;

public class DomainSelectionForm extends Form{

	private static final long serialVersionUID = 1L;

	private Domain domain;
	private Button domainButton;

	public DomainSelectionForm(AbstractView view, Domain domain) {

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
		final ArrayList<Activity> activities = domain.getActivities();
		//get naviagator
		final Navigator navigator = getNavigator();

		domainButton = new Button(propertyManager.getButtonDtl(getLabel()));

		domainButton.setWidth("300px");

		domainButton.addClickListener(new Button.ClickListener() {	

			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				for(int i = 0; i < activities.size(); i++){
					navigator.addView(activities.get(i).getLabel(), (View)activities.get(i).getAbstractActivityView());
				}
				navigator.navigateTo(domain.getLabel());
			}
		});

		layout.addComponent(domainButton);

		setSizeUndefined();

		return layout;
	}

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

}
