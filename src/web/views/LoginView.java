package web.views;

import property_pckg.PropertyManager;
import web.forms.LoginForm;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

import dao_classes.DaoIntrfc;

public class LoginView extends AbstractView{

	private static final long serialVersionUID = 1L;


	public LoginView(PropertyManager propertyManager, DaoIntrfc dao, Navigator navigator) {

		super(propertyManager, dao, navigator);
		setLabel("login");

	}	

	@Override
	public void enter(ViewChangeEvent event) {
		setCompositionRoot(buildLayout());
	}

	public Layout buildLayout() {
	
		//get propertyManager
		PropertyManager propertyManager = getPropertyManager();
		//get access to DB
		DaoIntrfc dao = getDao();	
		
		//create main layout
		VerticalLayout layout = new VerticalLayout();	

		layout.addStyleName("personcreate");
		layout.setSizeFull();

		// Title bar
		HorizontalLayout titleBar = new HorizontalLayout();
		titleBar.setWidth("100%");
		layout.addComponent(titleBar);

		Label title = new Label("MeDiNs");
		title.addStyleName("title");
		titleBar.addComponent(title);

		LoginForm loginFormIndividial = new LoginForm(this, "individualClients");
		loginFormIndividial.buildLayout("individual");
		LoginForm loginFormCorporate = new LoginForm(this, "corporateClients");
		loginFormCorporate.buildLayout("corporate");

		GridLayout grid = new GridLayout(2, 1);
		grid.setWidth("600px");
		grid.setSpacing(true);
		grid.addStyleName("test");

		loginFormCorporate.setSizeUndefined();
		grid.addComponent(loginFormCorporate);
		grid.setComponentAlignment(loginFormCorporate, Alignment.MIDDLE_LEFT);

		loginFormIndividial.setSizeUndefined();
		grid.addComponent(loginFormIndividial);
		grid.setComponentAlignment(loginFormIndividial, Alignment.MIDDLE_RIGHT);

		layout.addComponent(grid);
		layout.setComponentAlignment(grid, Alignment.MIDDLE_CENTER);
		layout.setExpandRatio(grid, 1);

		return layout;
	}

}
