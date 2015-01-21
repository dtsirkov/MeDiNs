package web.activities;


import web.forms.ContactForm;
import web.forms.Form;
import web.forms.PersonForm;
import web.forms.SearchPersonForm;
import web.forms.ValidationForm;


import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;

@Theme("medins")
public class UpdatePersonView extends AbstractActivityView{

	private static final long serialVersionUID = 1L;

	public UpdatePersonView(UI ui) {

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


}