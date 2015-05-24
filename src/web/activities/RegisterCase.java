package web.activities;

import java.util.HashMap;
import java.util.Set;

import web.forms.CaseAddInfoForm;
import web.forms.ContactForm;
import web.forms.Form;
import web.forms.PersonForm;
import web.forms.PersonTouristVisitForm;
import web.forms.UserForm;
import web.forms.ValidationForm;
import web.views.AbstractActivityView;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;

import database.pojo.CaseInfo;
import database.pojo.Contacts;
import database.pojo.Persons;
import database.pojo.TouristVisit;
import database.pojo.Users;

@Theme("medins")
public class RegisterCase extends AbstractActivityView{

	private static final long serialVersionUID = 1L;

	public RegisterCase(UI ui) {
		super(ui);
		setLabel("registerCase");
	}

	@Override
	public void enter(ViewChangeEvent event) {
		setCompositionRoot(buildLayout());
	}

	public Layout buildLayout(){

		Form[] requiredSteps = { 
				new CaseAddInfoForm(this,"stepCaseGeneralInfo"),
				new PersonForm(this, "stepCreatePerson"),
				new PersonTouristVisitForm(this,"stepTouristVisit"), 
				new ValidationForm(this, "stepValidate")
		};
		setRequiredSteps(requiredSteps);

		Form[] optionalSteps = {};
		setOptionalSteps(optionalSteps);

		setValidationMethod("validateRegisterCase");
		setMode("create");

		return super.buildLayout();
	}

	@Override
	protected boolean validate(HashMap<String, Form> steps) {
		Persons patient = (Persons)steps.get("stepCreatePerson").getData();
		//get case general info
		CaseInfo caseInfo = (CaseInfo) steps.get("stepCaseGeneralInfo").getData();
		caseInfo.setPersonsByPatientInfo(patient);
		//get Temp address info
		TouristVisit touristVisit = (TouristVisit) steps.get("stepTouristVisit").getData();
		//touristVisit.setPersons(patient);
		caseInfo.setReferanceNumber1(patient.getSocialNumber());

		Object[] objects  = {
				patient,
				touristVisit,
				caseInfo
		};

		for(int i = 0; i < objects.length; i++){
			getDao().saveOrUpdate(objects[i]);
		}

		return true;
	}


}
