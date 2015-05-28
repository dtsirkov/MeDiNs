package web.classes;

import org.hibernate.TransactionException;

import web.MedinsUI;

import com.vaadin.event.MouseEvents;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class Header extends HorizontalLayout{

	private static final long serialVersionUID = 1L;
	private Image image = new Image(null, new ThemeResource("images/medins_logo.jpg"));
	private VerticalLayout mainLayout;
	private HorizontalLayout homeBarLayout;
	private HorizontalLayout labelBarLayout;
	private Label title;
	private UI ui;

	public Header() {
		buildLayout(null);
	}

	public Header(String titleStr) {
		buildLayout(titleStr);
	}

	public Header(final UI ui) {
		this.ui = ui;
		buildLayout(null);
		addUserLoggedInfo(ui);
	}

	public Header(final UI ui, String titleStr) {
		this.ui = ui;
		buildLayout(titleStr);
		addUserLoggedInfo(ui);
	}

	public Header(final UI ui, boolean addHomeBttn) {
		this(ui, null);
		if(addHomeBttn)
			addHomeBttn(ui);
	}

	public Header(final UI ui, boolean addHomeBttn, String titleStr) {
		this(ui, titleStr);
		if(addHomeBttn)
			addHomeBttn(ui);
	}

	public VerticalLayout buildLayout(String titleStr){

		homeBarLayout = new HorizontalLayout();
		homeBarLayout.setWidth("100%");
		homeBarLayout.setHeight("60px");
		homeBarLayout.setSpacing(true);
		homeBarLayout.setStyleName("header");

		image.setHeight("60px");
		homeBarLayout.addComponent(image);

		HorizontalLayout homeBarOuterLayout = new HorizontalLayout();
		homeBarOuterLayout.setWidth("100%");
		homeBarOuterLayout.setHeight("65px");
		homeBarOuterLayout.addComponent(homeBarLayout);

		mainLayout = new VerticalLayout();
		mainLayout.addStyleName("personcreate");
		mainLayout.setWidth("100%");
		mainLayout.addComponent(homeBarOuterLayout);

		if(titleStr != null){

			labelBarLayout = new HorizontalLayout();		
			labelBarLayout.setWidth("100%");
			labelBarLayout.setHeight("50px"); 
			labelBarLayout.addStyleName("headerTitleLayout");

			title = new Label(MedinsUI.getPropertyManager().getLabelDtl(titleStr));
			title.addStyleName("headerTitle");
			title.setSizeUndefined();
			labelBarLayout.addComponent(title);
			labelBarLayout.setComponentAlignment(title, Alignment.MIDDLE_CENTER);

			mainLayout.addComponent(labelBarLayout);

		}

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

		homeBarLayout.addComponent(userLoggedInfo);
		homeBarLayout.setComponentAlignment(userLoggedInfo, Alignment.MIDDLE_RIGHT);
	}

	public void addHomeBttn(final UI ui){

		final Navigator navigator = ui.getNavigator();

		image.addClickListener(new MouseEvents.ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void click(com.vaadin.event.MouseEvents.ClickEvent event) {
				try{
					navigator.navigateTo("domainSelectionView");
				}catch(TransactionException e){
					navigator.navigateTo("domainSelectionView");
				}
			}
		});

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

	public VerticalLayout getMainLayout() {
		return mainLayout;
	}

	public void setMainLayout(VerticalLayout mainLayout) {
		this.mainLayout = mainLayout;
	}

	public HorizontalLayout getHomeBarLayout() {
		return homeBarLayout;
	}

	public void setHomeBarLayout(HorizontalLayout homeBarLayout) {
		this.homeBarLayout = homeBarLayout;
	}

	public HorizontalLayout getLabelBarLayout() {
		return labelBarLayout;
	}

	public void setLabelBarLayout(HorizontalLayout labelBarLayout) {
		this.labelBarLayout = labelBarLayout;
	}

	public Label getTitle() {
		return title;
	}

	public void setTitle(Label title) {
		this.title = title;
	}

}
