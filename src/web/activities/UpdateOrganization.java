package web.activities;


import web.forms.ContactForm;
import web.forms.Form;
import web.forms.OrganizationForm;
import web.forms.PersonForm;
import web.forms.SearchOrganizationForm;
import web.forms.SearchPersonForm;
import web.forms.ValidationForm;
import web.views.AbstractActivityView;


import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;

@Theme("medins")
public class UpdateOrganization extends AbstractActivityView{

	private static final long serialVersionUID = 1L;

	public UpdateOrganization(UI ui) {
		super(ui);
		setLabel("organizationUpdate");
	}

	@Override
	public void enter(ViewChangeEvent event) {
		setCompositionRoot(buildLayout());
	}

	public Layout buildLayout(){

		Form[] requiredSteps = { new SearchOrganizationForm(this, "stepSearchOrganization"),
		new OrganizationForm(this, "stepOrganization"),
		new ContactForm(this, "stepCreateContact"), 
		new ValidationForm(this, "stepValidate")
		};
		
		setRequiredSteps(requiredSteps);

		Form[] optionalSteps = {};
		setOptionalSteps(optionalSteps);

		setValidationMethod("validateOrganization");
		setMode("update");

		return super.buildLayout();
	}


}