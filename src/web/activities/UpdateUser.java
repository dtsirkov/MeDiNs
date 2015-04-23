package web.activities;


import web.forms.ContactForm;
import web.forms.Form;
import web.forms.PersonForm;
import web.forms.SearchPersonForm;
import web.forms.UserForm;
import web.forms.ValidationForm;
import web.views.AbstractActivityView;


import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;

@Theme("medins")
public class UpdateUser extends AbstractActivityView{

	private static final long serialVersionUID = 1L;

	public UpdateUser(UI ui) {
		super(ui);
		setLabel("userUpdate");
	}

	@Override
	public void enter(ViewChangeEvent event) {
		setCompositionRoot(buildLayout());
	}

	public Layout buildLayout(){

		Form[] requiredSteps = {
				new SearchPersonForm(this, "stepSearchPerson"), 
				new PersonForm(this, "stepCreatePerson"), 
				new ContactForm(this, "stepCreateContact"), 
				new UserForm(this,"stepCreateUser"),
				new ValidationForm(this, "stepValidate")
		};
		setRequiredSteps(requiredSteps);

		Form[] optionalSteps = {};
		setOptionalSteps(optionalSteps);

		setValidationMethod("validateUser");
		setMode("update");

		return super.buildLayout();
	}


}