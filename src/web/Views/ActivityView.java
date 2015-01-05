package web.Views;

import java.util.ArrayList;

import property_pckg.PropertyManager;
import web.Forms.ComponentValidator;
import web.Forms.ContactForm;
import web.Forms.Domain;
import web.Forms.DomainForm;
import web.Forms.Form;
import web.Forms.PersonForm;
import web.Forms.ValidationClass;
import web.Forms.ValidationForm;


import com.vaadin.annotations.Theme;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import dao_classes.DaoIntrfc;

@SuppressWarnings("serial")
@Theme("medins")
public class ActivityView extends AbstractView implements View{
	
	private ArrayList<Domain> domainList;

	public ActivityView(PropertyManager propertyManager, DaoIntrfc dao, Navigator navigator) {

		super(propertyManager, dao, navigator);
		setLabel("domainSelectionView");

	}

	@Override
	public void enter(ViewChangeEvent event) {
		setCompositionRoot(buildLayout());
	}

	public Layout buildLayout(){

		//get propertyManager
		final PropertyManager propertyManager = getPropertyManager();
		//get access to DB
		final DaoIntrfc dao = getDao();	

		//create main layout
		final VerticalLayout root = new VerticalLayout();
		//create component validater
		final ComponentValidator customValidator = new ComponentValidator(getPropertyManager());

		// Create the root layout (VerticalLayout is actually the default).
		root.addStyleName("personcreate");
		root.setSizeFull();

		// Title bar
		HorizontalLayout titleBar = new HorizontalLayout();
		titleBar.setWidth("100%");
		root.addComponent(titleBar);

		Label title = new Label(propertyManager.getLabelDtl("personCreate"));
		title.addStyleName("title");
		titleBar.addComponent(title);
		
		int rowsNumber = 0;
		int domainListSize = domainList.size();
		if (domainListSize % 2 == 0){
			rowsNumber = domainListSize / 2;
		}else{
			rowsNumber = domainListSize / 2 + 1;
		}
		
		GridLayout grid = new GridLayout(2, rowsNumber);
		grid.setSizeUndefined();
		grid.setSpacing(true);
		grid.addStyleName("test");
		
		for(int i = 0; i < domainListSize; i++){
			DomainForm domainForm = new DomainForm(this, domainList.get(i));
			domainForm.buildLayout("");
			grid.addComponent(domainForm);
		}

		//grid.setComponentAlignment(loginFormCorporate, Alignment.MIDDLE_LEFT);
		//grid.setComponentAlignment(loginFormIndividial, Alignment.MIDDLE_RIGHT);

		root.addComponent(grid);
		root.setComponentAlignment(grid, Alignment.MIDDLE_CENTER);
		root.setExpandRatio(grid, 1);

		return root;

	}

	public ArrayList<Domain> getDomainList() {
		return domainList;
	}

	public void setDomainList(ArrayList<Domain> domainList) {
		this.domainList = domainList;
	}

}