package web.forms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import database.dao.DaoIntrfc;
import database.pojo.Enumerations;
import database.pojo.Organizations;
import database.pojo.Persons;
import database.pojo.Users;

import web.StepIntrfc;
import web.beans.ComboxBean;
import web.classes.ComponentValidator;
import web.classes.PropertyManager;
import web.views.AbstractView;

public class UploadDocumentsForm extends Form implements StepIntrfc {

	private static final long serialVersionUID = 1L;

	public UploadDocumentsForm(AbstractView view, String label) {
		super(view, label, new FormLayout());
		setCompositionRoot(getLayout());

	}

	public Layout buildLayout(String mode) {	

		//get main web.components.table.generated.layout
		Layout formLayout = (FormLayout)getLayout();
		//get component validator
		ComponentValidator componentValidator = getComponentValidator();
		//get propertyManager
		PropertyManager propertyManager = getPropertyManager();
		//get access to DB
		DaoIntrfc dao = getDao();

		//get object that will be bind to the web.components.table.generated.components

		//remove all current web.components.table.generated.components
		formLayout.removeAllComponents();


		return formLayout;

	}

	@Override
	public boolean process(HashMap<String, Form> steps) {
		//get access to DB
		DaoIntrfc dao = getDao();

		return true;
	}

}
