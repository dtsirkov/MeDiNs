package web.components.table.generated.components;

import com.vaadin.ui.TextField;

public class MyNumberField extends TextField {

	private static final long serialVersionUID = -7390942553316386175L;

	public MyNumberField(String caption, boolean required, String requiredMsg, int length, String validationRegex, String validationMsg, String format) {
		super(caption);
		setNullRepresentation("");

		// TODO Add the formatting here
		
		
		// FIXME These two regexes does not work
		
//		int minDigit = 0;
//		if (required) {
//			minDigit = 1;
//		}
//		int maxDigit = length + (length / 3);
//		String lengthRegex = "^\\S{" + minDigit + "," + maxDigit + "}$";
//		this.addValidator(new RegexpValidator(lengthRegex, requiredMsg));
		
//		this.addValidator(new RegexpValidator(validationRegex, validationMsg));
		
	}


}
