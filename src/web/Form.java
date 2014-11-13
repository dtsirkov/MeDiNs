package web;

import java.util.Iterator;

import property_pckg.ManageProperty;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Layout;

import dao_classes.DaoImpl;
import dao_classes.DaoIntrfc;

public class Form extends CustomComponent implements CustomComponentIntrfc{

	private static final long serialVersionUID = 1L;
	//this is an array of objects that are behind the form
	private Object[] objectArray;
	//this is the layout of the form
	private Layout layout;
	//this is custom validator used to validate form components
	private ComponentValidator componentValidator;
	//the form is always not validated
	private boolean validated = false;
	//get access to DB
	private DaoIntrfc dao;
	//language of the form
	private String language;
	//title/step of the form
	private String label;
	

	public Form(){}
	
	public Form(VaadinRequest request, String title){
		this.setDao((DaoIntrfc)request.getAttribute("dao"));
		this.setLanguage(request.getLocale().getLanguage());	
		this.setLabel(ManageProperty.getLabelDtl(title + "_" + this.getLanguage()));
	}
	
	public Form(Object[] objectArray, Layout layout, ComponentValidator componentValidator){
		this.setObjectArray(objectArray);
		this.setLayout(layout);
		this.setComponentValidator(componentValidator);
	}
	
	public Form(Form form){
		this.setObjectArray(form.getObjectArray());
		this.setLayout(form.getLayout());
		this.setComponentValidator(form.getComponentValidator());
		this.setValidated(this.isValidated());
		this.setDao(this.getDao());
		this.setLanguage(this.getLanguage());
		this.setLabel(this.getLabel());
	}

	public Object[] getObjectArray(){
		return objectArray;
	}

	public void setObjectArray(Object[] objectArray){
		this.objectArray = new Object[objectArray.length];
		for(int i = 0; i < objectArray.length; i++){
			this.objectArray[i] = objectArray[i];
		}
	}

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

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setReadOnly(boolean readOnly) {
		Iterator<Component> layoutIterator = this.getLayout().iterator();
		while(layoutIterator.hasNext()){
			layoutIterator.next().setReadOnly(readOnly);
		}
	}

}
