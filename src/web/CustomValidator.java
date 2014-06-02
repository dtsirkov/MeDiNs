package web;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vaadin.data.Validator;

class CustomValidator {

	//vslidate social number
	public static final Validator socialNumber(){
		class MyValidator implements Validator {
			private static final long serialVersionUID = -8281962473854901819L;
			@Override
			public void validate(Object value) throws InvalidValueException {
				if (!isValid(value))
					throw new InvalidValueException("Incorrect input!");
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
	
}
