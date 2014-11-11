package web;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import property_pckg.ManageProperty;

import com.vaadin.data.Validator;
import com.vaadin.data.Validator.EmptyValueException;
import com.vaadin.server.UserError;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractComponentContainer;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CustomComponent;

class CustomValidator implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2270732896484883778L;
	private String language;

	public CustomValidator(String language){
		this.setLanguage(language);
	}

	//validate social number
	protected final Validator getOnlyDigitsValidator(final String propertyName){
		class MyValidator implements Validator {
			private static final long serialVersionUID = -8281962473854901819L;
			@Override
			public void validate(Object value) throws InvalidValueException {				
				if (!isValid(value))
					throw new InvalidValueException(ManageProperty.getExceptionDtl(propertyName));
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

	protected final Validator getRequiredValidator(final String propertyName){
		class MyValidator implements Validator {
			private static final long serialVersionUID = -8281962473854901819L;
			@Override
			public void validate(Object value) throws InvalidValueException {				
				if (!isValid(value))
					throw new InvalidValueException(ManageProperty.getExceptionDtl(propertyName));
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

	protected final boolean validate(CustomComponent customComponent) {
		boolean result = true;
		Iterator<Component> customComponentIterator = customComponent.iterator();
		while(customComponentIterator.hasNext()){
			String property = "EmptyValueException" + "_" + this.getLanguage();
			String label = ManageProperty.getExceptionDtl(property);
			Component component = customComponentIterator.next();
			if (component instanceof AbstractField) {
				try {
					((AbstractField) component).validate();
				} catch (Exception e) {
					if (e instanceof EmptyValueException){
						((AbstractComponent)component).setComponentError(new UserError(label));
					}
					result = false;
				}
			} else if (component instanceof AbstractComponentContainer) {
				if (!validate((AbstractComponentContainer) component)) {
					result = false;
				}
			}
		}
		return result;
	}

	protected final boolean validate(AbstractComponentContainer abstractComponentContainer) {
		boolean result = true;
		Iterator<Component> containerIterator = abstractComponentContainer.iterator();
		while(containerIterator.hasNext()){
			String property = "EmptyValueException" + "_" + this.getLanguage();
			String label = ManageProperty.getExceptionDtl(property);
			Component component = containerIterator.next();
			if (component instanceof AbstractField) {
				try {
					((AbstractField) component).validate();
				} catch (Exception e) {
					if (e instanceof EmptyValueException){
						((AbstractComponent)component).setComponentError(new UserError(label));
					}
					result = false;
				}
			} else if (component instanceof AbstractComponentContainer) {
				if (!validate((AbstractComponentContainer) component)) {
					result = false;
				}
			}
		}
		return result;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}
