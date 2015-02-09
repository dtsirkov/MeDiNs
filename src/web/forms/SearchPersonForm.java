package web.forms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;


import dao.classes.DaoIntrfc;

import pojo.classes.Contacts;
import pojo.classes.Enumerations;
import pojo.classes.PersonContactLink;
import pojo.classes.Persons;
import web.StepIntrfc;
import web.classes.ComponentValidator;
import web.classes.PropertyManager;
import web.views.AbstractView;

public class SearchPersonForm extends SearchForm<Persons> implements StepIntrfc{

	private static final long serialVersionUID = 1L;

	public SearchPersonForm(AbstractView view, String label){
		super(view, label);
		setdBTableName("Persons");
	}

	public FormLayout buildSearchByCriteriaLayout(){

		//get component validator
		ComponentValidator componentValidator = getComponentValidator();
		//get propertyManager
		PropertyManager propertyManager = getPropertyManager();

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
		addToSearchConstraint(personFirstNameTF, "firstName");

		// personLastNameTF
		TextField personLastNameTF = new TextField(propertyManager.getLabelDtl("personLastName"));
		personLastNameTF.setImmediate(true);
		personLastNameTF.setWidth(width);
		personLastNameTF.setHeight(height);
		personLastNameTF.addValidator(componentValidator.getOnlyLettersValidator("OnlyLettersAllowed"));
		formLayout.addComponent(personLastNameTF);
		addToSearchConstraint(personLastNameTF, "lastName");
		
		getSearchByKeyLayout().setVisible(false);

		setSearchByCriteriaLayout(formLayout);
		setId("socialNumber");
		return formLayout;
	}

	public IndexedContainer createContainer() {
		IndexedContainer container = new IndexedContainer();
		container.addContainerProperty("firstName", String.class, null);
		container.addContainerProperty("lastName", String.class, null);
		container.addContainerProperty("socialNumber", String.class, null);
		ArrayList<Persons> searchResult = getSearchResult();
		for (int i = 0; i < searchResult.size(); i++) {
			Item item = container.addItem(searchResult.get(i));
			item.getItemProperty("firstName").setValue(searchResult.get(i).getFirstName());
			item.getItemProperty("lastName").setValue(searchResult.get(i).getLastName());
			item.getItemProperty("socialNumber").setValue(searchResult.get(i).getSocialNumber());
		}
		container.sort(new Object[] { "firstName" }, new boolean[] { true });
		return container;
	}

	public String[] getTableHeader(){
		
		PropertyManager propertyManager = getPropertyManager();
		
		String firstName = propertyManager.getLabelDtl("personFirstName");
		String lastName = propertyManager.getLabelDtl("personLastName");
		String socialNumber = propertyManager.getLabelDtl("personSocialNumber");

		return new String[] { firstName, lastName, socialNumber};

	}

	public ArrayList<Persons> search() {
		return new ArrayList<Persons>();
	}

	@Override
	public boolean process(HashMap<String, Form> steps) {

		boolean processed = false;

		//get access to DB
		DaoIntrfc dao = getDao();

		Persons selectedPerson = getSelectedItem();
		dao.evict(selectedPerson);

		steps.get("stepCreatePerson").setData(selectedPerson);

		Map<Object, List<Object>> hmPerson = new HashMap<Object, List<Object>>();
		List<Object> personsLs = new ArrayList<Object>();
		personsLs.add(selectedPerson);
		hmPerson.put("persons", personsLs);
		List<Object> personAddressLinks = dao.findByExample(new PersonContactLink(), hmPerson);

		Enumerations enumeration;
		Contacts contact = new Contacts();
		for(Object personAddressLink : personAddressLinks){
			dao.evict(personAddressLink);
			contact = ((PersonContactLink) personAddressLink).getContacts();
			enumeration = contact.getEnumerationsByActive();
			if(enumeration.getCode().equals("yes")){
				dao.evict(contact);
				steps.get("stepCreateContact").setData(contact);
			}
		}
		
		processed = true;
		return processed;

	}

}
