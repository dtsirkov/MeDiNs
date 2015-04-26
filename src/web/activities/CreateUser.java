package web.activities;


import java.util.HashMap;
import java.util.Set;

import web.forms.ContactForm;
import web.forms.Form;
import web.forms.PersonForm;
import web.forms.UserForm;
import web.forms.ValidationForm;
import web.views.AbstractActivityView;


import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;

import database.pojo.Contacts;
import database.pojo.Persons;
import database.pojo.Users;

@Theme("medins")
public class CreateUser extends AbstractActivityView{

	private static final long serialVersionUID = 1L;

	public CreateUser(UI ui) {
		super(ui);
		setLabel("userCreate");
	}

	@Override
	public void enter(ViewChangeEvent event) {
		setCompositionRoot(buildLayout());
	}

	public Layout buildLayout(){

		Form[] requiredSteps = {
				new PersonForm(this, "stepCreatePerson"), 
				new ContactForm(this, "stepCreateContact"), 
				new UserForm(this,"stepCreateUser"),
				new ValidationForm(this, "stepValidate")
		};
		setRequiredSteps(requiredSteps);

		Form[] optionalSteps = {};
		setOptionalSteps(optionalSteps);

		setValidationMethod("validateUser");
		setMode("create");

		return super.buildLayout();
	}

	@Override
	protected boolean validate(HashMap<String, Form> hmRequiredSteps,HashMap<String, Form> hmOptionalSteps) {
		Persons person = (Persons)hmRequiredSteps.get("stepCreatePerson").getData();
		Contacts contact = (Contacts)hmRequiredSteps.get("stepCreateContact").getData();
		Users user = (Users)hmRequiredSteps.get("stepCreateUser").getData();

		Set<Contacts> contactses=person.getContactses();
		//load children
		contactses.size();
		contactses.add(contact);
		person.setContactses(contactses);

		Object[] objects  = {
				contact,
				person,
				user
		};

		for(int i = 0; i < objects.length; i++){
			getDao().saveOrUpdate(objects[i]);
		}

		return true;
	}


}