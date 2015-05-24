package web.activities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import web.forms.ContactForm;
import web.forms.DiagnosisForm;
import web.forms.CaseAddInfoForm;
//import web.forms.DiagnosisForm;
import web.forms.Form;
import web.forms.MedicalReportForm;
import web.forms.NewValidationForm;
import web.forms.PersonForm;
import web.forms.PersonTouristVisitForm;
import web.forms.PolicyForm;
import web.forms.SearchPersonForm;
import web.forms.ServicesForm;
import web.forms.ValidationForm;
import web.views.AbstractActivityView;


import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;

import database.dao.DaoIntrfc;
import database.pojo.CaseInfo;
import database.pojo.Diagnosis;
import database.pojo.MedicalReport;
import database.pojo.Persons;
import database.pojo.Policies;
import database.pojo.Services;
import database.pojo.TouristVisit;

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
				new SearchPersonForm(this, "stepCreatePerson"),
				new CaseAddInfoForm(this,"stepCaseGeneralInfo"),
				new PersonTouristVisitForm(this,"stepTouristVisit"),
				new PolicyForm(this,"stepPolicy"),
				new ServicesForm(this,"stepServices"),
				new MedicalReportForm(this, "stepMedicalReport"),
				new DiagnosisForm(this, "stepDiagnosis"),
				new NewValidationForm(this,"stepValidate")
		};
		setRequiredSteps(requiredSteps);

		Form[] optionalSteps = {};
		setOptionalSteps(optionalSteps);

		setValidationMethod("validateCase");
		setMode("create");

		return super.buildLayout();
	}

	protected boolean validate(HashMap<String, Form> steps){
		//get  Patien info (person)
		Persons patient = (Persons) steps.get("stepCreatePerson").getData();

		//get case general info
		CaseInfo caseInfo = (CaseInfo) steps.get("stepCaseGeneralInfo").getData();

		//get Temp address info
		TouristVisit touristVisit = (TouristVisit) steps.get("stepTouristVisit").getData();
		touristVisit.setPersons(patient);

		//get Policy info
		Policies policy = (Policies) steps.get("stepPolicy").getData();

		//get case services
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

		//get medical report (it contains Diagnosis list)
		MedicalReport medicalReport = (MedicalReport)steps.get("stepDiagnosis").getData();
		caseInfo.setMedicalReport(medicalReport);

		Set<Object> objectSet = new HashSet<Object>(updatedServices);

		caseInfo.setPersonsByPatientInfo(patient);
		caseInfo.setPolicies(policy);
		caseInfo.setReferanceNumber1(patient.getSocialNumber());
		
		Iterator<Object> iterator = objectSet.iterator();
		Object object;
		while(iterator.hasNext()){
			object = iterator.next();
			System.out.println(object);
			if(object != null){
				getDao().saveOrUpdate(object);
			}
		}

		Object[] objects  = {
				touristVisit,
				policy,
				medicalReport,
				caseInfo
		};

		for(int i = 0; i < objects.length; i++){
			if (objects[i] != null) {
				getDao().saveOrUpdate(objects[i]);
			}
		}

		return true;
	}

}