package web.forms;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import pojo.classes.Organizations;
import pojo.classes.Persons;
import pojo.classes.TouristVisit;

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
import web.components.OrganizationForm;
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
			touristVisit.setFrom(new Date());
			touristVisit.setTo(new Date());
			touristVisit.setHotel("");
			touristVisit.setId(0);
			touristVisit.setPersons(new Persons());
			touristVisit.setResort("");
			touristVisit.setRoom("");	
			touristVisit.setContactNumber("");
			setData(touristVisit);
		}

		//remove all current components
		formLayout.removeAllComponents();

		HorizontalLayout hlVacation=new HorizontalLayout();
		hlVacation.setCaption(propertyManager.getLabelDtl("Vacation period")+": ");
		hlVacation.setSpacing(true);

		// popupDateFieldStart
		PopupDateField popupDateFieldStart = new PopupDateField();
		popupDateFieldStart.setImmediate(false);
		popupDateFieldStart.setWidth("110px");
		popupDateFieldStart.setHeight("23px");
		hlVacation.addComponent(popupDateFieldStart);

		// label_5
		Label label_5 = new Label();
		label_5.setImmediate(false);
		label_5.setWidth("-1px");
		label_5.setHeight("-1px");
		label_5.setValue(" - ");
		hlVacation.addComponent(label_5);

		// popupDateFieldEnd
		PopupDateField popupDateFieldEnd = new PopupDateField();
		popupDateFieldEnd.setImmediate(false);
		popupDateFieldEnd.setWidth("110px");
		popupDateFieldEnd.setHeight("-1px");
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
		comboBoxResort.setNewItemsAllowed(true);
		hlResort.addComponent(comboBoxResort);

		/*		// buttonAddResort
		Button buttonAddResort = new Button();
		buttonAddResort.setCaption("Add new");
		buttonAddResort.setImmediate(false);
		buttonAddResort.setWidth("-1px");
		buttonAddResort.setHeight("-1px");
		hlResort.addComponent(buttonAddResort);*/

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
		hlHotel.addComponent(comboBoxHotel);

		/*		// buttonAddHotel
		Button buttonAddHotel = new Button();
		buttonAddHotel.setCaption("Add new");
		buttonAddHotel.setImmediate(false);
		buttonAddHotel.setWidth("-1px");
		buttonAddHotel.setHeight("-1px");
		hlHotel.addComponent(buttonAddHotel);*/

		formLayout.addComponent(hlHotel);

		// textFieldRoomNumber
		HorizontalLayout hlRoom=new HorizontalLayout();
		hlRoom.setCaption(propertyManager.getLabelDtl("Room number")+": ");

		TextField textFieldRoomNumber = new TextField();
		textFieldRoomNumber.setImmediate(false);
		textFieldRoomNumber.setWidth("90px");
		textFieldRoomNumber.setHeight("-1px");
		hlRoom.addComponent(textFieldRoomNumber);
		formLayout.addComponent(hlRoom);

		// textFieldContactNumber
		HorizontalLayout hlContactNumber=new HorizontalLayout();
		hlContactNumber.setCaption(propertyManager.getLabelDtl("Phone number")+": ");

		TextField textFieldContactNumber = new TextField();
		textFieldContactNumber.setImmediate(false);
		textFieldContactNumber.setWidth("150px");
		textFieldContactNumber.setHeight("-1px");
		hlContactNumber.addComponent(textFieldContactNumber);
		formLayout.addComponent(hlContactNumber);
		/*
		final UI ui=getView().getUI();

		//Define sub window
		final Window subWindow = new Window("Sub-window");
		subWindow.setWidth("450px");
		subWindow.setHeight("500px");
		VerticalLayout subWLayout=new VerticalLayout();
		final OrganizationForm orgForm=new OrganizationForm();
		subWLayout.addComponent(orgForm);
		subWindow.setContent(subWLayout);		
		subWindow.center();


		//getting mode for new org form
		final String modeOrg=mode;
		//button's ClickListener for adding new organization
		buttonAddResort.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				//Define sub window
				final Window subWindow = new Window("Sub-window");
				subWindow.setWidth("450px");
				subWindow.setHeight("500px");
				VerticalLayout subWLayout=new VerticalLayout();
				OrganizationForm orgForm=new OrganizationForm();				
				orgForm.setMode(modeOrg);
				subWLayout.addComponent(orgForm);
				subWindow.setContent(subWLayout);		
				subWindow.center();
				subWindow.setCaption("Add new resort");
				ui.addWindow(subWindow);

				//Close subwindow
				Button cancel=orgForm.getButtonCancel();
				cancel.addClickListener(new ClickListener() {
					public void buttonClick(ClickEvent event) {
						subWindow.close();
					}
				});


			}
		});
		 */

		// Custom handling for new items
		comboBoxHotel.setNewItemHandler(new NewItemHandler() {
			private static final long serialVersionUID = 1L;

			@Override
			public void addNewItem(String newItemCaption) {
				comboBoxHotel.addItem(newItemCaption);
				// Remember to set the selection to the new item
				comboBoxHotel.select(newItemCaption);
				Notification.show(propertyManager.getLabelDtl("Added new Hotel")+" " +newItemCaption);
			}
		});
		comboBoxResort.setNewItemHandler(new NewItemHandler() {
			private static final long serialVersionUID = 1L;

			@Override
			public void addNewItem(String newItemCaption) {
				comboBoxResort.addItem(newItemCaption);
				// Remember to set the selection to the new item
				comboBoxResort.select(newItemCaption);
				Notification.show(propertyManager.getLabelDtl("Added new Resort")+" " +newItemCaption);
			}
		});
		return formLayout;
	}
	@Override
	public boolean process(HashMap<String, Form> steps) {

		return true;
	}

}
