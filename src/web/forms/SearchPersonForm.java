package web.forms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import java.util.function.Consumer;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Table.ColumnGenerator;

import dao_classes.DaoIntrfc;

import pojo_classes.Contacts;
import pojo_classes.Enumerations;
import pojo_classes.PersonContactLink;
import pojo_classes.Persons;
import property_pckg.PropertyManager;
import web.StepIntrfc;
import web.classes.ComponentValidator;
import web.components.PagedTable;
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

		return new String[] { "First Name", "Last Name", "Social Number" };

	}

	public ArrayList<Persons> search() {
		return new ArrayList<Persons>();
	}

	@Override
	public void process(HashMap<String, Form> steps) {

		//get access to DB
		DaoIntrfc dao = getDao();

		Persons selectedPerson = getSelectedItem();

		steps.get("stepCreatePerson").setData(selectedPerson);

		Map<Object, List<Object>> hmPerson = new HashMap<Object, List<Object>>();
		List<Object> personsLs = new ArrayList<Object>();
		personsLs.add(selectedPerson);
		hmPerson.put("persons", personsLs);
		List<Object> personAddressLinks = dao.findByExample(new PersonContactLink(), hmPerson);

		Enumerations enumeration;
		Contacts contact;
		for(Object personAddressLink : personAddressLinks){
			contact = ((PersonContactLink) personAddressLink).getContacts();
			enumeration = contact.getEnumerationsByActive();
			if(enumeration.getCode().equals("yes")){
				steps.get("stepCreateContact").setData(contact);
			}
		}
	}

}
