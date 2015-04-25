package web.forms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
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

import database.dao.DaoIntrfc;
import database.pojo.Enumerations;
import database.pojo.Organizations;
import database.pojo.Persons;
import database.pojo.Policies;
import database.pojo.TouristVisit;

import web.StepIntrfc;
import web.beans.ComboxBean;
import web.classes.ComponentValidator;
import web.classes.PropertyManager;
import web.views.AbstractView;

public class PolicyForm extends Form implements StepIntrfc {

	private static final long serialVersionUID = 1L;

	public PolicyForm(AbstractView view, String label) {
		super(view, label, new FormLayout());
		setCompositionRoot(getLayout());

	}

	public Layout buildLayout(String mode) {	

		//get main web.components.table.generated.layout
		Layout formLayout = (FormLayout)getLayout();
		//get component validator
		ComponentValidator componentValidator = getComponentValidator();
		//get propertyManager
		PropertyManager propertyManager = getPropertyManager();
		//get access to DB
		DaoIntrfc dao = getDao();

		//get object that will be bind to the web.components.table.generated.components
		final Policies policy;
		if(mode.equals("update") || getData() != null){
			policy = (Policies)getData();
		}else{
			//set initial values
			policy = new Policies();
			policy.setNumber("");
			policy.setOrganizationsByAssistanceCompany(null);
			policy.setOrganizationsByInsuaranceCompany(null);
			policy.setValidFrom(null);
			policy.setValidTo(null);
			setData(policy);
		}

		//remove all current web.components.table.generated.components
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
		final ComboBox cbInsCompany = new ComboBox();
		cbInsCompany.setImmediate(true);
		cbInsCompany.setWidth("-1px");
		cbInsCompany.setHeight("-1px");
		cbInsCompany.setRequired(true);

		hlInsCompany.addComponent(cbInsCompany);
		formLayout.addComponent(hlInsCompany);

		HorizontalLayout hlAssistCompany = new HorizontalLayout();
		hlAssistCompany.setCaption(propertyManager.getLabelDtl("Assistance company")+": ");
		hlAssistCompany.setSpacing(true);

		// cbAssistCompany
		final ComboBox cbAssistCompany = new ComboBox();
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
					}
				});

		popupDateFieldFrom.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						policy.setValidFrom((Date)event.getProperty().getValue());
						popupDateFieldFrom.setData((Date)event.getProperty().getValue());
						popupDateFieldFrom.setComponentError(null);
					}
				});

		popupDateFieldTill.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {
						policy.setValidTo((Date)event.getProperty().getValue());
						popupDateFieldTill.setData((Date)event.getProperty().getValue());
						popupDateFieldTill.setComponentError(null);
					}
				});

		cbInsCompany.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {	
						Object selection=cbAssistCompany.getValue();														
						if (selection != null){
							Organizations value=(Organizations) cbInsCompany.getValue();
							policy.setOrganizationsByInsuaranceCompany(value);
							cbInsCompany.setData(value);
							cbInsCompany.setComponentError(null);
						}
					}
				});
		
		cbAssistCompany.addValueChangeListener(
				new Property.ValueChangeListener() {
					private static final long serialVersionUID = 1L;
					public void valueChange(ValueChangeEvent event) {	
						Object selection=cbAssistCompany.getValue();								
						if (selection != null){
							Organizations value=(Organizations) cbAssistCompany.getValue();
							policy.setOrganizationsByAssistanceCompany(value);
							cbAssistCompany.setData(value);
							cbAssistCompany.setComponentError(null);
						}
					}
				});
		
		//insert combo boxes with data
		final BeanItemContainer<Organizations> assistCompanyContainer = new BeanItemContainer<Organizations>(Organizations.class);
		assistCompanyContainer.addAll(getOrganizationList(dao,"assist_comp"));
		cbAssistCompany.setContainerDataSource(assistCompanyContainer);
		cbAssistCompany.setItemCaptionPropertyId("name");
		
		final BeanItemContainer<Organizations> insCompanyContainer = new BeanItemContainer<Organizations>(Organizations.class);
		insCompanyContainer.addAll(getOrganizationList(dao,"ins_comp"));
		cbInsCompany.setContainerDataSource(insCompanyContainer);
		cbInsCompany.setItemCaptionPropertyId("name");

		//retrieve and bind data to fields
		if(getData() != null){
			textFieldPn.setValue(policy.getNumber());
			popupDateFieldFrom.setValue(policy.getValidFrom());
			popupDateFieldTill.setValue(policy.getValidTo());
			cbInsCompany.setValue(policy.getOrganizationsByInsuaranceCompany());
			cbAssistCompany.setValue(policy.getOrganizationsByAssistanceCompany());
		}

		return formLayout;

	}

	public Set<Organizations> getOrganizationList(DaoIntrfc dao,String typeOrganization){
		Enumerations enumeration=(Enumerations) dao.findById("Enumerations",typeOrganization);
		
		Set<Organizations> orgs=enumeration.getOrganizationses();
		orgs.size();
		
		return orgs;
	}

	@Override
	public boolean process(HashMap<String, Form> steps) {
		//get access to DB
		DaoIntrfc dao = getDao();

		Policies policy = (Policies) getData();
		dao.evict(policy);

		steps.get("stepPolicy").setData(policy);

		return true;
	}

}
