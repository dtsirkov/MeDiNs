package web;

import java.util.Iterator;

import property_pckg.PropertyManager;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Layout;

import dao_classes.DaoIntrfc;

public class Form extends CustomComponent implements CustomComponentIntrfc{

	private static final long serialVersionUID = 1L;
	//this is the layout of the form
	private Layout layout;
	//this is custom validator used to validate form components
	private ComponentValidator componentValidator;
	//the form is always not validated
	private boolean validated = false;
	//get access to DB
	private DaoIntrfc dao;
	//label of the form
	private String label;
	//object that manages contents of Form labels
	private PropertyManager propertyManager;
	

	public Form(){}
	
	public Form(Form form){
		setLayout(form.getLayout());
		setComponentValidator(form.getComponentValidator());
		setValidated(form.isValidated());
		setDao(form.getDao());
		setLabel(form.getLabel());
		setPropertyManager(form.getPropertyManager());
		setData(form.getData());
	}
	
	public Form(VaadinRequest request, String label){
		setDao((DaoIntrfc)request.getAttribute("dao"));
		setPropertyManager((PropertyManager)request.getAttribute("propertyManager"));
		setLabel(propertyManager.getLabelDtl(label));
	}
	
	public Form(VaadinRequest request, String label, Layout layout){
		setDao((DaoIntrfc)request.getAttribute("dao"));
		setPropertyManager((PropertyManager)request.getAttribute("propertyManager"));
		setLabel(propertyManager.getLabelDtl(label));
		setLayout(layout);
		setComponentValidator(new ComponentValidator(getPropertyManager()));
	}
	
	public Form(Layout layout, ComponentValidator componentValidator){
		setLayout(layout);
		setComponentValidator(componentValidator);
	}
	
	public Form(DaoIntrfc dao, PropertyManager propertyManager, String label){
		setDao(dao);
		setPropertyManager(propertyManager);
		setLabel(label);
	}
	
	public Layout buildFormLayout(String mode){
		return layout;
	};

	public Layout getLayout() {
		return layout;
	}

	public void setLayout(Layout layout) {
		this.layout = layout;
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

	public DaoIntrfc getDao() {
		return dao;
	}

	public void setDao(DaoIntrfc dao) {
		this.dao = dao;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public PropertyManager getPropertyManager() {
		return propertyManager;
	}

	public void setPropertyManager(PropertyManager propertyManager) {
		this.propertyManager = propertyManager;
	}

	public void setReadOnly(boolean readOnly) {
		Iterator<Component> layoutIterator = this.getLayout().iterator();
		while(layoutIterator.hasNext()){
			layoutIterator.next().setReadOnly(readOnly);
		}
	}

}
