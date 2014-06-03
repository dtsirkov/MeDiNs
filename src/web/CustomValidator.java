package web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import property_pckg.ManageProperty;

import com.vaadin.data.Validator;

class CustomValidator implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2270732896484883778L;
	private String language;

	public CustomValidator(String language){
		this.setLanguage(language);
	}

	//vslidate social number
	public final Validator getOnlyDigitsValidator(final String propertyName){
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

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}
