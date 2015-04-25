package web.forms;

import java.util.HashMap;
import java.util.Map;


import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Upload;

import database.dao.DaoIntrfc;
import database.pojo.Contacts;
import database.pojo.Enumerations;
import database.pojo.Organizations;
import database.pojo.TouristVisit;
import web.StepIntrfc;
import web.classes.ComponentValidator;
import web.classes.PropertyManager;
import web.views.AbstractView;

public class OrganizationForm extends Form implements StepIntrfc {

	private static final long serialVersionUID = 1L;

	public OrganizationForm(AbstractView view, String label) {
		super(view, label, new FormLayout());
		setCompositionRoot(getLayout());

	}

	public Layout buildLayout(String mode) {

		//get main web.components.table.generated.layout
		FormLayout formLayout = (FormLayout)getLayout();
		//get component validator
		ComponentValidator componentValidator = getComponentValidator();
		//get propertyManager
		PropertyManager propertyManager = getPropertyManager();
		//get access to DB
		DaoIntrfc dao = getDao();	

		//get object that will be bind to the web.components.table.generated.components
		final Organizations organization;
		if(mode.equals("update") || getData() != null){
			organization = (Organizations) getData();
		}else{
			//set initial values
			organization = new Organizations();
			organization.setName("");
			organization.setBankDetails("");
			organization.setEnumerations(null);
			organization.setLogo("");
			organization.setIban("");
			setData(organization);
		}

		//remove all current web.components.table.generated.components
		formLayout.removeAllComponents();

		// textFieldName
		final TextField textFieldName = new TextField();
		textFieldName.setCaption(propertyManager.getLabelDtl("name"));
		textFieldName.setImmediate(false);
		textFieldName.setWidth("207px");
		textFieldName.setHeight("-1px");
		textFieldName.setRequired(true);
		formLayout.addComponent(textFieldName);

		// comboBoxType
		final ComboBox comboBoxOrgType = new ComboBox();
		comboBoxOrgType.setCaption(propertyManager.getLabelDtl("type"));
		comboBoxOrgType.setImmediate(false);
		comboBoxOrgType.setWidth("-1px");
		comboBoxOrgType.setHeight("-1px");
		comboBoxOrgType.setRequired(true);
		formLayout.addComponent(comboBoxOrgType);

		// textFieldIBAN
		final TextField textFieldIBAN = new TextField();
		textFieldIBAN.setCaption(propertyManager.getLabelDtl("iban"));
		textFieldIBAN.setImmediate(false);
		textFieldIBAN.setWidth("337px");
		textFieldIBAN.setHeight("-1px");
		formLayout.addComponent(textFieldIBAN);

		// textFieldBankDetails
		final TextField textFieldBankDetails = new TextField();
		textFieldBankDetails.setCaption(propertyManager.getLabelDtl("bank details"));
		textFieldBankDetails.setImmediate(false);
		textFieldBankDetails.setWidth("337px");
		textFieldBankDetails.setHeight("-1px");
		formLayout.addComponent(textFieldBankDetails);

		// uploadLogo
		Upload uploadLogo = new Upload();
		uploadLogo.setCaption(propertyManager.getLabelDtl("logo"));
		uploadLogo.setImmediate(false);
		uploadLogo.setReadOnly(true);
		uploadLogo.setWidth("-1px");
		uploadLogo.setHeight("-1px");
		formLayout.addComponent(uploadLogo);

		//get enumerations 
		final Map<Enumerations, String> orgTypeEnum = dao.getEnumeration("organization");

		//add values in combo boxes
		comboBoxOrgType.addItems(orgTypeEnum.values().toArray());

		//add listeners
		textFieldName.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						organization.setName(event.getProperty().getValue().toString());
						textFieldName.setData(event.getProperty().getValue().toString());
						textFieldName.setComponentError(null);
					}
				});

		comboBoxOrgType.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						Object value = event.getProperty().getValue();
						if (value !=null) {
							String string = value.toString();
							Enumerations enumeration = new Enumerations();
							for (Map.Entry<Enumerations, String> entry : orgTypeEnum.entrySet()) {		
								if (string.equals(entry.getValue())) {
									enumeration = (Enumerations)entry.getKey();
								}		
							}
							organization.setEnumerations(enumeration);
							comboBoxOrgType.setData(string);
							comboBoxOrgType.setComponentError(null);
						}
					}
				});


		textFieldIBAN.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						organization.setIban(event.getProperty().getValue().toString());
						textFieldIBAN.setData(event.getProperty().getValue().toString());
						textFieldIBAN.setComponentError(null);
					}
				});

		textFieldBankDetails.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						organization.setBankDetails(event.getProperty().getValue().toString());
						textFieldBankDetails.setData(event.getProperty().getValue().toString());
						textFieldBankDetails.setComponentError(null);
					}
				});

		//retrieve and bind data to fields
		if(mode.equals("update") || getData() != null){
			textFieldName.setValue(organization.getName());
			comboBoxOrgType.select(orgTypeEnum.get(organization.getEnumerations()));
			textFieldIBAN.setValue(organization.getIban());
			textFieldBankDetails.setValue(organization.getBankDetails());
		}

		return formLayout;
	}

	@Override
	public boolean process(HashMap<String, Form> steps) {
		//get access to DB
		DaoIntrfc dao = getDao();

		Organizations organization = (Organizations) getData();
		dao.evict(organization);

		steps.get("stepOrganization").setData(organization);

		return true;
	}

}
