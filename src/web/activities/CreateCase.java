package web.activities;

import web.forms.CasePersonForm;
import web.forms.DiagnosisForm;
import web.forms.Form;
import web.forms.MedicalReportForm;
import web.forms.PersonForm;
import web.forms.PersonTouristVisitForm;
import web.forms.PolicyForm;
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
				//new CasePersonForm(this, "stepCreatePerson"),
				new PersonTouristVisitForm(this,"stepTouristVisit"),
				new PolicyForm(this,"stepPolicy"),
				new MedicalReportForm(this,"stepMedicalReport"),
				new DiagnosisForm(this,"stepDiagnosis"),
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
