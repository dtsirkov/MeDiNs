package web.classes;

import java.util.HashMap;

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

			if(getValidationMethod().equals("validatePerson")){
				setValidated(validatePerson(mode));
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
		PersonContactLink personContactLink = new PersonContactLink(person, contact);

		Object[] objects  = {
				person,
				contact, 
				personContactLink
		};


		for(int i = 0; i < objects.length; i++){
			dao.saveOrUpdate(objects[i]);
		}

		return true;
	}

}
