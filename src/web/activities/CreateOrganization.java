package web.activities;


import web.forms.ContactForm;
import web.forms.Form;
import web.forms.OrganizationForm;
import web.forms.ValidationForm;
import web.views.AbstractActivityView;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;

public class CreateOrganization extends AbstractActivityView{

	private static final long serialVersionUID = 1L;

	public CreateOrganization(UI ui) {

		super(ui);
		setLabel("createOrganization");

	}

	@Override
	public void enter(ViewChangeEvent event) {
		setCompositionRoot(buildLayout());
	}

	public Layout buildLayout(){

		Form[] requiredSteps = {
				new OrganizationForm(this, "stepOrganization"),
				new ContactForm(this, "stepCreateContact"), 
				new ValidationForm(this, "stepValidate")
		};
		setRequiredSteps(requiredSteps);

		Form[] optionalSteps = {};
		setOptionalSteps(optionalSteps);

		setValidationMethod("validateOrganization");
		setMode("create");

		return super.buildLayout();
	}

}
