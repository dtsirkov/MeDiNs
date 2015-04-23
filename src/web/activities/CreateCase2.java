package web.activities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import pojo.classes.CaseInfo;
import pojo.classes.Services;
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

	protected boolean validate(HashMap<String, Form> hmRequiredSteps, HashMap<String, Form> hmOptionalSteps){

		CaseInfo caseInfo = (CaseInfo)hmRequiredSteps.get("stepValidate").getData();
		@SuppressWarnings("unchecked")
		Set<Services> services = (Set<Services>)hmRequiredSteps.get("stepServices").getData();

		Set<Services> oldServicesSet = caseInfo.getServiceses();
		oldServicesSet.size();

		Iterator<Services> serviceIterator = oldServicesSet.iterator();
		while(serviceIterator.hasNext()){
			getDao().delete(serviceIterator.next());
		}

		caseInfo.setServiceses(services);

		Set<Object> objectSet = new HashSet<Object>(services);
		objectSet.add(caseInfo);

		Iterator<Object> iterator = objectSet.iterator();
		Object object;
		while(iterator.hasNext()){
			object = iterator.next();
			System.out.println(object);
			if(object != null){
				getDao().saveOrUpdate(object);
			}
		}

		return true;
	}

}
