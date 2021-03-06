package web.forms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import web.StepIntrfc;
import web.beans.ComboxBean;
import web.classes.ComponentValidator;
import web.classes.PropertyManager;
import web.views.AbstractView;



import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;

import database.dao.DaoImpl;
import database.dao.DaoIntrfc;
import database.pojo.Contacts;
import database.pojo.Country;
import database.pojo.Enumerations;
import database.pojo.Organizations;

public class ContactForm extends Form implements StepIntrfc{

	private static final long serialVersionUID = 1L;
	private TextField addressTF;
	private TextField zipCodeTF;
	private TextField cityTF;
	private ComboBox countryCB;
	private TextField phoneTF;
	private TextField mobileTF;
	private TextField emailTF;
	private ComboBox typeCB;
	private ComboBox activeCB;
	private ComboBox preferedCB;

	public ContactForm(AbstractView view, String label) {

		super(view, label, new FormLayout());
		setCompositionRoot(getLayout());

	}

	public Layout viewLayout(String mode){
		return buildLayout(mode);
	}

	public Layout buildLayout(String mode) {

		//get main web.components.table.generated.layout
		FormLayout formLayout = new FormLayout();
		//get component validator
		ComponentValidator componentValidator = getComponentValidator();
		//get propertyManager
		PropertyManager propertyManager = getPropertyManager();
		//get access to DB
		DaoImpl dao = (DaoImpl) getDao();	

		//get object that will be bind to the web.components.table.generated.components
		final Contacts contact;
		if(getData() != null) {
			contact = (Contacts)getData();
		}else{
			//set initial values
			contact = new Contacts();
			contact.setAddress("");
			contact.setCity("");
			contact.setCountry("");
			contact.setZip("");
			contact.setEmail("");
			contact.setPhone("");
			contact.setMobile("");
			setData(contact);
		}

		//remove all current web.components.table.generated.components
		formLayout.removeAllComponents();

		//define measurements of the web.components.table.generated.components 
		String width = "180px", height = "-1px";

		// addressTF
		addressTF = new TextField(propertyManager.getLabelDtl("address"));
		addressTF.setImmediate(true);
		addressTF.setRequired(true);
		addressTF.setWidth(width);
		addressTF.setHeight(height);
		formLayout.addComponent(addressTF);

		// zipCodeTF
		zipCodeTF = new TextField(propertyManager.getLabelDtl("zip"));
		zipCodeTF.setImmediate(true);
		zipCodeTF.setRequired(true);
		zipCodeTF.setWidth(width);
		zipCodeTF.setHeight(height);
		formLayout.addComponent(zipCodeTF);

		// cityTF
		cityTF = new TextField(propertyManager.getLabelDtl("city"));
		cityTF.setImmediate(true);
		cityTF.setRequired(true);
		cityTF.setWidth(width);
		cityTF.setHeight(height);
		formLayout.addComponent(cityTF);		

		// countryCB
		countryCB = new ComboBox(propertyManager.getLabelDtl("country"));
		countryCB.setImmediate(true);
		countryCB.setRequired(true);
		countryCB.setWidth(width);
		countryCB.setHeight(height);
		formLayout.addComponent(countryCB);

		// phoneTF
		phoneTF = new TextField(propertyManager.getLabelDtl("phone"));
		phoneTF.setImmediate(true);
		phoneTF.setRequired(true);
		phoneTF.setWidth(width);
		phoneTF.setHeight(height);
		formLayout.addComponent(phoneTF);


		// mobileTF
		mobileTF = new TextField(propertyManager.getLabelDtl("mobile"));
		mobileTF.setImmediate(true);
		mobileTF.setWidth(width);
		mobileTF.setHeight(height);
		formLayout.addComponent(mobileTF);

		// emailTF
		emailTF = new TextField(propertyManager.getLabelDtl("email"));
		emailTF.setImmediate(true);
		emailTF.setRequired(true);
		emailTF.setWidth(width);
		emailTF.setHeight(height);
		formLayout.addComponent(emailTF);

		// typeCB
		typeCB = new ComboBox(propertyManager.getLabelDtl("contactType"));
		typeCB.setImmediate(true);
		typeCB.setRequired(true);
		typeCB.setWidth(width);
		typeCB.setHeight(height);
		formLayout.addComponent(typeCB);

		// preferedCB
		preferedCB = new ComboBox(propertyManager.getLabelDtl("preferedContact"));
		preferedCB.setImmediate(true);
		preferedCB.setRequired(true);
		preferedCB.setWidth(width);
		preferedCB.setHeight(height);
		formLayout.addComponent(preferedCB);

		// activeCB
		activeCB = new ComboBox(propertyManager.getLabelDtl("activeContact"));
		activeCB.setImmediate(true);
		activeCB.setWidth(width);
		activeCB.setHeight(height);
		formLayout.addComponent(activeCB);

		//get enumerations 
		final Map<Enumerations, String> countryEnum = dao.getEnumeration("country");
		final Map<Enumerations, String> contactTypeEnum = dao.getEnumeration("contact type");
		final Map<Enumerations, String> contactActiveEnum = dao.getEnumeration("yes no");
		final Map<Enumerations, String> contactPreferedEnum = dao.getEnumeration("yes no");

		List<Country> countries = getCountries(dao);

		//add values in combo box
		final BeanItemContainer<Country> container = new BeanItemContainer<Country>(Country.class);
		container.addAll(countries);
		countryCB.setContainerDataSource(container);
		String language = dao.getLanguage();
		String formatedLanguage = language.substring(0, 1).toUpperCase() + language.substring(1, 2);
		countryCB.setItemCaptionPropertyId("name" + formatedLanguage);

		//add values in combo boxes
		//countryCB.addItems(countryEnum.values().toArray());		
		typeCB.addItems(contactTypeEnum.values().toArray());		
		activeCB.addItems(contactActiveEnum.values().toArray());	
		preferedCB.addItems(contactPreferedEnum.values().toArray());

		//add listeners
		addressTF.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						contact.setAddress(event.getProperty().getValue().toString());
						addressTF.setData(event.getProperty().getValue().toString());
						addressTF.setComponentError(null);
					}
				});

		zipCodeTF.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						contact.setZip(event.getProperty().getValue().toString());
						zipCodeTF.setData(event.getProperty().getValue().toString());
						zipCodeTF.setComponentError(null);
					}
				});

		cityTF.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						contact.setCity(event.getProperty().getValue().toString());
						cityTF.setData(event.getProperty().getValue().toString());
						cityTF.setComponentError(null);
					}
				});

		countryCB.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						Object selection = event.getProperty().getValue();
						if (selection != null){
							String countryCode=((Country) selection).getId();
							contact.setCountry(countryCode);
							countryCB.setData(selection);
							countryCB.setComponentError(null);
						}
					}});

		phoneTF.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						contact.setPhone(event.getProperty().getValue().toString());
						phoneTF.setData(event.getProperty().getValue().toString());
						phoneTF.setComponentError(null);
					}
				});

		mobileTF.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						contact.setMobile(event.getProperty().getValue().toString());
						mobileTF.setData(event.getProperty().getValue().toString());
						mobileTF.setComponentError(null);
					}
				});

		emailTF.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						contact.setEmail(event.getProperty().getValue().toString());
						emailTF.setData(event.getProperty().getValue().toString());
						emailTF.setComponentError(null);
					}
				});

		typeCB.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						String value = event.getProperty().getValue().toString();
						Enumerations enumeration = new Enumerations();
						for (Map.Entry<Enumerations, String> entry : contactTypeEnum.entrySet()) {		
							if (value.equals(entry.getValue())) {
								enumeration = (Enumerations)entry.getKey();
							}		
						}
						contact.setEnumerationsByType(enumeration);
						typeCB.setData(event.getProperty().getValue().toString());
						typeCB.setComponentError(null);
					}
				});


		preferedCB.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						String value = event.getProperty().getValue().toString();
						Enumerations enumeration = new Enumerations();
						for (Map.Entry<Enumerations, String> entry : contactPreferedEnum.entrySet()) {		
							if (value.equals(entry.getValue())) {
								enumeration = (Enumerations)entry.getKey();
							}		
						}
						contact.setEnumerationsByPrefered(enumeration);
						preferedCB.setData(event.getProperty().getValue().toString());
						preferedCB.setComponentError(null);
					}
				});

		if(mode.equals("update")){
			//activeCB.setEnabled(true);
			activeCB.setReadOnly(false);
			activeCB.setRequired(true);
			activeCB.addValueChangeListener(
					new Property.ValueChangeListener() {
						private static final long serialVersionUID = 1L;
						public void valueChange(ValueChangeEvent event) {
							String value = event.getProperty().getValue().toString();
							Enumerations enumeration = new Enumerations();
							for (Map.Entry<Enumerations, String> entry : contactPreferedEnum.entrySet()) {		
								if (value.equals(entry.getValue())) {
									enumeration = (Enumerations)entry.getKey();
								}		
							}
							contact.setEnumerationsByActive(enumeration);
							activeCB.setData(event.getProperty().getValue().toString());
							activeCB.setComponentError(null);
						}
					});
		}else{
			String codeValue = "yes";
			String value = "";
			for (Map.Entry<Enumerations, String> entry : contactActiveEnum.entrySet()) {		
				if (codeValue.equals(entry.getKey().getCode())) {
					value = (String)entry.getValue();
					contact.setEnumerationsByActive(entry.getKey());
					activeCB.setData(value);
					activeCB.setComponentError(null);
				}		
			}
			activeCB.select(value);
			activeCB.setReadOnly(true);
		}

		//if mode is equal to "update" display selected value
		for (Map.Entry<Enumerations, String> entry : countryEnum.entrySet()) {		
			if (contact.getCountry().equals(entry.getKey().getCode())) {
				activeCB.select((String)entry.getValue());
			}		
		}
		typeCB.select(contactTypeEnum.get(contact.getEnumerationsByType()));
		activeCB.select(contactActiveEnum.get(contact.getEnumerationsByActive()));
		preferedCB.select(contactPreferedEnum.get(contact.getEnumerationsByPrefered()));

		//retrieve and bind data to fields
		if(getData() != null){
			addressTF.setValue(contact.getAddress());
			zipCodeTF.setValue(contact.getZip());
			cityTF.setValue(contact.getCity());
			countryCB.setValue(getCountry(dao, contact.getCountry()));
			phoneTF.setValue(contact.getPhone());
			mobileTF.setValue(contact.getMobile());
			emailTF.setValue(contact.getEmail());
			typeCB.setValue(contact.getEnumerationsByType());
			preferedCB.setValue(contact.getEnumerationsByPrefered());
			activeCB.setValue(contact.getEnumerationsByActive());
		}

		setLayout(formLayout);

		if(!mode.equals("validation"))
			setCompositionRoot(formLayout);
		else
			//this.setEnabled(false);
			this.setReadOnly(true);

		return formLayout;
	}

	public static List<Country> getCountries(DaoIntrfc dao){

		List<Country> outCountries = new ArrayList<Country>(0);
		Country country = new Country();		
		List<Object> ls = dao.findByExample(country);

		for(Object c:ls){
			Country c1 = (Country) c;
			outCountries.add(c1);
		}
		return outCountries;		
	}

	public static Country getCountry(DaoIntrfc dao, String countryCode){
		return (Country) dao.findById("Country", countryCode);	
	}

	@Override
	public boolean process(HashMap<String, Form> steps) {
		//get access to DB
		DaoIntrfc dao = getDao();

		Contacts contact = (Contacts) getData();
		dao.evict(contact);

		steps.get("stepCreateContact").setData(contact);

		return true;
	}
}
