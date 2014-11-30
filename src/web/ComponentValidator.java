package web;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import property_pckg.PropertyManager;

import com.vaadin.data.Validator;
import com.vaadin.data.Validator.EmptyValueException;
import com.vaadin.server.UserError;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;

import dao_classes.DaoIntrfc;

class ComponentValidator implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2270732896484883778L;
	private PropertyManager propertyManager;

	public ComponentValidator(PropertyManager propertyManager){
		this.setPropertyManager(propertyManager);
	}
	
	public PropertyManager getPropertyManager() {
		return propertyManager;
	}

	public void setPropertyManager(PropertyManager propertyManager) {
		this.propertyManager = propertyManager;
	}

	//validate social number
	protected final Validator getOnlyDigitsValidator(final String propertyName){
		class MyValidator implements Validator {
			private static final long serialVersionUID = -8281962473854901819L;
			@Override
			public void validate(Object value) throws InvalidValueException {				
				if (!isValid(value)){
					throw new InvalidValueException(propertyManager.getExceptionDtl(propertyName));
				}
			}

			public boolean isValid(Object value) {
				boolean isValid = false;  			
				String expression = "^[-+]?[0-9]*\\.?[0-9]+$";  
				CharSequence inputStr = value.toString();  
				Pattern pattern = Pattern.compile(expression);  
				Matcher matcher = pattern.matcher(inputStr);  
				if(matcher.matches()){  
					isValid = true;  
				}  
				return isValid;  
			}
		}
		return new MyValidator();
	}

	//validate social number
	protected final Validator getSocialNumberExistValidator(final DaoIntrfc dao, final String errorMessage){
		class MyValidator implements Validator {
			private static final long serialVersionUID = 4583840990919281331L;
			@Override
			public void validate(Object value) throws InvalidValueException {				
				if (!isValid(value)){
					throw new InvalidValueException(propertyManager.getExceptionDtl(errorMessage));
				}
			}
			public boolean isValid(Object value) { 			
				if(dao.findById("Persons", value.toString()) == null)
					return true;
				else
					return false;  
			}
		}
		return new MyValidator();
	}
	
	//validate only letters as an input
	protected final Validator getOnlyLettersValidator(final String propertyName){
		class MyValidator implements Validator {

			private static final long serialVersionUID = 4922238330568656105L;

			@Override
			public void validate(Object value) throws InvalidValueException {				
				if (!isValid(value)){
					throw new InvalidValueException(propertyManager.getExceptionDtl(propertyName));
				}
			}

			public boolean isValid(Object value) { 			
				boolean isValid = false;  
				CharSequence inputStr = value.toString();	
				String expression = "[a-zA-Z]+";  
				Pattern pattern = Pattern.compile(expression);  
				Matcher matcher = pattern.matcher(inputStr);  		 
				/*
				String cyrillicExpression = "\\p{IsCyrillic}+";  
				Pattern cyrillicPattern = Pattern.compile(cyrillicExpression);  
				Matcher cyrillicMatcher = cyrillicPattern.matcher(inputStr);
				cyrillicMatcher.matches() 
				*/
				if(matcher.matches() || inputStr.equals("")){  
					isValid = true;  
				}  
				return isValid;  
			}
		}
		return new MyValidator();
	}

	protected final Validator getRequiredValidator(final String propertyName){
		class MyValidator implements Validator {
			private static final long serialVersionUID = -8281962473854901819L;
			@Override
			public void validate(Object value) throws InvalidValueException {				
				if (!isValid(value)){
					throw new InvalidValueException(propertyManager.getExceptionDtl(propertyName));
				}
			}

			public boolean isValid(Object value) {
				boolean isValid = false;  			 
				if(value != null){  
					isValid = true;  
				}  
				return isValid;  
			}
		}
		return new MyValidator();
	}

	protected final boolean validate(CustomComponent customComponent, final String propertyName) {
		boolean result = true;
		Iterator<Component> customComponentIterator = customComponent.iterator();
		while(customComponentIterator.hasNext()){
			String label = propertyManager.getExceptionDtl(propertyName);
			Component component = customComponentIterator.next();
			if (component instanceof AbstractField) {
				try {
					((AbstractField<?>) component).validate();
				} catch (Exception e) {
					if (e instanceof EmptyValueException){
						((AbstractComponent)component).setComponentError(new UserError(label));
					}
					result = false;
				}
			} else if (component instanceof AbstractComponentContainer) {
				if (!validate((AbstractComponentContainer) component, propertyName)) {
					result = false;
				}
			}
		}
		return result;
	}

	protected final boolean validate(AbstractComponentContainer abstractComponentContainer, final String propertyName) {
		boolean result = true;
		Iterator<Component> containerIterator = abstractComponentContainer.iterator();
		while(containerIterator.hasNext()){
			String label = propertyManager.getExceptionDtl(propertyName);
			Component component = containerIterator.next();
			if (component instanceof AbstractField) {
				try {
					((AbstractField<?>) component).validate();
				} catch (Exception e) {
					if (e instanceof EmptyValueException){
						((AbstractComponent)component).setComponentError(new UserError(label));
					}
					result = false;
				}
			} else if (component instanceof AbstractComponentContainer) {
				if (!validate((AbstractComponentContainer) component, propertyName)) {
					result = false;
				}
			}
		}
		return result;
	}

}
