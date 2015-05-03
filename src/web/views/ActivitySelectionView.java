package web.views;

import web.MedinsUI;
import web.classes.ComponentValidator;
import web.classes.Domain;
import web.classes.Header;
import web.classes.PropertyManager;
import web.forms.ActivitySelectionForm;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import database.dao.DaoIntrfc;

public class ActivitySelectionView extends AbstractView{

	private static final long serialVersionUID = 1L;
	private Domain domain;

	public ActivitySelectionView(UI ui) {

		super(ui);
		setLabel("activitySelectionView");

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

		//create main web.components.table.generated.layout
		final VerticalLayout root = new VerticalLayout();
		//create component validater
		final ComponentValidator customValidator = new ComponentValidator(getPropertyManager());

		// Create the root web.components.table.generated.layout (VerticalLayout is actually the default).
		root.addStyleName("personcreate");
		root.setSizeFull();

		Header header=new Header(getUI());
		root.addComponent(header);
		root.setComponentAlignment(header,Alignment.TOP_RIGHT);

		Label title = new Label(domain.getLabel());
		title.addStyleName("title");
		root.addComponent(title);


		//grid.setComponentAlignment(loginFormCorporate, Alignment.MIDDLE_LEFT);
		//grid.setComponentAlignment(loginFormIndividial, Alignment.MIDDLE_RIGHT);

		ActivitySelectionForm activitySelectionForm = new ActivitySelectionForm(this, domain);
		activitySelectionForm.buildLayout("");

		root.addComponent(activitySelectionForm);
		root.setComponentAlignment(activitySelectionForm, Alignment.MIDDLE_CENTER);
		root.setExpandRatio(activitySelectionForm, 1);

		return root;

	}

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	@Override
	public Layout viewLayout(String mode) {
		// TODO Auto-generated method stub
		return null;
	}


}