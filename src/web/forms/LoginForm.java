package web.forms;

import java.util.List;

import web.classes.ComponentValidator;
import web.classes.PropertyManager;
import web.views.AbstractView;


import com.vaadin.data.Validator;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

import database.dao.DaoIntrfc;
import database.pojo.Users;

public class LoginForm extends Form implements View {

	private static final long serialVersionUID = 1L;
	private TextField usernameTF;
	private PasswordField passwordPF;
	private Button loginButton;

	public LoginForm(AbstractView view, String label) {

		super(view, label, new VerticalLayout());
		setCompositionRoot(getLayout());

	}

	public Layout buildLayout(String mode) {

		//get main web.components.table.generated.layout
		VerticalLayout layout = (VerticalLayout)getLayout();
		//get component validater
		ComponentValidator componentValidator = getComponentValidator();
		//get propertyManager
		PropertyManager propertyManager = getPropertyManager();
		//get access to DB
		final DaoIntrfc dao = getDao();
		//get naviagator
		final Navigator navigator = getNavigator();

		//remove all current web.components.table.generated.components
		layout.removeAllComponents();

		//define measurements of the web.components.table.generated.components 
		String width = "200px", height = "-1px";

		Panel panel = new Panel(getLabelDtl());
		panel.setImmediate(true);
		panel.setWidth("400px");
		panel.setHeight("-1px");
		//panel.setSizeUndefined();

		FormLayout formLayout = new FormLayout();
		formLayout.setImmediate(true);
		formLayout.setWidth(width);
		formLayout.setHeight("-1px");

		// usernameTF
		usernameTF = new TextField(propertyManager.getLabelDtl("username"));
		usernameTF.setImmediate(true);
		usernameTF.setWidth(width);
		usernameTF.setHeight(height);
		usernameTF.setRequired(true);
		usernameTF.focus();
		//usernameTF.addValidator(componentValidator.getUserExistValidator(dao, "User does not exists!"));

		// passwordTF
		passwordPF = new PasswordField(propertyManager.getLabelDtl("password"));
		passwordPF.setImmediate(true);
		passwordPF.setWidth(width);
		passwordPF.setHeight(height);
		passwordPF.setRequired(true);

		// loginButton 
		loginButton = new Button(propertyManager.getButtonDtl("login"));
		loginButton.setImmediate(true);
		loginButton.setWidth(width);
		loginButton.setHeight(height);

		loginButton.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				Users out = null;
				Users user = new Users();
				user.setUsername(usernameTF.getValue());
				user.setPassword(passwordPF.getValue());
				List<Object> searchResult=dao.findByExample(user);
				if (searchResult.size() > 0){
					out=(Users) searchResult.get(0);
				}
				if (usernameTF.isValid() == true && out != null)
				{
					// Store the current user in the service session
					getSession().setAttribute("user", usernameTF.getValue());
					navigator.navigateTo("domainSelectionView");
				}
				else
				{
					Notification notif = new Notification(
							"",
							"Invalid username and/or password!",
							Notification.TYPE_HUMANIZED_MESSAGE,true);
					notif.setDelayMsec(2000);
					notif.setPosition(Position.MIDDLE_CENTER);
					notif.show(Page.getCurrent());
					usernameTF.focus();
				}
			}
		});

		formLayout.addComponent(usernameTF);
		formLayout.addComponent(passwordPF);
		formLayout.addComponent(loginButton);

		panel.setContent(formLayout);

		layout.addComponent(panel);

		return layout;
	}

	@Override
	public void enter(ViewChangeEvent event) {
		this.usernameTF.focus();		
	}

	public Users checkUserPass(DaoIntrfc dao ,String username,String password){
		Users out = null;
		Users user = new Users();
		user.setUsername(username);
		user.setPassword(password);
		List<Object> searchResult=dao.findByExample(user);
		if (searchResult.size() > 0){
			out=(Users) searchResult.get(0);
		}
		return out;
	}
}
