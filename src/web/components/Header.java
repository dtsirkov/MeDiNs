package web.components;


import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class Header extends Panel{

	private static final long serialVersionUID = 1L;
	private UI ui;

	public Header(final UI ui) {
		this.ui = ui;

		this.setWidth("100%");

		VerticalLayout root = new VerticalLayout();

		//header layout
		HorizontalLayout header = new HorizontalLayout();
		header.setWidth("100%");

		Embedded medinsLogo = new Embedded("", new ThemeResource("images/medins_logo.jpg"));		

		header.addComponent(medinsLogo);
		header.setComponentAlignment(medinsLogo,Alignment.MIDDLE_LEFT);

		//logged user info
		VerticalLayout userLoggedInfo = new VerticalLayout();
		userLoggedInfo.setStyleName("userlogged");
		userLoggedInfo.setWidth("200px");

		Label text = new Label();
		Button logout = new Button("Logout", new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				// "Logout" the user
				ui.getSession().setAttribute("user", null);

				// Refresh this view, should redirect to login view
				ui.getNavigator().navigateTo("loginView");
			}
		});
		// Get the user name from the session
		String username = String.valueOf(ui.getSession().getAttribute("user"));
		if (!username.equals("null")){
			// And show the username
			text.setValue("You are logged in as: " + username+" ! ");
			userLoggedInfo.addComponent(text);
			userLoggedInfo.addComponent(logout);
		}
		else {
			userLoggedInfo.setVisible(false);
		}
		header.addComponent(userLoggedInfo);
		header.setComponentAlignment(userLoggedInfo,Alignment.BOTTOM_RIGHT);

/*		//menu layout
		VerticalLayout menuLayout = new VerticalLayout();

		MenuBar mainMenu = new MenuBar();
		mainMenu.addStyleName("mainmenu");
		MenuItem home = mainMenu.addItem("Home", null,null);
		MenuItem domain = mainMenu.addItem("Domain", null,null);
		
		MenuItem cases=domain.addItem("Case", null, null);
		cases.addItem("New case", null, null);
		cases.addItem("New case 2", null, null);
		domain.addSeparator();
		
		MenuItem personOrganization=domain.addItem("Person&Organization", null, null);
		personOrganization.addItem("New Person", null, null);
		personOrganization.addItem("New Organization", null, null);
		personOrganization.addItem("New User", null, null);


		menuLayout.addComponent((Component) mainMenu);*/

		//adding header and menu to root layout
		root.addComponent(header);
		//root.addComponent(menuLayout);

		this.setContent(root);
	}

	public UI getUi() {
		return ui;
	}

	public void setUi(UI ui) {
		this.ui = ui;
	}

}
