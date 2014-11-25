package web;

import java.util.Date;
import java.util.Map;

import pojo_classes.Enumerations;
import pojo_classes.Persons;
import property_pckg.ManageProperty;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;

import dao_classes.DaoIntrfc;

public class PersonForm extends Form{

	private static final long serialVersionUID = 1L;
	private TextField personSocialNumberTF;
	private ComboBox personTitleCB;
	private ComboBox personRoleCB;
	private ComboBox personSexCB;
	private ComboBox personJobTitleCB;
	private PopupDateField personBirthDatePDF;
	private TextField personLastNameTF;
	private TextField persomMiddleNameTF;
	private TextField personFirstNameTF;

	public PersonForm(VaadinRequest request, String label) {

		super(request, label, new FormLayout());
		Object[] objectArray = {new Persons()};
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
		final Persons person = (Persons)getObjectArray()[0];
		//define measurements of the components 
		String width = "180px", height = "-1px";
		
		// personTitleCB
		personTitleCB = new ComboBox(ManageProperty.getLabelDtl("personTitle" + "_" + language));
		personTitleCB.setImmediate(true);
		personTitleCB.setRequired(true);
		personTitleCB.setWidth(width);
		personTitleCB.setHeight(height);
		formLayout.addComponent(personTitleCB);
		
		// personFirstNameTF
		personFirstNameTF = new TextField(ManageProperty.getLabelDtl("personFirstName" + "_" + language));
		personFirstNameTF.setImmediate(true);
		personFirstNameTF.setInvalidAllowed(false);
		personFirstNameTF.setRequired(true);
		personFirstNameTF.setWidth(width);
		personFirstNameTF.setHeight(height);
		formLayout.addComponent(personFirstNameTF);

		// persomMiddleNameTF
		persomMiddleNameTF = new TextField(ManageProperty.getLabelDtl("personMiddleName" + "_" + language));
		persomMiddleNameTF.setImmediate(true);
		persomMiddleNameTF.setInvalidAllowed(false);
		persomMiddleNameTF.setWidth(width);
		persomMiddleNameTF.setHeight(height);
		formLayout.addComponent(persomMiddleNameTF);

		// personLastNameTF
		personLastNameTF = new TextField(ManageProperty.getLabelDtl("personLastName" + "_" + language));
		personLastNameTF.setImmediate(true);
		personLastNameTF.setInvalidAllowed(false);
		personLastNameTF.setRequired(true);
		personLastNameTF.setWidth(width);
		personLastNameTF.setHeight(height);
		formLayout.addComponent(personLastNameTF);

		// personSocialNumberTF
		personSocialNumberTF = new TextField(ManageProperty.getLabelDtl("personSocialNumber" + "_" + language));
		personSocialNumberTF.setImmediate(true);
		personSocialNumberTF.setRequired(true);
		personSocialNumberTF.setWidth(width);
		personSocialNumberTF.setHeight(height);
		formLayout.addComponent(personSocialNumberTF);
		String socialNumberException = "IncorrectSocialNumber" + "_" + language;
		personSocialNumberTF.addValidator(componentValidator.getOnlyDigitsValidator(socialNumberException));

		// personBirthDatePDF
		personBirthDatePDF = new PopupDateField(ManageProperty.getLabelDtl("personBirthDate" + "_" + language));
		personBirthDatePDF.setImmediate(true);
		personBirthDatePDF.setInvalidAllowed(false);
		personBirthDatePDF.setRequired(true);
		personBirthDatePDF.setWidth(width);
		personBirthDatePDF.setHeight(height);
		formLayout.addComponent(personBirthDatePDF);

		// personSexCB
		personSexCB = new ComboBox(ManageProperty.getLabelDtl("personSex" + "_" + language));
		personSexCB.setImmediate(true);
		personSexCB.setInvalidAllowed(false);
		personSexCB.setRequired(true);
		personSexCB.setWidth(width);
		personSexCB.setHeight(height);
		formLayout.addComponent(personSexCB);

		// personJobTitleCB
		personJobTitleCB = new ComboBox(ManageProperty.getLabelDtl("personJobTitle" + "_" + language));
		personJobTitleCB.setImmediate(true);
		personJobTitleCB.setInvalidAllowed(false);
		personJobTitleCB.setRequired(true);
		personJobTitleCB.setWidth(width);
		personJobTitleCB.setHeight(height);
		formLayout.addComponent(personJobTitleCB);

		// personRoleCB
		personRoleCB = new ComboBox(ManageProperty.getLabelDtl("personRole" + "_" + language));
		personRoleCB.setImmediate(true);
		personRoleCB.setRequired(true);
		personRoleCB.setWidth(width);
		personRoleCB.setHeight(height);
		formLayout.addComponent(personRoleCB);

		//get enumerations 
		final Map<Enumerations, String> personTitleEnum = dao.getEnumeration("person title", this.getLanguage());
		final Map<Enumerations, String> personSexEnum = dao.getEnumeration("person sex", this.getLanguage());
		final Map<Enumerations, String> personRoleEnum = dao.getEnumeration("person role", this.getLanguage());
		final Map<Enumerations, String> personJobTitleEnum = dao.getEnumeration("job title", this.getLanguage());

		//add values in combo boxes
		personTitleCB.addItems(personTitleEnum.values().toArray());		
		personSexCB.addItems(personSexEnum.values().toArray());		
		personRoleCB.addItems(personRoleEnum.values().toArray());	
		personJobTitleCB.addItems(personJobTitleEnum.values().toArray());

		//set initial values
		person.setSocialNumber("");
		person.setFirstName("");
		person.setMiddleName("");
		person.setLastName("");

		//bind data
		personSocialNumberTF.setValue(person.getSocialNumber());
		personFirstNameTF.setValue(person.getFirstName());
		persomMiddleNameTF.setValue(person.getMiddleName());
		personLastNameTF.setValue(person.getLastName());
		personBirthDatePDF.setValue(person.getBirthDate());
		personSexCB.setValue(person.getEnumerationsBySex());
		personJobTitleCB.setValue(person.getEnumerationsByJobTitle());
		personRoleCB.setValue(person.getEnumerationsByRole());
		personTitleCB.setValue(person.getEnumerationsByTitle());

		//add listeners
		personSocialNumberTF.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						person.setSocialNumber(event.getProperty().getValue().toString());
						personSocialNumberTF.setData(event.getProperty().getValue().toString());
						personSocialNumberTF.setComponentError(null);
					}
				});

		personFirstNameTF.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						person.setFirstName(event.getProperty().getValue().toString());
						personFirstNameTF.setData(event.getProperty().getValue().toString());
						personFirstNameTF.setComponentError(null);
					}
				});

		persomMiddleNameTF.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						person.setMiddleName(event.getProperty().getValue().toString());
						persomMiddleNameTF.setData(event.getProperty().getValue().toString());
						persomMiddleNameTF.setComponentError(null);
					}
				});

		personLastNameTF.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						person.setLastName(event.getProperty().getValue().toString());
						personLastNameTF.setData(event.getProperty().getValue().toString());
						personLastNameTF.setComponentError(null);
					}
				});

		personBirthDatePDF.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						person.setBirthDate((Date)event.getProperty().getValue());
						personBirthDatePDF.setData((Date)event.getProperty().getValue());
						personBirthDatePDF.setComponentError(null);
					}
				});

		personSexCB.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						Enumerations enumeration = new Enumerations();
						if(event.getProperty().getValue() != null){
							String value = event.getProperty().getValue().toString();					
							for (Map.Entry<Enumerations, String> entry : personSexEnum.entrySet()) {		
								if (value.equals(entry.getValue())) {
									enumeration = (Enumerations)entry.getKey();
								}		
							}
						}
						person.setEnumerationsBySex(enumeration);
						personSexCB.setData(event.getProperty().getValue().toString());
						personSexCB.setComponentError(null);
					}
				});

		personJobTitleCB.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						Enumerations enumeration = new Enumerations();
						if(event.getProperty().getValue() != null){
							String value = event.getProperty().getValue().toString();
							for (Map.Entry<Enumerations, String> entry : personJobTitleEnum.entrySet()) {		
								if (value.equals(entry.getValue())) {
									enumeration = (Enumerations)entry.getKey();
								}		
							}
						}
						person.setEnumerationsByJobTitle(enumeration);
						personJobTitleCB.setData(event.getProperty().getValue().toString());
						personJobTitleCB.setComponentError(null);
					}
				});

		personRoleCB.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						Enumerations enumeration = new Enumerations();
						if(event.getProperty().getValue() != null){
							String value = event.getProperty().getValue().toString();					
							for (Map.Entry<Enumerations, String> entry : personRoleEnum.entrySet()) {		
								if (value.equals(entry.getValue())) {
									enumeration = (Enumerations)entry.getKey();
								}		
							}
						}
						person.setEnumerationsByRole(enumeration);
						personRoleCB.setData(event.getProperty().getValue().toString());
						personRoleCB.setComponentError(null);
					}
				});

		personTitleCB.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						Enumerations enumeration = new Enumerations();
						if(event.getProperty().getValue() != null){
							String value = event.getProperty().getValue().toString();
							for (Map.Entry<Enumerations, String> entry : personTitleEnum.entrySet()) {		
								if (value.equals(entry.getValue())) {
									enumeration = (Enumerations)entry.getKey();
								}		
							}
							person.setEnumerationsByTitle(enumeration);
							personTitleCB.setData(event.getProperty().getValue().toString());
							personTitleCB.setComponentError(null);
						}
					}
				});

		return formLayout;
	}

}
