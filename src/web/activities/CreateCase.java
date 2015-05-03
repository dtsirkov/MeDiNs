package web.activities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import web.forms.ContactForm;
//import web.forms.DiagnosisForm;
import web.forms.Form;
import web.forms.NewValidationForm;
import web.forms.PersonForm;
import web.forms.ServicesForm;
import web.views.AbstractActivityView;


import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;

import database.dao.DaoIntrfc;
import database.pojo.CaseInfo;
import database.pojo.Services;

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

		Form serviceForm = new ServicesForm(this, "stepServices");
		services.size();
		serviceForm.setData(services);

		Form validationForm = new NewValidationForm(this, "stepValidate");
		validationForm.setData(caseInfo);

		Form[] requiredSteps = {
				//new SearchPersonForm(this, "stepCreatePerson"),
				//new PersonTouristVisitForm(this,"stepTouristVisit"),
				//new PolicyForm(this,"stepPolicy"),
				//new MedicalReportForm(this,"stepMedicalReport"),
				//new ServicesForm(this,"stepServices"),
				//new PersonForm(this, "stepCreatePerson"), 
				//new ContactForm(this, "stepCreateContact"),
				serviceForm,
				//new DiagnosisForm(this,"stepDiagnosis"),
				//new ValidationForm(this, "stepValidate")
				validationForm
		};
		setRequiredSteps(requiredSteps);

		Form[] optionalSteps = {};
		setOptionalSteps(optionalSteps);

		setValidationMethod("validateCase");
		setMode("create");

		return super.buildLayout();
	}

	protected boolean validate(HashMap<String, Form> steps){

		CaseInfo caseInfo = (CaseInfo)steps.get("stepValidate").getData();
		@SuppressWarnings("unchecked")
		Set<Services> updatedServices = (Set<Services>)steps.get("stepServices").getData();

		Set<Integer> updatedServiceIds = new HashSet<Integer>(); 
		Iterator<Services> updatedServicesIterator = updatedServices.iterator();
		while(updatedServicesIterator.hasNext()){
			updatedServiceIds.add(updatedServicesIterator.next().getId());
		}

		Set<Services> oldServicesSet = caseInfo.getServiceses();
		oldServicesSet.size();
		Iterator<Services> oldServicesIterator = oldServicesSet.iterator();
		Services tmpService;
		int tmpId;
		while(oldServicesIterator.hasNext()){
			tmpService = oldServicesIterator.next();
			tmpId = tmpService.getId();
			if(!updatedServiceIds.contains(tmpId))
				getDao().delete(tmpService);
		}

		caseInfo.setServiceses(updatedServices);

		Set<Object> objectSet = new HashSet<Object>(updatedServices);
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