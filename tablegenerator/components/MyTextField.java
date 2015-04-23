package components;

import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.ui.TextField;

public class MyTextField extends TextField {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5786602410609267935L;
	
	public MyTextField(String caption, boolean required, String requiredMsg, int length, String validationRegex, String validationMsg) {
		super(caption);
		setNullRepresentation("");
		
		int min = 0;
		if (required) {
			min = 1;
		}
		String lengthRegex = "((?s).){" + min + "," + length + "}";
		this.addValidator(new RegexpValidator(lengthRegex, requiredMsg));
		
		this.addValidator(new RegexpValidator(validationRegex, validationMsg));
	}

}
