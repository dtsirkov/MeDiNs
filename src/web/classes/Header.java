package web.classes;

import com.vaadin.server.ThemeResource;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class Header extends Panel{

	private static final long serialVersionUID = 1L;
	static Image image = new Image(null, new ThemeResource("images/medins_logo.jpg"));
	private UI ui;

	public Header(final UI ui) {
		this.ui = ui;

		this.setWidth("100%");

		VerticalLayout root = new VerticalLayout();

		//header web.components.table.generated.layout
		HorizontalLayout header = new HorizontalLayout();
		header.setWidth("100%");
		header.setHeight("-1px");		

		Image image = new Image(null, new ThemeResource("images/medins_logo.jpg"));

		header.addComponent(image);
		header.setComponentAlignment(image, Alignment.TOP_LEFT);

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
				ui.getSession().setAttribute("userName", null);

				//Transaction transaction = dao.getTransaction();

				// Refresh this view, should redirect to login view
				ui.getNavigator().navigateTo("loginView");
			}
		});
		// Get the user name from the session
		String username = String.valueOf(ui.getSession().getAttribute("userName"));
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
		header.setComponentAlignment(userLoggedInfo, Alignment.MIDDLE_RIGHT);

		/*		//menu web.components.table.generated.layout
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

		//adding header and menu to root web.components.table.generated.layout
		root.addComponent(header);
		//root.addComponent(menuLayout);

		this.setContent(root);
	}

	public static HorizontalLayout create(){

		HorizontalLayout titleBar = new HorizontalLayout();
		titleBar.setWidth("100%");
		titleBar.setHeight("60px");
		titleBar.setSpacing(true);
		titleBar.setStyleName("header");

		image.setHeight("60px");
		titleBar.addComponent(image);

		HorizontalLayout titleBarLayout = new HorizontalLayout();
		titleBarLayout.setWidth("100%");
		titleBarLayout.setHeight("65px");
		titleBarLayout.addComponent(titleBar);

		return titleBarLayout;
	}

	public UI getUi() {
		return ui;
	}

	public void setUi(UI ui) {
		this.ui = ui;
	}

	public static Image getImage() {
		return image;
	}

	public static void setImage(Image image) {
		Header.image = image;
	}

}
