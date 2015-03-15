package web.forms;

import java.util.Date;
import java.util.HashMap;

import pojo.classes.Organizations;
import pojo.classes.Persons;
import pojo.classes.Policies;
import pojo.classes.TouristVisit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
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

public class PolicyForm extends Form implements StepIntrfc {

	private static final long serialVersionUID = 1L;
	private Policies policy;

	public PolicyForm(AbstractView view, String label) {
		super(view, label, new FormLayout());
		setCompositionRoot(getLayout());

	}

	public Layout buildLayout(String mode) {	

		//get main layout
		Layout formLayout = (FormLayout)getLayout();
		//get component validator
		ComponentValidator componentValidator = getComponentValidator();
		//get propertyManager
		PropertyManager propertyManager = getPropertyManager();
		//get access to DB
		DaoIntrfc dao = getDao();

		final Policies policy;
		if(mode.equals("update") || getData() != null){
			policy = (Policies)getData();
		}else{
			//set initial values
			policy = new Policies();
			policy.setNumber("");
			Organizations organizationsByAssistanceCompany = null;
			policy.setOrganizationsByAssistanceCompany(organizationsByAssistanceCompany);
			Organizations organizationsByInsuaranceCompany = null;
			policy.setOrganizationsByInsuaranceCompany(organizationsByInsuaranceCompany);
			policy.setValidFrom(null);
			policy.setValidTo(null);
			setData(policy);
		}

		//remove all current components
		formLayout.removeAllComponents();

		HorizontalLayout hlPolicyNumber = new HorizontalLayout();
		hlPolicyNumber.setCaption(propertyManager.getLabelDtl("Policy number")+": ");
		hlPolicyNumber.setSpacing(true);

		// textFieldPn
		final TextField textFieldPn = new TextField();
		textFieldPn.setImmediate(true);
		textFieldPn.setWidth("312px");
		textFieldPn.setHeight("-1px");
		textFieldPn.setRequired(true);

		hlPolicyNumber.addComponent(textFieldPn);

		formLayout.addComponent(hlPolicyNumber);

		HorizontalLayout hlValidPeriod = new HorizontalLayout();
		hlValidPeriod.setCaption(propertyManager.getLabelDtl("Validadtion period")+": ");
		hlValidPeriod.setSpacing(true);

		// popupDateFieldFrom
		final PopupDateField popupDateFieldFrom = new PopupDateField();
		popupDateFieldFrom.setImmediate(true);
		popupDateFieldFrom.setWidth("120px");
		popupDateFieldFrom.setHeight("-1px");
		popupDateFieldFrom.setRequired(true);

		hlValidPeriod.addComponent(popupDateFieldFrom);

		// label_9
		Label label_9 = new Label();
		label_9.setImmediate(false);
		label_9.setWidth("4px");
		label_9.setHeight("-1px");
		label_9.setValue(" - ");

		hlValidPeriod.addComponent(label_9);

		// popupDateFieldTill
		final PopupDateField popupDateFieldTill = new PopupDateField();
		popupDateFieldTill.setImmediate(true);
		popupDateFieldTill.setWidth("120px");
		popupDateFieldTill.setHeight("-1px");
		popupDateFieldTill.setRequired(true);

		hlValidPeriod.addComponent(popupDateFieldTill);

		formLayout.addComponent(hlValidPeriod);

		HorizontalLayout hlInsCompany = new HorizontalLayout();
		hlInsCompany.setCaption(propertyManager.getLabelDtl("Insurance company")+": ");
		hlInsCompany.setSpacing(true);

		// cbInsCompany
		ComboBox cbInsCompany = new ComboBox();
		cbInsCompany.setImmediate(true);
		cbInsCompany.setWidth("-1px");
		cbInsCompany.setHeight("-1px");
		cbInsCompany.setRequired(true);

		Button buttonAddOrg=new Button("Add");

		hlInsCompany.addComponent(cbInsCompany);
		hlInsCompany.addComponent(buttonAddOrg);
		
		formLayout.addComponent(hlInsCompany);

		HorizontalLayout hlAssistCompany = new HorizontalLayout();
		hlAssistCompany.setCaption(propertyManager.getLabelDtl("Assistance company")+": ");
		hlAssistCompany.setSpacing(true);

		// cbAssistCompany
		ComboBox cbAssistCompany = new ComboBox();
		cbAssistCompany.setImmediate(true);
		cbAssistCompany.setWidth("-1px");
		cbAssistCompany.setHeight("-1px");
		cbAssistCompany.setRequired(true);
		hlAssistCompany.addComponent(cbAssistCompany);	

		formLayout.addComponent(hlAssistCompany);

		//add listeners

		textFieldPn.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						policy.setNumber(event.getProperty().getValue().toString());
						textFieldPn.setData(event.getProperty().getValue().toString());
						textFieldPn.setComponentError(null);
						setPolicy(policy);
					}
				});

		popupDateFieldFrom.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						policy.setValidFrom((Date)event.getProperty().getValue());
						popupDateFieldFrom.setData((Date)event.getProperty().getValue());
						popupDateFieldFrom.setComponentError(null);
						setPolicy(policy);
					}
				});

		popupDateFieldTill.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						policy.setValidTo((Date)event.getProperty().getValue());
						popupDateFieldTill.setData((Date)event.getProperty().getValue());
						popupDateFieldTill.setComponentError(null);
						setPolicy(policy);
					}
				});


		//add button click for adding new org
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
		buttonAddOrg.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				//Define sub window
				final Window subWindow = new Window("Sub-window");
				subWindow.setWidth("450px");
				subWindow.setHeight("500px");
				VerticalLayout subWLayout = new VerticalLayout();
				OrganizationForm orgForm = new OrganizationForm();				
				orgForm.setMode(modeOrg);
				subWLayout.addComponent(orgForm);
				subWindow.setContent(subWLayout);		
				subWindow.center();
				subWindow.setCaption("Add new insurance company");
				ui.addWindow(subWindow);

				//Close subwindow
				Button cancel = orgForm.getButtonCancel();
				cancel.addClickListener(new ClickListener() {
					private static final long serialVersionUID = 1L;

					public void buttonClick(ClickEvent event) {
						subWindow.close();
					}
				});


			}
		});


		//retrieve and bind data to fields
		if(getData() != null){
			textFieldPn.setValue(policy.getNumber());

			popupDateFieldFrom.setValue(policy.getValidFrom());
			popupDateFieldTill.setValue(policy.getValidTo());
		}

		return formLayout;

	}

	public Policies getPolicy() {
		return policy;
	}

	public void setPolicy(Policies policy) {
		this.policy = policy;
	}

	@Override
	public boolean process(HashMap<String, Form> steps) {
		//get access to DB
		DaoIntrfc dao = getDao();

		Policies policy = getPolicy();
		dao.evict(policy);

		steps.get("stepPolicy").setData(policy);

		return true;
	}

}
