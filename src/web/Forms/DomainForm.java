package web.Forms;

import java.util.ArrayList;

import property_pckg.PropertyManager;
import web.Views.AbstractView;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Button.ClickEvent;

public class DomainForm extends Form{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Domain domain;
	private Button domainButton;

	public DomainForm(AbstractView view, Domain domain) {

		super(view, domain.getName(), new HorizontalLayout());
		setDomain(domain);
		setCompositionRoot(getLayout());

	}

	public Layout buildLayout(String mode) {

		//get main layout
		HorizontalLayout layout = (HorizontalLayout)getLayout();
		//get propertyManager
		PropertyManager propertyManager = getPropertyManager();
		//get activities by domain
		final ArrayList<Object> activities = getDomain().getActivities();
		//get naviagator
		final Navigator navigator = getNavigator();

		domainButton = new Button(propertyManager.getButtonDtl(getLabel()));
		
		domainButton.setWidth("300px");
		
		domainButton.addClickListener(new Button.ClickListener() {	

			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				for(int i = 0; i < activities.size(); i++){
					navigator.addView("activitySelectionView", (View)activities.get(i));
				}
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
