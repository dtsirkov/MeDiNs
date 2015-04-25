package web.activities;


import java.util.HashMap;
import java.util.Set;

import web.forms.ContactForm;
import web.forms.Form;
import web.forms.PersonForm;
import web.forms.SearchPersonForm;
import web.forms.ValidationForm;
import web.views.AbstractActivityView;


import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;

import database.pojo.Contacts;
import database.pojo.Persons;

@Theme("medins")
public class UpdatePerson extends AbstractActivityView{

	private static final long serialVersionUID = 1L;

	public UpdatePerson(UI ui) {

		super(ui);
		setLabel("personUpdate");

	}

	@Override
	public void enter(ViewChangeEvent event) {
		setCompositionRoot(buildLayout());
	}

	public Layout buildLayout(){

		SearchPersonForm searchPersonForm = new SearchPersonForm(this, "stepSearchPerson");
		PersonForm personForm = new PersonForm(this, "stepCreatePerson"); 
		ContactForm contactForm = new ContactForm(this, "stepCreateContact");
		ValidationForm validationForm = new ValidationForm(this, "stepValidate");

		Form[] requiredSteps = {
				searchPersonForm,
				personForm,
				contactForm, 
				validationForm
		};
		setRequiredSteps(requiredSteps);

		Form[] optionalSteps = {};
		setOptionalSteps(optionalSteps);

		setValidationMethod("validatePerson");
		setMode("update");

		return super.buildLayout();
	}
	
	protected boolean validate(HashMap<String, Form> hmRequiredSteps, HashMap<String, Form> hmOptionalSteps){		

		Persons person = (Persons)hmRequiredSteps.get("stepCreatePerson").getData();
		Contacts contact = (Contacts)hmRequiredSteps.get("stepCreateContact").getData();
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