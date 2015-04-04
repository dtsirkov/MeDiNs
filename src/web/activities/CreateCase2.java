package web.activities;

import web.forms.ContactForm;
//import web.forms.DiagnosisForm;
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

public class CreateCase2 extends AbstractActivityView{

	private static final long serialVersionUID = 1L;

	public CreateCase2(UI ui) {
		super(ui);
		setLabel("createCase2");
	}

	@Override
	public void enter(ViewChangeEvent event) {
		setCompositionRoot(buildLayout());
	}

	public Layout buildLayout(){
		
		Form[] requiredSteps = {
				new PersonForm(this, "stepCreatePerson"), 
				new ContactForm(this, "stepCreateContact"),
				new PersonTouristVisitForm(this,"stepTouristVisit"),
				new PolicyForm(this,"stepPolicy"),
				new MedicalReportForm(this,"stepMedicalReport"),
				//new DiagnosisForm(this,"stepDiagnosis"),
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
