package web.forms;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import web.StepIntrfc;
import web.classes.ComponentValidator;
import web.classes.PropertyManager;
import web.views.AbstractView;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;

import database.dao.DaoIntrfc;
import database.pojo.Enumerations;
import database.pojo.Persons;

public class PersonForm extends Form implements StepIntrfc{

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

	public PersonForm(AbstractView view, String label) {

		super(view, label, new FormLayout());
		setCompositionRoot(getLayout());

	}

	public Layout viewLayout(String mode){
		return buildLayout(mode);
	}

	public Layout buildLayout(String mode) {	

		//get main web.components.table.generated.layout
		//FormLayout formLayout = (FormLayout)getLayout();
		FormLayout formLayout = new FormLayout();
		//get component validator
		ComponentValidator componentValidator = getComponentValidator();
		//get propertyManager
		PropertyManager propertyManager = getPropertyManager();
		//get access to DB
		DaoIntrfc dao = getDao();	

		//get object that will be bind to the web.components.table.generated.components
		final Persons person;
		if(getData() != null){
			person = (Persons)getData();
		}else{
			//set initial values
			person = new Persons();
			person.setSocialNumber("");
			person.setFirstName("");
			person.setMiddleName("");
			person.setLastName("");
			setData(person);
		}

		//remove all current web.components.table.generated.components
		//formLayout.removeAllComponents();

		//define measurements of the web.components.table.generated.components 
		String width = "180px", height = "-1px";

		// personTitleCB
		personTitleCB = new ComboBox(propertyManager.getLabelDtl("personTitle"));
		personTitleCB.setImmediate(true);
		personTitleCB.setRequired(true);
		personTitleCB.setWidth(width);
		personTitleCB.setHeight(height);
		formLayout.addComponent(personTitleCB);

		// personFirstNameTF
		personFirstNameTF = new TextField(propertyManager.getLabelDtl("personFirstName"));
		personFirstNameTF.setImmediate(true);
		personFirstNameTF.setRequired(true);
		personFirstNameTF.setWidth(width);
		personFirstNameTF.setHeight(height);
		personFirstNameTF.addValidator(componentValidator.getOnlyLettersValidator("OnlyLettersAllowed"));
		formLayout.addComponent(personFirstNameTF);

		// persomMiddleNameTF
		persomMiddleNameTF = new TextField(propertyManager.getLabelDtl("personMiddleName"));
		persomMiddleNameTF.setImmediate(true);
		persomMiddleNameTF.setWidth(width);
		persomMiddleNameTF.setHeight(height);
		persomMiddleNameTF.addValidator(componentValidator.getOnlyLettersValidator("OnlyLettersAllowed"));
		formLayout.addComponent(persomMiddleNameTF);

		// personLastNameTF
		personLastNameTF = new TextField(propertyManager.getLabelDtl("personLastName"));
		personLastNameTF.setImmediate(true);
		personLastNameTF.setRequired(true);
		personLastNameTF.setWidth(width);
		personLastNameTF.setHeight(height);
		personLastNameTF.addValidator(componentValidator.getOnlyLettersValidator("OnlyLettersAllowed"));
		formLayout.addComponent(personLastNameTF);

		// personSocialNumberTF
		personSocialNumberTF = new TextField(propertyManager.getLabelDtl("personSocialNumber"));
		personSocialNumberTF.setImmediate(true);
		personSocialNumberTF.setRequired(true);
		personSocialNumberTF.setWidth(width);
		personSocialNumberTF.setHeight(height);
		formLayout.addComponent(personSocialNumberTF);

		// personBirthDatePDF
		personBirthDatePDF = new PopupDateField(propertyManager.getLabelDtl("personBirthDate"));
		personBirthDatePDF.setImmediate(true);
		personBirthDatePDF.setInvalidAllowed(false);
		personBirthDatePDF.setRequired(true);
		personBirthDatePDF.setWidth(width);
		personBirthDatePDF.setHeight(height);
		formLayout.addComponent(personBirthDatePDF);

		// personSexCB
		personSexCB = new ComboBox(propertyManager.getLabelDtl("personSex"));
		personSexCB.setImmediate(true);
		personSexCB.setInvalidAllowed(false);
		personSexCB.setRequired(true);
		personSexCB.setWidth(width);
		personSexCB.setHeight(height);
		formLayout.addComponent(personSexCB);

		// personJobTitleCB
		personJobTitleCB = new ComboBox(propertyManager.getLabelDtl("personJobTitle"));
		personJobTitleCB.setImmediate(true);
		personJobTitleCB.setInvalidAllowed(false);
		personJobTitleCB.setRequired(true);
		personJobTitleCB.setWidth(width);
		personJobTitleCB.setHeight(height);
		formLayout.addComponent(personJobTitleCB);

		// personRoleCB
		personRoleCB = new ComboBox(propertyManager.getLabelDtl("personRole"));
		personRoleCB.setImmediate(true);
		personRoleCB.setRequired(true);
		personRoleCB.setWidth(width);
		personRoleCB.setHeight(height);
		formLayout.addComponent(personRoleCB);

		//get enumerations 
		final Map<Enumerations, String> personTitleEnum = dao.getEnumeration("person title");
		final Map<Enumerations, String> personSexEnum = dao.getEnumeration("person sex");
		final Map<Enumerations, String> personRoleEnum = dao.getEnumeration("person role");
		final Map<Enumerations, String> personJobTitleEnum = dao.getEnumeration("job title");

		//add values in combo boxes
		personTitleCB.addItems(personTitleEnum.values().toArray());	
		personSexCB.addItems(personSexEnum.values().toArray());		
		personRoleCB.addItems(personRoleEnum.values().toArray());	
		personJobTitleCB.addItems(personJobTitleEnum.values().toArray());

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
							person.setEnumerationsBySex(enumeration);
							personSexCB.setData(event.getProperty().getValue().toString());
							personSexCB.setComponentError(null);
						}
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
							person.setEnumerationsByJobTitle(enumeration);
							personJobTitleCB.setData(event.getProperty().getValue().toString());
							personJobTitleCB.setComponentError(null);
						}
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
							person.setEnumerationsByRole(enumeration);
							personRoleCB.setData(event.getProperty().getValue().toString());
							personRoleCB.setComponentError(null);
						}
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

		//if mode is equal to "update" display selected value
		personTitleCB.select(personTitleEnum.get(person.getEnumerationsByTitle()));
		personSexCB.select(personSexEnum.get(person.getEnumerationsBySex()));
		personRoleCB.select(personRoleEnum.get(person.getEnumerationsByRole()));
		personJobTitleCB.select(personJobTitleEnum.get(person.getEnumerationsByJobTitle()));

		//retrieve and bind data to fields
		if(getData() != null){
			personSocialNumberTF.setValue(person.getSocialNumber());
			personFirstNameTF.setValue(person.getFirstName());
			persomMiddleNameTF.setValue(person.getMiddleName());
			personLastNameTF.setValue(person.getLastName());
			personBirthDatePDF.setValue(person.getBirthDate());
			personSexCB.setValue(person.getEnumerationsBySex());
			personJobTitleCB.setValue(person.getEnumerationsByJobTitle());
			personRoleCB.setValue(person.getEnumerationsByRole());
			personTitleCB.setValue(person.getEnumerationsByTitle());
		}

		if (mode.equals("update")){
			personSocialNumberTF.setData(person.getSocialNumber());
			personSocialNumberTF.setReadOnly(true);
		}else{
			personSocialNumberTF.addValidator(componentValidator.getOnlyDigitsValidator("IncorrectSocialNumber"));
			personSocialNumberTF.addValidator(componentValidator.getSocialNumberExistValidator(dao, "SocialNumberExist"));
		}

		setLayout(formLayout);

		if(!mode.equals("validation"))
			setCompositionRoot(formLayout);
		else
			//this.setEnabled(false);
			this.setReadOnly(true);

		return formLayout;
	}


	@Override
	public boolean process(HashMap<String, Form> steps) {
		//get access to DB
		DaoIntrfc dao = getDao();

		Persons person = (Persons) getData();
		dao.evict(person);

		steps.get("stepCreatePerson").setData(person);

		return true;
	}

}
