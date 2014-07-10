package web;

import java.util.Date;
import java.util.Map;

import pojo_classes.Enumerations;
import pojo_classes.Persons;
import property_pckg.ManageProperty;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;

import dao_classes.DaoImpl;
import dao_classes.DaoIntrfc;

public class PersonForm extends CustomComponent{

	@AutoGenerated
	private FormLayout formLayout;
	@AutoGenerated
	private TextField personSocialNumberTF;
	@AutoGenerated
	private Button personCancelBttn;
	@AutoGenerated
	private Button personCreateBttn;
	@AutoGenerated
	private ComboBox personTitleCB;
	@AutoGenerated
	private ComboBox personRoleCB;
	@AutoGenerated
	private ComboBox personSexCB;
	@AutoGenerated
	private ComboBox personJobTitleCB;
	@AutoGenerated
	private PopupDateField personBirthDatePDF;
	@AutoGenerated
	private TextField personLastNameTF;
	@AutoGenerated
	private TextField persomMiddleNameTF;
	@AutoGenerated
	private TextField personFirstNameTF;

	private Persons person;

	/**
	 * 
	 */
	private static final long serialVersionUID = 8985294135487658005L;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public PersonForm(VaadinRequest request) {

		String language = request.getLocale().getLanguage();

		buildFormLayout(language);
		setCompositionRoot(formLayout);

		person = new Persons();	
		final DaoIntrfc dao = new DaoImpl();

		//get enumerations 
		final Map<Enumerations, String> personTitleEnum = dao.getEnumeration("person title", language);
		final Map<Enumerations, String> personSexEnum = dao.getEnumeration("person sex", language);
		final Map<Enumerations, String> personRoleEnum = dao.getEnumeration("person role", language);
		final Map<Enumerations, String> personJobTitleEnum = dao.getEnumeration("job title", language);

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
					}
				});

		personFirstNameTF.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						person.setFirstName(event.getProperty().getValue().toString());
					}
				});

		persomMiddleNameTF.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						person.setMiddleName(event.getProperty().getValue().toString());
					}
				});

		personLastNameTF.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						person.setLastName(event.getProperty().getValue().toString());
					}
				});

		personBirthDatePDF.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						person.setBirthDate((Date)event.getProperty().getValue());
					}
				});

		personSexCB.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						String value = event.getProperty().getValue().toString();
						Enumerations enumeration = new Enumerations();
						for (Map.Entry<Enumerations, String> entry : personSexEnum.entrySet()) {		
							if (value.equals(entry.getValue())) {
								enumeration = (Enumerations)entry.getKey();
							}		
						}
						person.setEnumerationsBySex(enumeration);
					}
				});

		personJobTitleCB.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						String value = event.getProperty().getValue().toString();
						Enumerations enumeration = new Enumerations();
						for (Map.Entry<Enumerations, String> entry : personJobTitleEnum.entrySet()) {		
							if (value.equals(entry.getValue())) {
								enumeration = (Enumerations)entry.getKey();
							}		
						}
						person.setEnumerationsByJobTitle(enumeration);
					}
				});

		personRoleCB.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						String value = event.getProperty().getValue().toString();
						Enumerations enumeration = new Enumerations();
						for (Map.Entry<Enumerations, String> entry : personRoleEnum.entrySet()) {		
							if (value.equals(entry.getValue())) {
								enumeration = (Enumerations)entry.getKey();
							}		
						}
						person.setEnumerationsByRole(enumeration);
					}
				});

		personTitleCB.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						String value = event.getProperty().getValue().toString();
						Enumerations enumeration = new Enumerations();
						for (Map.Entry<Enumerations, String> entry : personTitleEnum.entrySet()) {		
							if (value.equals(entry.getValue())) {
								enumeration = (Enumerations)entry.getKey();
							}		
						}
						person.setEnumerationsByTitle(enumeration);
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

	public Persons getPerson() {
		return person;
	}

	public void setPerson(Persons person) {
		this.person = person;
	}

	@AutoGenerated
	private FormLayout buildFormLayout(String language) {

		CustomValidator customValidator = new CustomValidator(language);	

		// common part: create formLayout
		formLayout = new FormLayout();
		formLayout.setImmediate(true);
		formLayout.setSizeUndefined();

		//formLayout.addStyleName("flexwrap");

		/*
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");

		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		 */

		// personCreateLb
		/*
		personCreateLb = new Label(ManageProperty.getLabelDtl("personCreate" + "_" + language));
		personCreateLb.setImmediate(true);
		formLayout.addComponent(personCreateLb);
		 */

		// personSocialNumberTF
		personSocialNumberTF = new TextField(ManageProperty.getLabelDtl("personSocialNumber" + "_" + language));
		personSocialNumberTF.setImmediate(true);
		personSocialNumberTF.setRequired(true);
		personSocialNumberTF.setWidth("180px");
		personSocialNumberTF.setHeight("-1px");
		formLayout.addComponent(personSocialNumberTF);
		String socialNumberException = "IncorrectSocialNumber" + "_" + language;
		personSocialNumberTF.addValidator(customValidator.getOnlyDigitsValidator(socialNumberException));

		// personTitleCB
		personTitleCB = new ComboBox(ManageProperty.getLabelDtl("personTitle" + "_" + language));
		personTitleCB.setImmediate(true);
		personTitleCB.setRequired(true);
		personTitleCB.setWidth("180px");
		personTitleCB.setHeight("-1px");
		formLayout.addComponent(personTitleCB);

		// personFirstNameTF
		personFirstNameTF = new TextField(ManageProperty.getLabelDtl("personFirstName" + "_" + language));
		personFirstNameTF.setImmediate(true);
		personFirstNameTF.setInvalidAllowed(false);
		personFirstNameTF.setRequired(true);
		personFirstNameTF.setWidth("180px");
		personFirstNameTF.setHeight("-1px");
		formLayout.addComponent(personFirstNameTF);

		// persomMiddleNameTF
		persomMiddleNameTF = new TextField(ManageProperty.getLabelDtl("personMiddleName" + "_" + language));
		persomMiddleNameTF.setImmediate(true);
		persomMiddleNameTF.setInvalidAllowed(false);
		persomMiddleNameTF.setWidth("180px");
		persomMiddleNameTF.setHeight("-1px");
		formLayout.addComponent(persomMiddleNameTF);

		// personLastNameTF
		personLastNameTF = new TextField(ManageProperty.getLabelDtl("personLastName" + "_" + language));
		personLastNameTF.setImmediate(true);
		personLastNameTF.setInvalidAllowed(false);
		personLastNameTF.setRequired(true);
		personLastNameTF.setWidth("180px");
		personLastNameTF.setHeight("-1px");
		formLayout.addComponent(personLastNameTF);

		// personBirthDatePDF
		personBirthDatePDF = new PopupDateField(ManageProperty.getLabelDtl("personBirthDate" + "_" + language));
		personBirthDatePDF.setImmediate(true);
		personBirthDatePDF.setInvalidAllowed(false);
		personBirthDatePDF.setRequired(true);
		personBirthDatePDF.setWidth("180px");
		personBirthDatePDF.setHeight("-1px");
		formLayout.addComponent(personBirthDatePDF);

		// personSexCB
		personSexCB = new ComboBox(ManageProperty.getLabelDtl("personSex" + "_" + language));
		personSexCB.setImmediate(true);
		personSexCB.setInvalidAllowed(false);
		personSexCB.setRequired(true);
		personSexCB.setWidth("180px");
		personSexCB.setHeight("-1px");
		formLayout.addComponent(personSexCB);

		// personJobTitleCB
		personJobTitleCB = new ComboBox(ManageProperty.getLabelDtl("personJobTitle" + "_" + language));
		personJobTitleCB.setImmediate(true);
		personJobTitleCB.setInvalidAllowed(false);
		personJobTitleCB.setRequired(true);
		personJobTitleCB.setWidth("180px");
		personJobTitleCB.setHeight("-1px");
		formLayout.addComponent(personJobTitleCB);

		// personRoleCB
		personRoleCB = new ComboBox(ManageProperty.getLabelDtl("personRole" + "_" + language));
		personRoleCB.setImmediate(true);
		personRoleCB.setRequired(true);
		personRoleCB.setWidth("180px");
		personRoleCB.setHeight("-1px");
		formLayout.addComponent(personRoleCB);


		/*
		// personCreateBttn
		personCreateBttn = new Button(ManageProperty.getButtonDtl("personCreate" + "_" + language));
		personCreateBttn.setImmediate(true);
		formLayout.addComponent(personCreateBttn);


		// personCancelBttn
		personCancelBttn = new Button(ManageProperty.getButtonDtl("personCancel" + "_" + language));
		personCancelBttn.setImmediate(true);
		formLayout.addComponent(personCancelBttn);
		 */

		return formLayout;
	}
}
