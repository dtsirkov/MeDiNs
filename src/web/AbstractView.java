package web;

import property_pckg.PropertyManager;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Layout;

import dao_classes.DaoIntrfc;

public abstract class AbstractView extends CustomComponent{

	private static final long serialVersionUID = 1L;
	
	//this is the layout of the view
	private Layout layout;
	//get access to DB
	private DaoIntrfc dao;
	//label of the view
	private String label;
	//object that manages contents of View labels
	private PropertyManager propertyManager;
	//object that manages navigation 
	private Navigator navigator;

	public AbstractView(){}

	public AbstractView(PropertyManager propertyManager, DaoIntrfc dao, Navigator navigator){
		setPropertyManager(propertyManager);
		setDao(dao);
		setNavigator(navigator);
		setSizeFull();
	}

	public AbstractView(String label, Layout layout, 
			PropertyManager propertyManager, DaoIntrfc dao, Navigator navigator){
		setLabel(label);
		setLayout(layout);
		setPropertyManager(propertyManager);
		setDao(dao);
		setNavigator(navigator);
		setSizeFull();
	}

	public AbstractView(AbstractView AbstractView){
		setLabel(AbstractView.getLabel());
		setLayout(AbstractView.getLayout());
		setPropertyManager(AbstractView.getPropertyManager());
		setDao(AbstractView.getDao());
		setNavigator(AbstractView.getNavigator());
	}

	public Layout getLayout() {
		return layout;
	}
	public void setLayout(Layout layout) {
		this.layout = layout;
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
	public Navigator getNavigator() {
		return navigator;
	}
	public void setNavigator(Navigator navigator) {
		this.navigator = navigator;
	}
	
	abstract Layout buildViewLayout();

}

