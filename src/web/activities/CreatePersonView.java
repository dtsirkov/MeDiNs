package web.activities;


import web.forms.ContactForm;
import web.forms.Form;
import web.forms.PersonForm;
import web.forms.ValidationForm;


import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;

@Theme("medins")
public class CreatePersonView extends AbstractActivityView{

	private static final long serialVersionUID = 1L;

	public CreatePersonView(UI ui) {

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
				new ValidationForm(this, "stepValidate")
		};
		setRequiredSteps(requiredSteps);

		Form[] optionalSteps = {};
		setOptionalSteps(optionalSteps);

		setValidationMethod("validatePerson");
		setMode("create");
		
		return super.buildLayout();
	}


}