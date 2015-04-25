package web.forms;

import java.util.HashMap;

import web.StepIntrfc;
import web.views.AbstractView;


import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Layout;

public class NewValidationForm extends Form implements StepIntrfc{

	private static final long serialVersionUID = 1L;

	public NewValidationForm(){}

	public NewValidationForm(AbstractView view, String label){

		super(view, label, new VerticalLayout());

	}

	public NewValidationForm(Form validationForm) {

		super(validationForm);
		setLayout(new VerticalLayout());
		setCompositionRoot(getLayout());

	}

	public Layout buildLayout(String mode) {

		getLayout().setSizeUndefined();
		Form[] requiredSteps = (Form[])getData();
		for(int i = 0; i < requiredSteps.length - 1; i++){
			if(! (requiredSteps[i] instanceof SearchForm))
				addValidationComponent(requiredSteps[i]);
		}	
		return getLayout();

	}

	private void addValidationComponent(Form form){

		Label label = new Label(form.getLabelDtl());
		label.setStyleName("validationCompenentTitle");
		label.setSizeUndefined();
		Panel panel = new Panel(label);
		if(form.getData() != null)
			panel.setContent(form.buildLayout("view"));
		panel.setStyleName("validation_panel");
		panel.setSizeFull();
		Layout mainLayout = this.getLayout();
		mainLayout.addComponent(panel);

	}

	@Override
	public boolean process(HashMap<String, Form> steps) {
		// TODO Auto-generated method stub
		return true;
	}

}
