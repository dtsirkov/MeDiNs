package web.forms;

import java.util.Iterator;

import property_pckg.PropertyManager;
import web.CustomComponentIntrfc;
import web.classes.ComponentValidator;
import web.views.AbstractView;


import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Layout;

import dao_classes.DaoIntrfc;

public class Form extends CustomComponent implements CustomComponentIntrfc{

	private static final long serialVersionUID = 1L;
	//layout of the form
	private Layout layout;
	//label of the form
	private String label;
	//custom validator used to validate form components
	private ComponentValidator componentValidator;
	//the form is always not validated
	private boolean validated = false;
	//view of the form
	private AbstractView view;

	public Form(){}

	public Form(Form form){
		setLayout(form.getLayout());
		setLabel(form.getLabel());
		setComponentValidator(form.getComponentValidator());
		setValidated(form.isValidated());
		setView(form.getView());
	}
	
	public Form(String label){
		setLabel(label);
	}

	public Form(AbstractView view, String label){
		setLabel(label);
		setView(view);
	}
	
	public Form(Layout layout, ComponentValidator componentValidator){
		setLayout(layout);
		setComponentValidator(componentValidator);
	}

	public Form(AbstractView view, String label, Layout layout){
		setLayout(layout);
		setLabel(label);
		setView(view);
		setComponentValidator(new ComponentValidator(getPropertyManager()));
	}

	public Layout getLayout() {
		return layout;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
	}
	
	public String getLabel() {
		return label;
	}
	
	public String getLabelDtl() {
		return getPropertyManager().getLabelDtl(label);
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public ComponentValidator getComponentValidator() {
		return componentValidator;
	}

	public void setComponentValidator(ComponentValidator componentValidator) {
		this.componentValidator = componentValidator;
	}

	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}

	public AbstractView getView() {
		return view;
	}

	public void setView(AbstractView view) {
		this.view = view;
	}
	
	public DaoIntrfc getDao() {
		return view.getDao();
	}

	public PropertyManager getPropertyManager() {
		return view.getPropertyManager();
	}

	public Navigator getNavigator() {
		return view.getNavigator();
	}
	
	public Layout buildLayout(String mode){
		return layout;
	};

	public void setReadOnly(boolean readOnly) {
		Iterator<Component> layoutIterator = this.getLayout().iterator();
		while(layoutIterator.hasNext()){
			layoutIterator.next().setReadOnly(readOnly);
		}
	}

}
