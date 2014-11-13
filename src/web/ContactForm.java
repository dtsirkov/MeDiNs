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

		super(request, label);

		final Contacts contact = new Contacts();	
		Object[] objectArray = {contact};
		this.setObjectArray(objectArray);
		this.setLayout(new FormLayout());	
		this.setComponentValidator(new ComponentValidator(this.getLanguage()));

		buildFormLayout();
		setCompositionRoot(this.getLayout());

		//get access to DB
		DaoIntrfc dao = this.getDao();

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


		String codeValue = "yes";
		String value = "";
		for (Map.Entry<Enumerations, String> entry : contactActiveEnum.entrySet()) {		
			if (codeValue.equals(entry.getKey().getCode())) {
				value = (String)entry.getValue();
				contact.setEnumerationsByActive(entry.getKey());
			}		
		}
		activeCB.select(value);

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
					}
				});

		zipCodeTF.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						contact.setZip(event.getProperty().getValue().toString());
					}
				});

		cityTF.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						contact.setCity(event.getProperty().getValue().toString());
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
					}
				});

		phoneTF.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						contact.setPhone(event.getProperty().getValue().toString());
					}
				});

		mobileTF.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						contact.setMobile(event.getProperty().getValue().toString());
					}
				});

		emailTF.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						contact.setEmail(event.getProperty().getValue().toString());
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
					}
				});

		/*
		activeCB.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						String value = event.getProperty().getValue().toString();
						Enumerations enumeration = new Enumerations();
						for (Map.Entry<Enumerations, String> entry : contactActiveEnum.entrySet()) {		
							if (value.equals(entry.getValue())) {
								enumeration = (Enumerations)entry.getKey();
							}		
						}
						contact.setEnumerationsByActive(enumeration);
					}
				});
		 */

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
					}
				});

		/*
		personCreateBttn.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;
			public void buttonClick(ClickEvent event) {

				dao.persist(person);

				personCreateBttn.setCaption("Created!");
			}
		});
		 */


	}

	private Layout buildFormLayout() {

		FormLayout formLayout = (FormLayout)this.getLayout();
		ComponentValidator componentValidator = this.getComponentValidator();
		String language = this.getLanguage();

		// addressTF
		addressTF = new TextField(ManageProperty.getLabelDtl("address" + "_" + language));
		addressTF.setImmediate(true);
		addressTF.setRequired(true);
		addressTF.setWidth("180px");
		addressTF.setHeight("-1px");
		formLayout.addComponent(addressTF);

		// zipCodeTF
		zipCodeTF = new TextField(ManageProperty.getLabelDtl("zip" + "_" + language));
		zipCodeTF.setImmediate(true);
		zipCodeTF.setInvalidAllowed(false);
		zipCodeTF.setRequired(true);
		zipCodeTF.setWidth("180px");
		zipCodeTF.setHeight("-1px");
		formLayout.addComponent(zipCodeTF);

		// cityTF
		cityTF = new TextField(ManageProperty.getLabelDtl("city" + "_" + language));
		cityTF.setImmediate(true);
		cityTF.setInvalidAllowed(false);
		cityTF.setRequired(true);
		cityTF.setWidth("180px");
		cityTF.setHeight("-1px");
		formLayout.addComponent(cityTF);		

		// countryCB
		countryCB = new ComboBox(ManageProperty.getLabelDtl("country" + "_" + language));
		countryCB.setImmediate(true);
		countryCB.setRequired(true);
		countryCB.setWidth("180px");
		countryCB.setHeight("-1px");
		formLayout.addComponent(countryCB);

		// phoneTF
		phoneTF = new TextField(ManageProperty.getLabelDtl("phone" + "_" + language));
		phoneTF.setImmediate(true);
		phoneTF.setInvalidAllowed(false);
		phoneTF.setRequired(true);
		phoneTF.setWidth("180px");
		phoneTF.setHeight("-1px");
		formLayout.addComponent(phoneTF);


		// mobileTF
		mobileTF = new TextField(ManageProperty.getLabelDtl("mobile" + "_" + language));
		mobileTF.setImmediate(true);
		mobileTF.setInvalidAllowed(false);
		mobileTF.setWidth("180px");
		mobileTF.setHeight("-1px");
		formLayout.addComponent(mobileTF);

		// emailTF
		emailTF = new TextField(ManageProperty.getLabelDtl("email" + "_" + language));
		emailTF.setImmediate(true);
		emailTF.setInvalidAllowed(false);
		emailTF.setRequired(true);
		emailTF.setWidth("180px");
		emailTF.setHeight("-1px");
		formLayout.addComponent(emailTF);

		// typeCB
		typeCB = new ComboBox(ManageProperty.getLabelDtl("contactType" + "_" + language));
		typeCB.setImmediate(true);
		typeCB.setInvalidAllowed(false);
		typeCB.setRequired(true);
		typeCB.setWidth("180px");
		typeCB.setHeight("-1px");
		formLayout.addComponent(typeCB);

		// activeCB
		activeCB = new ComboBox(ManageProperty.getLabelDtl("activeContact" + "_" + language));
		activeCB.setImmediate(true);
		activeCB.setInvalidAllowed(false);
		activeCB.setWidth("180px");
		activeCB.setHeight("-1px");
		activeCB.setEnabled(false);
		formLayout.addComponent(activeCB);

		// preferedCB
		preferedCB = new ComboBox(ManageProperty.getLabelDtl("preferedContact" + "_" + language));
		preferedCB.setImmediate(true);
		preferedCB.setRequired(true);
		preferedCB.setWidth("180px");
		preferedCB.setHeight("-1px");
		formLayout.addComponent(preferedCB);

		return formLayout;
	}
}
