package web;

import pojo_classes.*;

import com.vaadin.ui.CustomComponent;

import dao_classes.DaoIntrfc;

public class ValidationClass {

	private boolean isValidated = false;
	private String validationMethod;
	private DaoIntrfc dao;
	private CustomComponent[] requiredSteps;
	private CustomComponent[] optionalSteps;

	public ValidationClass(String validationMethod, CustomComponent[] requiredSteps, CustomComponent[] optionalSteps){

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

	public CustomComponent[] getRequiredSteps() {
		return requiredSteps;
	}

	public void setRequiredSteps(CustomComponent[] requiredSteps) {
		this.requiredSteps = requiredSteps;
	}

	public CustomComponent[] getOptionalSteps() {
		return optionalSteps;
	}

	public void setOptionalSteps(CustomComponent[] optionalSteps) {
		this.optionalSteps = optionalSteps;
	}

	public boolean validate(String mode){
		if(this.getValidationMethod().equals("validatePerson")){
			return validatePerson(mode);
		}
		return isValidated();
	}

	private boolean validatePerson(String mode){		
		try{

			Persons person = (Persons)((Form)getRequiredSteps()[0]).getData();
			Contacts contact = (Contacts)((Form)getRequiredSteps()[1]).getData();
			PersonContactLink personContactLink = new PersonContactLink(person, contact);

			Object[] objectsToCreate  = {
					person,
					contact, 
					personContactLink
			};

			Object[] objectsToUpdate  = {
					person,
					contact
			};

			Object[] objects;
			if(mode.equals("update")){
				objects = objectsToUpdate;
			}else{
				objects = objectsToCreate;
			}
			dao.persist(objects);
			setValidated(true);

		}catch(Exception e){
			System.out.println("Error in personValidateMethod");
		}
		return isValidated();
	}

}
