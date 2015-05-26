package web.classes;

import web.MedinsUI;

import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.UI;

public class Header extends HorizontalLayout{

	private static final long serialVersionUID = 1L;
	private Image image = new Image(null, new ThemeResource("images/medins_logo.jpg"));
	private HorizontalLayout mainLayout;
	private HorizontalLayout titleBarLayout;
	private UI ui;

	public Header() {
		buildLayout();
	}

	public Header(final UI ui) {
		this.ui = ui;
		buildLayout();
		addUserLoggedInfo(ui);
	}

	public HorizontalLayout buildLayout(){

		titleBarLayout = new HorizontalLayout();
		titleBarLayout.setWidth("100%");
		titleBarLayout.setHeight("60px");
		titleBarLayout.setSpacing(true);
		titleBarLayout.setStyleName("header");

		image.setHeight("60px");
		titleBarLayout.addComponent(image);

		mainLayout = new HorizontalLayout();
		mainLayout.setWidth("100%");
		mainLayout.setHeight("65px");
		mainLayout.addComponent(titleBarLayout);

		return mainLayout;
	}

	public void addUserLoggedInfo(final UI ui){

		PropertyManager propertyManager = MedinsUI.getPropertyManager();

		//logged user info
		HorizontalLayout userLoggedInfo = new HorizontalLayout();
		userLoggedInfo.setSizeUndefined();

		HorizontalLayout bttnLayout = new HorizontalLayout();
		bttnLayout.setSizeUndefined();

		HorizontalLayout labelLayout = new HorizontalLayout();
		labelLayout.setSizeUndefined();

		HorizontalLayout userLayout = new HorizontalLayout();
		userLayout.setSizeUndefined();

		userLoggedInfo.addComponent(labelLayout);
		userLoggedInfo.setComponentAlignment(labelLayout, Alignment.MIDDLE_RIGHT);

		userLoggedInfo.addComponent(userLayout);
		userLoggedInfo.setComponentAlignment(userLayout, Alignment.MIDDLE_RIGHT);

		userLoggedInfo.addComponent(bttnLayout);
		userLoggedInfo.setComponentAlignment(bttnLayout, Alignment.MIDDLE_RIGHT);

		Label label = new Label();
		label.addStyleName("user_label");
		label.setSizeUndefined();

		Label userLabel = new Label();
		userLabel.addStyleName("user");
		userLabel.setSizeUndefined();

		Button logoutBttn = new Button(propertyManager.getButtonDtl("logout"), new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {

				// "Logout" the user
				ui.getSession().setAttribute("user", null);
				ui.getSession().setAttribute("userName", null);

				// Refresh this view, should redirect to login view
				ui.getNavigator().navigateTo("loginView");
			}
		});

		// Get the user name from the session
		String username = String.valueOf(ui.getSession().getAttribute("userName"));
		if (!username.equals("null")){

			label.setValue(propertyManager.getLabelDtl("userLoggedIn"));
			labelLayout.addComponent(label);
			labelLayout.setMargin(new MarginInfo(true, false, true, false));

			userLabel.setValue(username);
			userLayout.addComponent(userLabel);		
			userLayout.setMargin(new MarginInfo(true, false, true, false));

			bttnLayout.addComponent(logoutBttn);
			bttnLayout.setMargin(new MarginInfo(true, true, true, true));

		}
		else
			userLoggedInfo.setVisible(false);

		titleBarLayout.addComponent(userLoggedInfo);
		titleBarLayout.setComponentAlignment(userLoggedInfo, Alignment.MIDDLE_RIGHT);
	}

	public UI getUi() {
		return ui;
	}

	public void setUi(UI ui) {
		this.ui = ui;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public HorizontalLayout getMainLayout() {
		return mainLayout;
	}

	public void setMainLayout(HorizontalLayout mainLayout) {
		this.mainLayout = mainLayout;
	}

	public HorizontalLayout getTitleBarLayout() {
		return titleBarLayout;
	}

	public void setTitleBarLayout(HorizontalLayout titleBarLayout) {
		this.titleBarLayout = titleBarLayout;
	}

}
