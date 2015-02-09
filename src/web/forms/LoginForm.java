package web.forms;

import web.classes.ComponentValidator;
import web.classes.PropertyManager;
import web.views.AbstractView;


import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import dao.classes.DaoIntrfc;

public class LoginForm extends Form {

	private static final long serialVersionUID = 1L;
	private TextField usernameTF;
	private TextField passwordTF;
	private Button loginButton;

	public LoginForm(AbstractView view, String label) {

		super(view, label, new VerticalLayout());
		setCompositionRoot(getLayout());

	}

	public Layout buildLayout(String mode) {

		//get main layout
		VerticalLayout layout = (VerticalLayout)getLayout();
		//get component validater
		ComponentValidator componentValidator = getComponentValidator();
		//get propertyManager
		PropertyManager propertyManager = getPropertyManager();
		//get access to DB
		DaoIntrfc dao = getDao();
		//get naviagator
		final Navigator navigator = getNavigator();

		//remove all current components
		layout.removeAllComponents();

		//define measurements of the components 
		String width = "180px", height = "-1px";

		Panel panel = new Panel(getLabelDtl());
		panel.setImmediate(true);
		panel.setWidth(width);
		panel.setHeight(height);
		panel.setSizeUndefined();

		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setImmediate(true);
		verticalLayout.setWidth(width);
		verticalLayout.setHeight("300px");

		// usernameLB
		Label usernameLB = new Label(propertyManager.getLabelDtl("username"));
		usernameLB.setImmediate(true);
		usernameLB.setWidth(width);
		usernameLB.setHeight(height);

		// passwordLB
		Label passwordLB = new Label(propertyManager.getLabelDtl("password"));
		passwordLB.setImmediate(true);
		passwordLB.setWidth(width);
		passwordLB.setHeight(height);

		// usernameTF
		usernameTF = new TextField();
		usernameTF.setImmediate(true);
		usernameTF.setWidth(width);
		usernameTF.setHeight(height);

		// passwordTF
		passwordTF = new TextField();
		passwordTF.setImmediate(true);
		passwordTF.setWidth(width);
		passwordTF.setHeight(height);

		// loginButton 
		loginButton = new Button(propertyManager.getButtonDtl("login"));
		loginButton.setImmediate(true);
		loginButton.setWidth(width);
		loginButton.setHeight(height);

		loginButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			@Override
			public void buttonClick(ClickEvent event) {
				navigator.navigateTo("domainSelectionView");
			}
		});

		verticalLayout.addComponent(usernameLB);
		verticalLayout.addComponent(usernameTF);
		verticalLayout.addComponent(passwordLB);
		verticalLayout.addComponent(passwordTF);
		verticalLayout.addComponent(loginButton);

		panel.setContent(verticalLayout);

		layout.addComponent(panel);

		return layout;
	}
}
