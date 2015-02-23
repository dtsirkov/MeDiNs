package web.activities;

import web.forms.Form;
import web.forms.PersonForm;
import web.forms.ValidationForm;
import web.views.AbstractActivityView;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;

public class CreateCase extends AbstractActivityView{

	private static final long serialVersionUID = 1L;

	public CreateCase(UI ui) {

		super(ui);
		setLabel("createCase");

	}

	@Override
	public void enter(ViewChangeEvent event) {
		setCompositionRoot(buildLayout());
	}

	public Layout buildLayout(){

		Form[] requiredSteps = {
				new PersonForm(this, "stepCreatePerson"),
				new ValidationForm(this, "stepValidate")
		};
		setRequiredSteps(requiredSteps);

		Form[] optionalSteps = {};
		setOptionalSteps(optionalSteps);

		setValidationMethod("validateCase");
		setMode("create");

		return super.buildLayout();
	}

}
