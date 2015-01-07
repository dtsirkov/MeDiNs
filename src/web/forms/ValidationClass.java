package web.forms;

import pojo_classes.*;


import com.vaadin.ui.CustomComponent;

import dao_classes.DaoIntrfc;

public class ValidationClass {

	private boolean isValidated = false;
	private String validationMethod;
	private DaoIntrfc dao;
	private Form[] requiredSteps;
	private Form[] optionalSteps;

	public ValidationClass(String validationMethod, Form[] requiredSteps, Form[] optionalSteps){

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

	public void setRequiredSteps(Form[] requiredSteps) {
		this.requiredSteps = requiredSteps;
	}

	public CustomComponent[] getOptionalSteps() {
		return optionalSteps;
	}

	public void setOptionalSteps(Form[] optionalSteps) {
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

			Persons person = (Persons)(getRequiredSteps()[0]).getData();
			Contacts contact = (Contacts)(getRequiredSteps()[1]).getData();
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
