package web.activities;


import java.util.HashMap;
import java.util.Set;

import web.forms.ContactForm;
import web.forms.Form;
import web.forms.NewValidationForm;
import web.forms.PersonForm;
import web.views.AbstractActivityView;


import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;

import database.pojo.Contacts;
import database.pojo.Persons;

@Theme("medins")
public class CreatePerson extends AbstractActivityView{

	private static final long serialVersionUID = 1L;

	public CreatePerson(UI ui) {

		super(ui);
		setLabel("personCreate");

	}

	@Override
	public void enter(ViewChangeEvent event) {
		setCompositionRoot(buildLayout());
	}

	public Layout buildLayout(){

		Form[] requiredSteps = {
				new PersonForm(this, "stepCreatePerson"), 
				new ContactForm(this, "stepCreateContact"), 
				new NewValidationForm(this, "stepValidate")
				//new ValidationForm(this, "stepValidate")
		};
		setRequiredSteps(requiredSteps);

		Form[] optionalSteps = {};
		setOptionalSteps(optionalSteps);

		setValidationMethod("validatePerson");
		setMode("create");

		return super.buildLayout();
	}

	protected boolean validate(HashMap<String, Form> steps){		

		Persons person = (Persons)steps.get("stepCreatePerson").getData();
		Contacts contact = (Contacts)steps.get("stepCreateContact").getData();
		Set<Contacts> contactses=person.getContactses();
		//load children
		contactses.size();
		contactses.add(contact);
		person.setContactses(contactses);

		Object[] objects  = {
				contact,
				person
		};

		for(int i = 0; i < objects.length; i++){
			getDao().saveOrUpdate(objects[i]);
		}

		return true;
	}

}