package web;


import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;

import property_pckg.PropertyManager;
import web.activities.CreatePersonView;
import web.classes.Activity;
import web.classes.Domain;
import web.views.DomainSelectionView;
import web.views.LoginView;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import dao_classes.DaoImpl;
import dao_classes.DaoIntrfc;

@SuppressWarnings("serial")
@Theme("medins")
public class MedinsUI extends UI {
	
	private PropertyManager propertyManager;
	private DaoIntrfc dao;
	private Navigator navigator;
	
	public MedinsUI(){
		
	}

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

		Domain personOrganizationDomain = new Domain("personOrganizationDomain");
		Activity createPersonActivity = new Activity(new CreatePersonView(this));
		personOrganizationDomain.addActivity(createPersonActivity);

		ArrayList<Domain> domainList = new ArrayList<Domain>();
		
		domainList.add(new Domain("caseDomain"));
		domainList.add(personOrganizationDomain);
		domainList.add(new Domain("medicalDomain"));
		domainList.add(new Domain("treatmentDomain"));
		domainSelectionView.setDomainList(domainList);

		navigator.addView("domainSelectionView", domainSelectionView);

		navigator.navigateTo("loginView");

	}

	public PropertyManager getPropertyManager() {
		return propertyManager;
	}

	public void setPropertyManager(PropertyManager propertyManager) {
		this.propertyManager = propertyManager;
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

}