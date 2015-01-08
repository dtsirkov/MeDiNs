package web.views;


import property_pckg.PropertyManager;
import web.forms.ContactForm;
import web.forms.Form;
import web.forms.PersonForm;
import web.forms.ValidationForm;


import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;

import dao_classes.DaoIntrfc;

@Theme("medins")
public class CreatePersonActivity extends AbstractActivityView{

	private static final long serialVersionUID = 1L;

	public CreatePersonActivity(PropertyManager propertyManager, DaoIntrfc dao, Navigator navigator) {

		super(propertyManager, dao, navigator);
		setLabel("personCreate");

		final Form[] requiredSteps = {
				new PersonForm(this, "stepCreatePerson"), 
				new ContactForm(this, "stepCreateContact"), 
				new ValidationForm(this, "stepValidate")
		};
		setRequiredSteps(requiredSteps);

		final Form[] optionalSteps = {};
		setOptionalSteps(optionalSteps);

		this.setValidationMethod("validatePerson");
		this.setMode("no_update");

	}

	@Override
	public void enter(ViewChangeEvent event) {
		setCompositionRoot(buildLayout());
	}


}