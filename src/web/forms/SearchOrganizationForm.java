package web.forms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Hibernate;

import pojo.classes.Contacts;
import pojo.classes.Enumerations;
import pojo.classes.Organizations;
import pojo.classes.Persons;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;


import beans.ComboxBean;
import dao.classes.DaoIntrfc;

import web.StepIntrfc;
import web.classes.PropertyManager;
import web.components.PagedTable;
import web.views.AbstractView;

public class SearchOrganizationForm extends SearchForm<Organizations> implements StepIntrfc{

	private static final long serialVersionUID = 1L;

	public SearchOrganizationForm(AbstractView view, String label){
		super(view, label);
		setdBTableName("Organizations");
	}

	public Layout buildLayout(String mode){
		Layout root=super.buildLayout(mode);
		getSearchByIdntfrLayout().setVisible(false);
		getSearchByObjectLayout().setVisible(false);
		return root;
	};

	public FormLayout buildSearchByCriteriaLayout(){
		//get access to DB
		DaoIntrfc dao = getDao();

		//get propertyManager
		PropertyManager propertyManager = getPropertyManager();

		FormLayout formLayout = new FormLayout();

		// textFieldName
		final TextField textFieldName = new TextField();
		textFieldName.setCaption(propertyManager.getLabelDtl("name"));
		textFieldName.setImmediate(false);
		textFieldName.setWidth("207px");
		textFieldName.setHeight("-1px");
		textFieldName.setRequired(false);
		formLayout.addComponent(textFieldName);
		addToSearchConstraint(textFieldName, "name");


		// comboBoxType
		final ComboBox comboBoxOrgType = new ComboBox();
		comboBoxOrgType.setCaption(propertyManager.getLabelDtl("type"));
		comboBoxOrgType.setImmediate(false);
		comboBoxOrgType.setWidth("-1px");
		comboBoxOrgType.setHeight("-1px");
		comboBoxOrgType.setRequired(false);
		formLayout.addComponent(comboBoxOrgType);

		//get enumerations 
		final Map<Enumerations, String> orgTypeEnum = dao.getEnumeration("organization");

		//add values in combo box
		// Have a bean container to put the beans in
		final BeanItemContainer<ComboxBean> container = new BeanItemContainer<ComboxBean>(ComboxBean.class);
		container.addAll(ComboxBean.getComboxBeanList(orgTypeEnum));
		comboBoxOrgType.setContainerDataSource(container);
		comboBoxOrgType.setItemCaptionPropertyId("label");
		addToSearchConstraint(comboBoxOrgType, "type");

		getSearchByKeyLayout().setVisible(false);

		setSearchByCriteriaLayout(formLayout);
		setId("number");

		return formLayout;
	}

	public IndexedContainer createContainer() {
		IndexedContainer container = new IndexedContainer();
		container.addContainerProperty("name", String.class, null);
		container.addContainerProperty("type", String.class, null);
		ArrayList<Organizations> searchResult = getSearchResult();
		for (int i = 0; i < searchResult.size(); i++) {
			Item item = container.addItem(searchResult.get(i));
			item.getItemProperty("name").setValue(searchResult.get(i).getName());
			item.getItemProperty("type").setValue(searchResult.get(i).getEnumerations().getCode());
		}
		container.sort(new Object[] { "name" }, new boolean[] { true });
		return container;
	}

	public String[] getTableHeader(){

		PropertyManager propertyManager = getPropertyManager();		
		String name = propertyManager.getLabelDtl("name");
		String type = propertyManager.getLabelDtl("type");

		return new String[] { name, type};

	}

	@Override
	public boolean process(HashMap<String, Form> steps) {

		boolean processed = false;

		//get access to DB
		DaoIntrfc dao = getDao();

		Organizations selectedOrganization = (Organizations) getSelectedItem();

		steps.get("stepOrganization").setData(selectedOrganization);
		
		//load objects
		Set<Contacts> contactses=selectedOrganization.getContactses();
		contactses.size();

		Enumerations enumeration;
		for(Contacts contact : contactses){
			dao.evict(contact);
			enumeration = contact.getEnumerationsByActive();
			if(enumeration.getCode().equals("yes")){
				dao.evict(contact);
				steps.get("stepCreateContact").setData(contact);
			}
		}
		
		//evict objects
		dao.evict(selectedOrganization);
		
		processed = true;
		return processed;
	}

	@Override
	protected ArrayList<Organizations> search() {
		// TODO Auto-generated method stub
		return null;
	}

}
