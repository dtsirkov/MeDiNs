package web;


import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;

import property_pckg.PropertyManager;
import web.forms.Activity;
import web.forms.Domain;
import web.views.CreatePersonActivity;
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


	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = MedinsUI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {

		PropertyManager propertyManager = new PropertyManager(request);
		DaoIntrfc dao = new DaoImpl(propertyManager.getLanguage());
		Navigator navigator = new Navigator(this, this);

		// Create and register the views
		navigator.addView("loginView", new LoginView(propertyManager, dao, navigator));

		DomainSelectionView domainSelectionView =  new DomainSelectionView(propertyManager, dao, navigator);

		ArrayList<Domain> domainList = new ArrayList<Domain>();
		domainList.add(new Domain("caseDomain"));

		Domain personOrganizationDomain = new Domain("personOrganizationDomain");

		CreatePersonActivity createPersonActivity = new CreatePersonActivity(propertyManager, dao, navigator);
		personOrganizationDomain.addActivity(new Activity(createPersonActivity));

		domainList.add(personOrganizationDomain);
		domainList.add(new Domain("medicalDomain"));
		domainList.add(new Domain("treatmentDomain"));
		domainSelectionView.setDomainList(domainList);

		navigator.addView("domainSelectionView", domainSelectionView);

		navigator.navigateTo("loginView");

	}

}