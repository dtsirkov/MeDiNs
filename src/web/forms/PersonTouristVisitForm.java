package web.forms;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import pojo.classes.Enumerations;
import pojo.classes.Organizations;
import pojo.classes.Persons;
import pojo.classes.TouristVisit;



import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.AbstractSelect;
import com.vaadin.ui.AbstractSelect.NewItemHandler;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
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

public class PersonTouristVisitForm extends Form implements StepIntrfc {

	private static final long serialVersionUID = 1L;

	public PersonTouristVisitForm(AbstractView view, String label) {
		super(view, label, new FormLayout());
		setCompositionRoot(getLayout());

	}

	public Layout buildLayout(String mode) {	

		//get main layout
		FormLayout formLayout = (FormLayout) getLayout();
		//get component validator
		ComponentValidator componentValidator = getComponentValidator();
		//get propertyManager
		final PropertyManager propertyManager = getPropertyManager();
		//get access to DB
		final DaoIntrfc dao = getDao();

		//get object that will be bind to the components
		final TouristVisit touristVisit;
		if(mode.equals("update") || getData() != null){
			touristVisit = (TouristVisit)getData();
		}else{
			//set initial values
			touristVisit = new TouristVisit();
			touristVisit.setFrom(null);
			touristVisit.setTo(null);
			touristVisit.setHotel("");
			touristVisit.setPersons(new Persons());
			touristVisit.setResort("");
			touristVisit.setRoom("");	
			touristVisit.setPhoneNumber("");
			setData(touristVisit);
		}

		//remove all current components
		formLayout.removeAllComponents();

		HorizontalLayout hlVacation=new HorizontalLayout();
		hlVacation.setCaption(propertyManager.getLabelDtl("Vacation period")+": ");
		hlVacation.setSpacing(true);

		// popupDateFieldStart
		final PopupDateField popupDateFieldStart = new PopupDateField();
		popupDateFieldStart.setImmediate(true);
		popupDateFieldStart.setWidth("110px");
		popupDateFieldStart.setHeight("23px");
		popupDateFieldStart.setRequired(true);
		hlVacation.addComponent(popupDateFieldStart);

		// label_5
		Label label_5 = new Label();
		label_5.setImmediate(false);
		label_5.setWidth("-1px");
		label_5.setHeight("-1px");
		label_5.setValue(" - ");
		hlVacation.addComponent(label_5);

		// popupDateFieldEnd
		final PopupDateField popupDateFieldEnd = new PopupDateField();
		popupDateFieldEnd.setImmediate(true);
		popupDateFieldEnd.setWidth("110px");
		popupDateFieldEnd.setHeight("-1px");
		popupDateFieldEnd.setRequired(true);
		hlVacation.addComponent(popupDateFieldEnd);

		formLayout.addComponent(hlVacation);

		HorizontalLayout hlResort=new HorizontalLayout();
		hlResort.setCaption(propertyManager.getLabelDtl("Resort")+": ");
		hlResort.setSpacing(true);

		// comboBoxResort
		final ComboBox comboBoxResort = new ComboBox();
		comboBoxResort.setImmediate(true);
		comboBoxResort.setWidth("280px");
		comboBoxResort.setHeight("-1px");
		comboBoxResort.setRequired(true);
		comboBoxResort.setNewItemsAllowed(true);
		comboBoxResort.setNullSelectionAllowed(true);
		comboBoxResort.setNullSelectionItemId("");
		hlResort.addComponent(comboBoxResort);

		formLayout.addComponent(hlResort);

		HorizontalLayout hlHotel=new HorizontalLayout();
		hlHotel.setCaption(propertyManager.getLabelDtl("Hotel")+": ");
		hlHotel.setSpacing(true);

		// comboBoxHotel
		final ComboBox comboBoxHotel = new ComboBox();
		comboBoxHotel.setImmediate(true);
		comboBoxHotel.setWidth("280px");
		comboBoxHotel.setHeight("-1px");
		comboBoxHotel.setNewItemsAllowed(true);
		comboBoxHotel.setNullSelectionAllowed(true);
		comboBoxHotel.setNullSelectionItemId("");
		hlHotel.addComponent(comboBoxHotel);

		formLayout.addComponent(hlHotel);

		// textFieldRoomNumber
		HorizontalLayout hlRoom = new HorizontalLayout();
		hlRoom.setCaption(propertyManager.getLabelDtl("Room number")+": ");

		final TextField textFieldRoomNumber = new TextField();
		textFieldRoomNumber.setImmediate(false);
		textFieldRoomNumber.setWidth("90px");
		textFieldRoomNumber.setHeight("-1px");
		hlRoom.addComponent(textFieldRoomNumber);
		formLayout.addComponent(hlRoom);

		// textFieldContactNumber
		HorizontalLayout hlContactNumber = new HorizontalLayout();
		hlContactNumber.setCaption(propertyManager.getLabelDtl("Phone number")+": ");

		final TextField textFieldContactNumber = new TextField();
		textFieldContactNumber.setImmediate(false);
		textFieldContactNumber.setWidth("150px");
		textFieldContactNumber.setHeight("-1px");
		hlContactNumber.addComponent(textFieldContactNumber);
		formLayout.addComponent(hlContactNumber);

		//Get combobox data
		List<String> hotels = getOrganizationNameList(dao,"hotel");
		if (!hotels.isEmpty())
		{
			comboBoxHotel.addItems(hotels.toArray());
		}

		// Custom handling for new items
		comboBoxHotel.setNewItemHandler(new NewItemHandler() {
			private static final long serialVersionUID = 1L;

			@Override
			public void addNewItem(String newItemCaption) {
				if (!newItemCaption.equals(""))
				{
					comboBoxHotel.addItem(newItemCaption);
					// Remember to set the selection to the new item
					comboBoxHotel.select(newItemCaption);
					Notification.show(propertyManager.getLabelDtl("Added new Hotel")+" " +newItemCaption);
				}
			}
		});
		comboBoxResort.setNewItemHandler(new NewItemHandler() {
			private static final long serialVersionUID = 1L;

			@Override
			public void addNewItem(String newItemCaption) {
				if (!newItemCaption.equals(""))
				{
					comboBoxResort.addItem(newItemCaption);
					// Remember to set the selection to the new item
					comboBoxResort.select(newItemCaption);
					Notification.show(propertyManager.getLabelDtl("Added new Resort")+" " +newItemCaption);
				}
			}
		});

		//add listeners
		popupDateFieldStart.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						touristVisit.setFrom((Date)event.getProperty().getValue());
						popupDateFieldStart.setData((Date)event.getProperty().getValue());
						popupDateFieldStart.setComponentError(null);
						//setTouristVisit(touristVisit);
					}
				});

		popupDateFieldEnd.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						touristVisit.setTo((Date)event.getProperty().getValue());
						popupDateFieldEnd.setData((Date)event.getProperty().getValue());
						popupDateFieldEnd.setComponentError(null);
						//setTouristVisit(touristVisit);
					}
				});

		comboBoxResort.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						Object value=comboBoxResort.getValue();
						if (value != null){
							touristVisit.setResort(event.getProperty().getValue().toString());
							comboBoxResort.setData(event.getProperty().getValue().toString());
							comboBoxResort.setComponentError(null);
							//setTouristVisit(touristVisit);
						}
					}
				});

		comboBoxHotel.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {	
						Object value=comboBoxHotel.getValue();								
						if (value != null){
							touristVisit.setHotel(event.getProperty().getValue().toString());
							comboBoxHotel.setData(event.getProperty().getValue().toString());
							comboBoxHotel.setComponentError(null);
							//setTouristVisit(touristVisit);
						}
					}
				});

		textFieldRoomNumber.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						touristVisit.setRoom(event.getProperty().getValue().toString());
						textFieldRoomNumber.setData(event.getProperty().getValue().toString());
						textFieldRoomNumber.setComponentError(null);
						//setTouristVisit(touristVisit);
					}
				});

		textFieldContactNumber.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						touristVisit.setPhoneNumber(event.getProperty().getValue().toString());
						textFieldContactNumber.setData(event.getProperty().getValue().toString());
						textFieldContactNumber.setComponentError(null);
						//setTouristVisit(touristVisit);
					}
				});

		//retrieve and bind data to fields
		if(mode.equals("update") || getData() != null){
			popupDateFieldStart.setValue(touristVisit.getFrom());
			popupDateFieldEnd.setValue(touristVisit.getTo());

			comboBoxResort.addItem(touristVisit.getResort());
			comboBoxResort.select(touristVisit.getResort());

			comboBoxHotel.addItem(touristVisit.getHotel());
			comboBoxHotel.select(touristVisit.getHotel());

			textFieldRoomNumber.setValue(touristVisit.getRoom());
			textFieldContactNumber.setValue(touristVisit.getPhoneNumber());
		}

		return formLayout;
	}

	@Override
	public boolean process(HashMap<String, Form> steps) {
		//get access to DB
		DaoIntrfc dao = getDao();

		TouristVisit touristVisit = (TouristVisit) getData();
		dao.evict(touristVisit);

		//Persons person=(Persons) steps.get("stepCreatePerson").getData();
		//touristVisit.setPersons(person);
		steps.get("stepTouristVisit").setData(touristVisit);

		return true;
	}

	public List<String> getOrganizationNameList(DaoIntrfc dao,String typeOrganization){
		Map<Enumerations, String> typeOrg = dao.getEnumeration("organization");
		Enumerations orgEnum=new Enumerations();

		for (Map.Entry<Enumerations, String> entry : typeOrg.entrySet()) {
			Enumerations enumeration = entry.getKey();			
			String label = entry.getValue();
			if (label.equalsIgnoreCase(typeOrganization)){
				orgEnum=enumeration;
				break;
			}
		} 

		Organizations hotels=new Organizations();
		hotels.setEnumerations(orgEnum);

		List<Object> orgs=dao.findByExample(hotels);

		List<String> hotelList=new ArrayList<String>();

		for (Object organization:orgs){
			Organizations org=(Organizations) organization;
			hotelList.add(org.getName());
			//System.out.println(org.getName());
		}		

		return hotelList;
	}

}
