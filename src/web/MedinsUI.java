package web;


import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;

import property_pckg.PropertyManager;
import web.Forms.Domain;
import web.Views.ActivityView;
import web.Views.LoginView;

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
		
		ActivityView domainSelectionView =  new ActivityView(propertyManager, dao, navigator);
		
		ArrayList<Domain> domainList = new ArrayList<Domain>();
		domainList.add(new Domain("caseDomain"));
		domainList.add(new Domain("personOrganizationDomain"));
		domainList.add(new Domain("medicalDomain"));
		domainList.add(new Domain("treatmentDomain"));
		domainSelectionView.setDomainList(domainList);
		
		navigator.addView("activityView", domainSelectionView);

		navigator.navigateTo("loginView");

	}

}