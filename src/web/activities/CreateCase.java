package web.activities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import pojo.classes.CaseInfo;
import pojo.classes.Services;

import web.forms.CasePersonForm;
//import web.forms.DiagnosisForm;
import web.forms.Form;
import web.forms.MedicalReportForm;
import web.forms.PersonForm;
import web.forms.PersonTouristVisitForm;
import web.forms.PolicyForm;
import web.forms.ServicesForm;
import web.forms.ValidationForm;
import web.views.AbstractActivityView;


import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;

import dao.classes.DaoIntrfc;

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

		DaoIntrfc dao = this.getDao();
		CaseInfo caseInfo = (CaseInfo)dao.findById("CaseInfo", 1);

		Set<Services> services = caseInfo.getServiceses();

		Form serviceForm = new ServicesForm(this,"stepServices");
		services.size();
		serviceForm.setData(services);

		Form validationForm = new ValidationForm(this, "stepValidate");
		validationForm.setData(caseInfo);

		Form[] requiredSteps = {
				//new CasePersonForm(this, "stepCreatePerson"),
				//new PersonTouristVisitForm(this,"stepTouristVisit"),
				//new PolicyForm(this,"stepPolicy"),
				//new MedicalReportForm(this,"stepMedicalReport"),
				//new ServicesForm(this,"stepServices"),
				serviceForm,
				//new DiagnosisForm(this,"stepDiagnosis"),
				//new ValidationForm(this, "stepValidate")
				validationForm
		};
		setRequiredSteps(requiredSteps);

		Form[] optionalSteps = {};
		setOptionalSteps(optionalSteps);

		setValidationMethod("validateCase");
		setMode("update");

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
