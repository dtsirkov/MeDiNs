package web.forms;

import java.util.HashMap;

import web.StepIntrfc;
import web.views.AbstractView;


import com.vaadin.ui.Alignment;
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
			if(! (requiredSteps[i] instanceof SearchForm) && ! (requiredSteps[i] instanceof SearchCaseForm))
				addValidationComponent(requiredSteps[i]);
		}	
		return getLayout();

	}

	private void addValidationComponent(Form form){


		if(form.getData() != null){

			Panel panel = new Panel(form.getLabelDtl());
			panel.setStyleName("validation_panel");
			panel.setSizeFull();
			panel.setContent(form.viewLayout("validation"));

			VerticalLayout mainLayout = (VerticalLayout)getLayout();

			VerticalLayout verticalLayout = new VerticalLayout();
			verticalLayout.setHeight("30px");
			mainLayout.addComponent(verticalLayout);

			mainLayout.addComponent(panel);
			mainLayout.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);

		}

		/*
		if(form.getData() != null){

			GridLayout grid = new GridLayout(1, 2);

			grid.setSizeUndefined();
			grid.setStyleName("validation_grid");

			Label label = new Label(form.getLabelDtl());
			label.setStyleName("validationCompenentTitle");
			label.setSizeUndefined();
			grid.addComponent(label);
			grid.setComponentAlignment(label, Alignment.MIDDLE_LEFT);

			Layout viewLayout = form.viewLayout("validation");
			viewLayout.setSizeUndefined();
			grid.addComponent(viewLayout);
			grid.setComponentAlignment(viewLayout, Alignment.MIDDLE_CENTER);

			this.getLayout().addComponent(grid);

		}
		 */

	}

	@Override
	public boolean process(HashMap<String, Form> steps) {
		// TODO Auto-generated method stub
		return true;
	}

}
