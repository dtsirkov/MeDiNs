package web;

import pojo_classes.*;

import com.vaadin.ui.CustomComponent;

import dao_classes.DaoIntrfc;

public class ValidationClass {

	boolean isValidated = false;
	String validationMethod;
	CustomComponent[] requiredSteps;
	CustomComponent[] optionalSteps;

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

	public boolean validate(){
		if(this.getValidationMethod().equals("createPerson")){
			return createPerson();
		}
		return isValidated();
	}

	private boolean createPerson(){		
		try{

			Persons person = (Persons)((Form)getRequiredSteps()[0]).getObjectArray()[0];
			Contacts contact = (Contacts)((Form)getRequiredSteps()[1]).getObjectArray()[0];
			PersonContactLink personContactLink = new PersonContactLink(person, contact);

			DaoIntrfc dao = ((Form)getRequiredSteps()[0]).getDao();

			dao.persist(person);
			dao.persist(contact);
			dao.persist(personContactLink);

			setValidated(true);

		}catch(Exception e){}

		return isValidated();
	}

}
