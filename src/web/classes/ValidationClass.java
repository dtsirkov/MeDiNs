package web.classes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.Transaction;

import pojo.classes.*;
import web.forms.Form;

import dao.classes.DaoIntrfc;

public class ValidationClass {

	private boolean isValidated = false;
	private String validationMethod;
	private DaoIntrfc dao;
	private HashMap<String, Form> requiredSteps;
	private HashMap<String, Form> optionalSteps;

	public ValidationClass(String validationMethod, HashMap<String, Form> requiredSteps, HashMap<String, Form> optionalSteps){

		setValidationMethod(validationMethod);
		setRequiredSteps(requiredSteps);
		setOptionalSteps(optionalSteps);

	}

	public boolean isValidated() {
		return isValidated;
	}

	public void setValidated(boolean isValidated) {
		this.isValidated = isValidated;
	}


	public String getValidationMethod() {
		return validationMethod;
	}

	public void setValidationMethod(String validationMethod) {
		this.validationMethod = validationMethod;
	}

	public DaoIntrfc getDao() {
		return dao;
	}

	public void setDao(DaoIntrfc dao) {
		this.dao = dao;
	}

	public HashMap<String, Form> getRequiredSteps() {
		return requiredSteps;
	}

	public void setRequiredSteps(HashMap<String, Form> requiredSteps) {
		this.requiredSteps = requiredSteps;
	}

	public HashMap<String, Form> getOptionalSteps() {
		return optionalSteps;
	}

	public void setOptionalSteps(HashMap<String, Form> optionalSteps) {
		this.optionalSteps = optionalSteps;
	}

	public boolean validate(String mode){
		Transaction trans = getDao().getTransaction();
		try{

			String validationMethod=getValidationMethod();
			/*if(getValidationMethod().equals("validatePerson")){
				setValidated(validatePerson(mode));
			}*/
			switch(validationMethod){
			case "validatePerson":
				setValidated(validatePerson(mode));
				break;
			case "validateOrganization":
				setValidated(validateOrganization(mode));
				break;
			}

			trans.commit();

		}catch(Exception e){
			System.out.println("Error in validation method.");
			trans.rollback();
		}
		return isValidated();
	}

	private boolean validatePerson(String mode){		

		Persons person = (Persons)getRequiredSteps().get("stepCreatePerson").getData();
		Contacts contact = (Contacts)getRequiredSteps().get("stepCreateContact").getData();
		Set<Contacts> contactses=person.getContactses();
		//load children
		contactses.size();
		contactses.add(contact);
		person.setContactses(contactses);

		Object[] objects  = {
				contact,
				person
		};

		for(int i = 0; i < objects.length; i++){
			dao.saveOrUpdate(objects[i]);
		}

		return true;
	}

	private boolean validateOrganization(String mode){		

		Organizations organization = (Organizations)getRequiredSteps().get("stepOrganization").getData();
		Contacts contact = (Contacts)getRequiredSteps().get("stepCreateContact").getData();
		Set<Contacts> contactses=organization.getContactses();
		//load children
		contactses.size();
		contactses.add(contact);
		organization.setContactses(contactses);
		
		Object[] objects  = {
				contact,
				organization
		};


		for(int i = 0; i < objects.length; i++){
			dao.saveOrUpdate(objects[i]);
		}

		return true;
	}

}
