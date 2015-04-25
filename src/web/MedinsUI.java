package web;


import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;

import web.activities.CreateCase;
import web.activities.CreateCase2;
import web.activities.CreateOrganization;
import web.activities.CreatePerson;
import web.activities.CreateUser;
import web.activities.CreateUser2;
import web.activities.UpdateOrganization;
import web.activities.UpdatePerson;
import web.activities.UpdateUser;
import web.classes.Activity;
import web.classes.Domain;
import web.classes.PropertyManager;
import web.components.table.generated.windows.EditPopupWindow;
import web.components.table.generated.windows.ListPopupWindow;
import web.views.DomainSelectionView;
import web.views.LoginView;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import database.dao.DaoImpl;
import database.dao.DaoIntrfc;

@SuppressWarnings("serial")
@Theme("medins")
@PreserveOnRefresh
public class MedinsUI extends UI {

	private static PropertyManager propertyManager;

	private DaoIntrfc dao;
	private Navigator navigator;
	private EditPopupWindow activeEditPopupWindow;
	private ListPopupWindow activeListPopupWindow;

	public MedinsUI(){}

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = MedinsUI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {

		setPropertyManager(new PropertyManager(request));
		setDao(new DaoImpl(propertyManager.getLanguage()));
		setNavigator(new Navigator(this, this));

		// Create and register the views
		navigator.addView("loginView", new LoginView(this));

		DomainSelectionView domainSelectionView =  new DomainSelectionView(this);

		//person organization domain
		Domain personOrganizationDomain = new Domain("personOrganizationDomain");
		
		Activity createPersonActivity = new Activity(new CreatePerson(this));
		personOrganizationDomain.addActivity(createPersonActivity);
		
		Activity updatePersonActivity = new Activity(new UpdatePerson(this));
		personOrganizationDomain.addActivity(updatePersonActivity);
		
		Activity createOrganizationActivity = new Activity(new CreateOrganization(this));
		personOrganizationDomain.addActivity(createOrganizationActivity);
		
		Activity updateOrganizationActivity = new Activity(new UpdateOrganization(this));
		personOrganizationDomain.addActivity(updateOrganizationActivity);
		
		Activity createUserActivity = new Activity(new CreateUser(this));
		personOrganizationDomain.addActivity(createUserActivity);
		
		Activity createUserActivity2 = new Activity(new CreateUser2(this));
		personOrganizationDomain.addActivity(createUserActivity2);
		
		Activity updateUserActivity = new Activity(new UpdateUser(this));
		personOrganizationDomain.addActivity(updateUserActivity);

		//case domain
		Domain caseDomain = new Domain("caseDomain");
		Activity createCaseActivity = new Activity(new CreateCase(this));
		Activity createCaseActivity2 = new Activity(new CreateCase2(this));
		caseDomain.addActivity(createCaseActivity);
		caseDomain.addActivity(createCaseActivity2);

		ArrayList<Domain> domainList = new ArrayList<Domain>();

		domainList.add(caseDomain);
		domainList.add(personOrganizationDomain);
		domainList.add(new Domain("medicalDomain"));
		domainList.add(new Domain("treatmentDomain"));
		domainSelectionView.setDomainList(domainList);

		navigator.addView("domainSelectionView", domainSelectionView);

		navigator.addViewChangeListener(new ViewChangeListener(){

			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {

				// Check if a user has logged in
				boolean isLoggedIn = getSession().getAttribute("user") != null;
				boolean isLoginView = event.getNewView() instanceof LoginView;

				if (!isLoggedIn && !isLoginView) {
					// Redirect to login view always if a user has not yet
					// logged in
					navigator.navigateTo("loginView");
					return false;

				} else if (isLoggedIn && isLoginView) {
					// If someone tries to access to login view while logged in,
					// then cancel
					// Navigate to main view
		            getUI().getNavigator().navigateTo("domainSelectionView");
					return false;
				}

				return true;
			}

			@Override
			public void afterViewChange(ViewChangeEvent event) {}
			
		});

		navigator.navigateTo("loginView");
	}

	public static PropertyManager getPropertyManager() {
		return MedinsUI.propertyManager;
	}

	public static void setPropertyManager(PropertyManager propertyManager) {
		MedinsUI.propertyManager = propertyManager;
	}

	public DaoIntrfc getDao() {
		return dao;
	}

	public void setDao(DaoIntrfc dao) {
		this.dao = dao;
	}

	public Navigator getNavigator() {
		return navigator;
	}

	public void setNavigator(Navigator navigator) {
		this.navigator = navigator;
	}
	
	public EditPopupWindow getActiveEditPopupWindow() {
		return activeEditPopupWindow;
	}
	
	public void setActiveEditPopupWindow(final EditPopupWindow activeEditPopup) {
		removeActiveEditPopupWindow();
		activeEditPopupWindow = activeEditPopup;
	}
	
	public ListPopupWindow getActiveListPopupWindow() {
		return activeListPopupWindow;
	}

	public void setActiveListPopupWindow(final ListPopupWindow activeListPopup) {
		removeActiveListPopupWindow();
		activeListPopupWindow = activeListPopup;
	}
	
	

	public void removeActiveEditPopupWindow() {
		getCurrent().removeWindow(activeEditPopupWindow);
		if (activeEditPopupWindow != null) {
			activeEditPopupWindow.close();
		}
	}

	public void removeActiveListPopupWindow() {
		getCurrent().removeWindow(activeListPopupWindow);
		if (activeListPopupWindow != null) {
			activeListPopupWindow.close();
		}
	}

	public void showEditPopup(final EditPopupWindow editPopup) {
		getCurrent().addWindow(editPopup);
		setActiveEditPopupWindow(editPopup);
	}

	public void showListPopup(final ListPopupWindow listPopup) {
		removeActiveListPopupWindow();
		getCurrent().addWindow(listPopup);
		setActiveListPopupWindow(listPopup);
	}

	public void removeActivePopupWindows() {
		removeActiveEditPopupWindow();
		removeActiveListPopupWindow();
	}


}