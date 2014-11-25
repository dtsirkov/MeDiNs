package web;

import java.util.Map;

import pojo_classes.Contacts;
import pojo_classes.Enumerations;
import property_pckg.ManageProperty;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.TextField;

import dao_classes.DaoIntrfc;

public class ContactForm extends Form{

	/**
	 * 
	 */
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

	public ContactForm(VaadinRequest request, String label) {

		super(request, label, new FormLayout());
		Object[] objectArray = {new Contacts()};
		setObjectArray(objectArray);
		
		buildFormLayout();
		setCompositionRoot(getLayout());

	}

	private Layout buildFormLayout() {

		//gem main layout
		FormLayout formLayout = (FormLayout)getLayout();
		//get component validater
		ComponentValidator componentValidator = getComponentValidator();
		//get language
		String language = getLanguage();
		//get access to DB
		DaoIntrfc dao = getDao();	
		//get object that will be bind to the components
		final Contacts contact = (Contacts)getObjectArray()[0];
		//define measurements of the components 
		String width = "180px", height = "-1px";

		// addressTF
		addressTF = new TextField(ManageProperty.getLabelDtl("address" + "_" + language));
		addressTF.setImmediate(true);
		addressTF.setRequired(true);
		addressTF.setWidth(width);
		addressTF.setHeight(height);
		formLayout.addComponent(addressTF);

		// zipCodeTF
		zipCodeTF = new TextField(ManageProperty.getLabelDtl("zip" + "_" + language));
		zipCodeTF.setImmediate(true);
		zipCodeTF.setInvalidAllowed(false);
		zipCodeTF.setRequired(true);
		zipCodeTF.setWidth(width);
		zipCodeTF.setHeight(height);
		formLayout.addComponent(zipCodeTF);

		// cityTF
		cityTF = new TextField(ManageProperty.getLabelDtl("city" + "_" + language));
		cityTF.setImmediate(true);
		cityTF.setInvalidAllowed(false);
		cityTF.setRequired(true);
		cityTF.setWidth(width);
		cityTF.setHeight(height);
		formLayout.addComponent(cityTF);		

		// countryCB
		countryCB = new ComboBox(ManageProperty.getLabelDtl("country" + "_" + language));
		countryCB.setImmediate(true);
		countryCB.setRequired(true);
		countryCB.setWidth(width);
		countryCB.setHeight(height);
		formLayout.addComponent(countryCB);

		// phoneTF
		phoneTF = new TextField(ManageProperty.getLabelDtl("phone" + "_" + language));
		phoneTF.setImmediate(true);
		phoneTF.setInvalidAllowed(false);
		phoneTF.setRequired(true);
		phoneTF.setWidth(width);
		phoneTF.setHeight(height);
		formLayout.addComponent(phoneTF);


		// mobileTF
		mobileTF = new TextField(ManageProperty.getLabelDtl("mobile" + "_" + language));
		mobileTF.setImmediate(true);
		mobileTF.setInvalidAllowed(false);
		mobileTF.setWidth(width);
		mobileTF.setHeight(height);
		formLayout.addComponent(mobileTF);

		// emailTF
		emailTF = new TextField(ManageProperty.getLabelDtl("email" + "_" + language));
		emailTF.setImmediate(true);
		emailTF.setInvalidAllowed(false);
		emailTF.setRequired(true);
		emailTF.setWidth(width);
		emailTF.setHeight(height);
		formLayout.addComponent(emailTF);

		// typeCB
		typeCB = new ComboBox(ManageProperty.getLabelDtl("contactType" + "_" + language));
		typeCB.setImmediate(true);
		typeCB.setInvalidAllowed(false);
		typeCB.setRequired(true);
		typeCB.setWidth(width);
		typeCB.setHeight(height);
		formLayout.addComponent(typeCB);

		// preferedCB
		preferedCB = new ComboBox(ManageProperty.getLabelDtl("preferedContact" + "_" + language));
		preferedCB.setImmediate(true);
		preferedCB.setRequired(true);
		preferedCB.setWidth(width);
		preferedCB.setHeight(height);
		formLayout.addComponent(preferedCB);
		
		// activeCB
		activeCB = new ComboBox(ManageProperty.getLabelDtl("activeContact" + "_" + language));
		activeCB.setImmediate(true);
		activeCB.setInvalidAllowed(false);
		activeCB.setWidth(width);
		activeCB.setHeight(height);
		activeCB.setEnabled(false);
		formLayout.addComponent(activeCB);
		
		//get enumerations 
		final Map<Enumerations, String> countryEnum = dao.getEnumeration("country", this.getLanguage());
		final Map<Enumerations, String> contactTypeEnum = dao.getEnumeration("contact type", this.getLanguage());
		final Map<Enumerations, String> contactActiveEnum = dao.getEnumeration("yes no", this.getLanguage());
		final Map<Enumerations, String> contactPreferedEnum = dao.getEnumeration("yes no", this.getLanguage());
		
		//add values in combo boxes
		countryCB.addItems(countryEnum.values().toArray());		
		typeCB.addItems(contactTypeEnum.values().toArray());		
		activeCB.addItems(contactActiveEnum.values().toArray());	
		preferedCB.addItems(contactPreferedEnum.values().toArray());
		
		//set initial values
		contact.setAddress("");
		contact.setCity("");
		contact.setCountry("");
		contact.setZip("");
		contact.setEmail("");
		contact.setPhone("");
		contact.setMobile("");
		
		//bind data
		addressTF.setValue(contact.getAddress());
		zipCodeTF.setValue(contact.getZip());
		cityTF.setValue(contact.getCity());
		countryCB.setValue(contact.getCountry());
		phoneTF.setValue(contact.getPhone());
		mobileTF.setValue(contact.getMobile());
		emailTF.setValue(contact.getEmail());
		typeCB.setValue(contact.getEnumerationsByType());
		preferedCB.setValue(contact.getEnumerationsByPrefered());
		activeCB.setValue(contact.getEnumerationsByActive());

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
						String value = event.getProperty().getValue().toString();
						String enumeration = "";
						for (Map.Entry<Enumerations, String> entry : countryEnum.entrySet()) {		
							if (value.equals(entry.getValue())) {
								enumeration = entry.getValue();
							}		
						}
						contact.setCountry(enumeration);
						countryCB.setData(event.getProperty().getValue().toString());
						countryCB.setComponentError(null);
					}
				});

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
		
		String codeValue = "yes";
		String value = "";
		for (Map.Entry<Enumerations, String> entry : contactActiveEnum.entrySet()) {		
			if (codeValue.equals(entry.getKey().getCode())) {
				value = (String)entry.getValue();
				contact.setEnumerationsByActive(entry.getKey());
				activeCB.setData(codeValue);
				activeCB.setComponentError(null);
			}		
		}
		activeCB.select(value);

		return formLayout;
	}
}
