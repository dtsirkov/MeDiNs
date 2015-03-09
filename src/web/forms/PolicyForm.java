package web.forms;

import java.util.HashMap;

import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import dao.classes.DaoIntrfc;

import web.StepIntrfc;
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

		//get main layout
		Layout formLayout = (FormLayout)getLayout();
		//get component validator
		ComponentValidator componentValidator = getComponentValidator();
		//get propertyManager
		PropertyManager propertyManager = getPropertyManager();
		//get access to DB
		DaoIntrfc dao = getDao();
		//remove all current components
		formLayout.removeAllComponents();
		
		HorizontalLayout hlPolicyNumber=new HorizontalLayout();
		hlPolicyNumber.setCaption(propertyManager.getLabelDtl("Policy number")+": ");
		hlPolicyNumber.setSpacing(true);
		
		// textFieldPn
		TextField textFieldPn = new TextField();
		textFieldPn.setImmediate(true);
		textFieldPn.setWidth("312px");
		textFieldPn.setHeight("-1px");
		textFieldPn.setRequired(false);
		hlPolicyNumber.addComponent(textFieldPn);
		formLayout.addComponent(hlPolicyNumber);
		
		HorizontalLayout hlValidPeriod=new HorizontalLayout();
		hlValidPeriod.setCaption(propertyManager.getLabelDtl("Validadtion period")+": ");
		hlValidPeriod.setSpacing(true);
		// popupDateFieldFrom
		PopupDateField popupDateFieldFrom = new PopupDateField();
		popupDateFieldFrom.setImmediate(true);
		popupDateFieldFrom.setWidth("120px");
		popupDateFieldFrom.setHeight("-1px");
		popupDateFieldFrom.setRequired(false);
		hlValidPeriod.addComponent(popupDateFieldFrom);
		// label_9
		Label label_9 = new Label();
		label_9.setImmediate(false);
		label_9.setWidth("4px");
		label_9.setHeight("-1px");
		label_9.setValue(" - ");
		hlValidPeriod.addComponent(label_9);
		// popupDateFieldTill
		PopupDateField popupDateFieldTill = new PopupDateField();
		popupDateFieldTill.setImmediate(true);
		popupDateFieldTill.setWidth("120px");
		popupDateFieldTill.setHeight("-1px");
		popupDateFieldTill.setRequired(false);
		hlValidPeriod.addComponent(popupDateFieldTill);
		formLayout.addComponent(hlValidPeriod);

		HorizontalLayout hlInsCompany=new HorizontalLayout();
		hlInsCompany.setCaption(propertyManager.getLabelDtl("Insurance company")+": ");
		hlInsCompany.setSpacing(true);
		// cbInsCompany
		ComboBox cbInsCompany = new ComboBox();
		cbInsCompany.setImmediate(true);
		cbInsCompany.setWidth("-1px");
		cbInsCompany.setHeight("-1px");
		cbInsCompany.setRequired(false);
		hlInsCompany.addComponent(cbInsCompany);
		formLayout.addComponent(hlInsCompany);
		
		HorizontalLayout hlAssistCompany=new HorizontalLayout();
		hlAssistCompany.setCaption(propertyManager.getLabelDtl("Assistance company")+": ");
		hlAssistCompany.setSpacing(true);
		// cbAssistCompany
		ComboBox cbAssistCompany = new ComboBox();
		cbAssistCompany.setImmediate(true);
		cbAssistCompany.setWidth("-1px");
		cbAssistCompany.setHeight("-1px");
		cbAssistCompany.setRequired(false);
		hlAssistCompany.addComponent(cbAssistCompany);		
		formLayout.addComponent(hlAssistCompany);
		
		return formLayout;
		
	}
	
	@Override
	public boolean process(HashMap<String, Form> steps) {
		// TODO Auto-generated method stub
		return true;
	}

}
