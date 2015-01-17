package web.forms;

import java.util.ArrayList;

import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import dao_classes.DaoIntrfc;

import pojo_classes.Persons;
import property_pckg.PropertyManager;
import web.classes.ComponentValidator;
import web.views.AbstractView;

public class SearchPersonForm extends SearchForm<Persons>{

	private static final long serialVersionUID = 1L;

	public SearchPersonForm(AbstractView view, String label){
		super(view, label);
		setTable("Persons");
	}

	public FormLayout buildSearchByCriteriaLayout(){

		//get component validator
		ComponentValidator componentValidator = getComponentValidator();
		//get propertyManager
		PropertyManager propertyManager = getPropertyManager();
		//get access to DB
		DaoIntrfc dao = getDao();

		//define measurements of the components 
		String width = "180px", height = "-1px";

		FormLayout formLayout = new FormLayout();

		// personFirstNameTF
		TextField personFirstNameTF = new TextField(propertyManager.getLabelDtl("personFirstName"));
		personFirstNameTF.setImmediate(true);
		personFirstNameTF.setWidth(width);
		personFirstNameTF.setHeight(height);
		personFirstNameTF.addValidator(componentValidator.getOnlyLettersValidator("OnlyLettersAllowed"));
		formLayout.addComponent(personFirstNameTF);

		// personLastNameTF
		TextField personLastNameTF = new TextField(propertyManager.getLabelDtl("personLastName"));
		personLastNameTF.setImmediate(true);
		personLastNameTF.setWidth(width);
		personLastNameTF.setHeight(height);
		personLastNameTF.addValidator(componentValidator.getOnlyLettersValidator("OnlyLettersAllowed"));
		formLayout.addComponent(personLastNameTF);

		setSearchByCriteriaLayout(formLayout);

		return formLayout;
	}

	public ArrayList<Persons> search() {
		return new ArrayList<Persons>();
	}

}
