package web.views;

import web.classes.Header;
import web.classes.PropertyManager;
import web.forms.LoginForm;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import database.dao.DaoIntrfc;

public class LoginView extends AbstractView implements View{

	private static final long serialVersionUID = 1L;

	public LoginView(UI ui) {
		super(ui);
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

		//create main web.components.table.generated.layout
		VerticalLayout layout =new VerticalLayout();	
		layout.removeAllComponents();

		layout.addStyleName("personcreate");
		layout.setSizeFull();

		/*	// Title bar
		HorizontalLayout titleBar = new HorizontalLayout();
		titleBar.setWidth("100%");
		web.components.table.generated.layout.addComponent(titleBar);

		Label title = new Label("MeDiNs");
		title.addStyleName("title");
		titleBar.addComponent(title);*/

		Header header=new Header(getUI());
		layout.addComponent(header);
		layout.setComponentAlignment(header,Alignment.TOP_RIGHT);

		LoginForm loginFormIndividial = new LoginForm(this, "individualClients");
		loginFormIndividial.buildLayout("individual");
		loginFormIndividial.setSizeUndefined();

		layout.addComponent(loginFormIndividial);
		layout.setComponentAlignment(loginFormIndividial, Alignment.MIDDLE_CENTER);
		layout.setExpandRatio(loginFormIndividial, 1);

		return layout;
	}

	@Override
	public Layout viewLayout(String mode) {
		// TODO Auto-generated method stub
		return null;
	}

}
