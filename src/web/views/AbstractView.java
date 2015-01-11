package web.views;

import property_pckg.PropertyManager;
import web.CustomComponentIntrfc;
import web.MedinsUI;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;

import dao_classes.DaoIntrfc;

public abstract class AbstractView extends CustomComponent implements CustomComponentIntrfc, View{

	private static final long serialVersionUID = 1L;
	
	//this is the layout of the view
	private Layout layout;
	//label of the view
	private String label;
	//this is the UI to which the view belongs
	private UI ui;


	public AbstractView(){}

	public AbstractView(UI ui){
		setUi(ui);
		setSizeFull();
	}
	
	public AbstractView(String label, Layout layout, 
			PropertyManager propertyManager, DaoIntrfc dao, Navigator navigator){
		setLabel(label);
		setLayout(layout);
		setSizeFull();
	}

	public AbstractView(AbstractView abstractView){
		setLabel(abstractView.getLabel());
		setLayout(abstractView.getLayout());
		setUi(abstractView.getUi());
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
	public void setLabel(String label) {
		this.label = label;
	}
	
	public UI getUi() {
		return ui;
	}

	public void setUi(UI ui) {
		this.ui = ui;
	}
	
	public PropertyManager getPropertyManager() {
		return ((MedinsUI)getUi()).getPropertyManager();
	}

	public DaoIntrfc getDao() {
		return ((MedinsUI)getUi()).getDao();
	}

	public Navigator getNavigator() {
		return ((MedinsUI)getUi()).getNavigator();
	}
	
	public Layout buildLayout(String mode){
		return buildLayout();
	};
	
	public abstract Layout buildLayout();

}

