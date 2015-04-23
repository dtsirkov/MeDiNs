package web.forms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pojo.classes.Enumerations;
import pojo.classes.Organizations;
import pojo.classes.Persons;
import pojo.classes.Users;
import beans.ComboxBean;

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

import dao.classes.DaoIntrfc;

import web.StepIntrfc;
import web.classes.ComponentValidator;
import web.classes.PropertyManager;
import web.views.AbstractView;

public class UserForm extends Form implements StepIntrfc {

	private static final long serialVersionUID = 1L;

	public UserForm(AbstractView view, String label) {
		super(view, label, new FormLayout());
		setCompositionRoot(getLayout());

	}

	public Layout buildLayout(String mode) {	

		//get main layout
		Layout formLayout = (FormLayout)getLayout();
		//get component validator
		ComponentValidator componentValidator = getComponentValidator();
		//get propertyManager
		PropertyManager propertyManager = getPropertyManager();
		//get access to DB
		DaoIntrfc dao = getDao();

		//get object that will be bind to the components
		final Users user;
		if(mode.equals("update") || getData() != null){
			user = (Users)getData();
		}else{
			//set initial values
			user=new Users();
			user.setUsername("");
			user.setPassword("");
			user.setActive("yes");
			user.setUserRole("user");
			setData(user);
		}

		//remove all current components
		formLayout.removeAllComponents();

		// textFieldUserName
		final TextField textFieldUserName = new TextField(propertyManager.getLabelDtl("username")+": ");
		textFieldUserName.setImmediate(true);
		textFieldUserName.setWidth("200px");
		textFieldUserName.setHeight("-1px");
		textFieldUserName.setRequired(true);
		formLayout.addComponent(textFieldUserName);

		// textFieldUserName
		final TextField textFieldUserPassword = new TextField(propertyManager.getLabelDtl("password")+": ");
		textFieldUserPassword.setImmediate(true);
		textFieldUserPassword.setWidth("200px");
		textFieldUserPassword.setHeight("-1px");
		textFieldUserPassword.setRequired(true);
		formLayout.addComponent(textFieldUserPassword);		

		// user role CB
		final ComboBox userRoleCB = new ComboBox(propertyManager.getLabelDtl("role")+": ");
		userRoleCB.setImmediate(true);
		userRoleCB.setWidth("100px");
		userRoleCB.setHeight("-1px");
		userRoleCB.setRequired(true);
		formLayout.addComponent(userRoleCB);

		// activeCB
		final ComboBox activeCB = new ComboBox(propertyManager.getLabelDtl("active")+": ");
		activeCB.setImmediate(true);
		activeCB.setWidth("100px");
		activeCB.setHeight("-1px");
		activeCB.setRequired(true);
		formLayout.addComponent(activeCB);

		//get enumerations 
		final Map<Enumerations, String> userRoleEnum = dao.getEnumeration("user role");
		final Map<Enumerations, String> activeEnum = dao.getEnumeration("yes no");

		//add values in combo boxes	
		// Have a bean container to put the beans in
		final BeanItemContainer<ComboxBean> containerUserRole = new BeanItemContainer<ComboxBean>(ComboxBean.class);
		containerUserRole.addAll(ComboxBean.getComboxBeanList(userRoleEnum));
		userRoleCB.setContainerDataSource(containerUserRole);
		userRoleCB.setItemCaptionPropertyId("label");

		final BeanItemContainer<ComboxBean> containerUserActive = new BeanItemContainer<ComboxBean>(ComboxBean.class);
		containerUserActive.addAll(ComboxBean.getComboxBeanList(activeEnum));
		activeCB.setContainerDataSource(containerUserActive);
		activeCB.setItemCaptionPropertyId("label");	

		//add listeners
		textFieldUserName.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						user.setUsername(event.getProperty().getValue().toString());
						textFieldUserName.setData(event.getProperty().getValue().toString());
						textFieldUserName.setComponentError(null);
					}
				});

		textFieldUserPassword.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						user.setPassword(event.getProperty().getValue().toString());
						textFieldUserPassword.setData(event.getProperty().getValue().toString());
						textFieldUserPassword.setComponentError(null);
					}
				});

		userRoleCB.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						ComboxBean value = (ComboxBean) event.getProperty().getValue();
						if (value != null){
							user.setUserRole(value.getValue());
							userRoleCB.setData(value);
							userRoleCB.setComponentError(null);
						}
					}
				});

		activeCB.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						ComboxBean value = (ComboxBean) event.getProperty().getValue();
						if (value != null){
							user.setActive(value.getValue());
							activeCB.setData(value);
							activeCB.setComponentError(null);
						}
					}
				});

		//retrieve and bind data to fields
		if(getData() != null){
			textFieldUserName.setValue(user.getUsername());
			textFieldUserPassword.setValue(user.getPassword());
			for (ComboxBean bean : containerUserRole.getItemIds()) {		
				if (user.getUserRole().equals(bean.getValue())) {
					userRoleCB.setValue(bean);
				}		
			}
			for (ComboxBean bean : containerUserActive.getItemIds()) {		
				if (user.getActive().equals(bean.getValue())) {
					activeCB.setValue(bean);
				}		
			}
		}

		return formLayout;

	}

	@Override
	public boolean process(HashMap<String, Form> steps) {
		//get access to DB
		DaoIntrfc dao = getDao();

		Users user = (Users) getData();

		Persons person=(Persons) steps.get("stepCreatePerson").getData();
		user.setPersons(person);

		dao.evict(user);

		steps.get("stepCreateUser").setData(user);

		return true;
	}

}
