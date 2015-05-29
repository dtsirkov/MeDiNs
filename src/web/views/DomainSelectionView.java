package web.views;

import java.util.ArrayList;

import web.MedinsUI;
import web.classes.ComponentValidator;
import web.classes.Domain;
import web.classes.Header;
import web.classes.PropertyManager;
import web.forms.DomainSelectionForm;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import database.dao.DaoIntrfc;

//@Theme("medins")
public class DomainSelectionView extends AbstractView{

	private static final long serialVersionUID = 1L;
	private ArrayList<Domain> domainList;

	public DomainSelectionView(MedinsUI ui) {

		super(ui);
		setLabel("domainSelectionView");

	}

	@Override
	public void enter(ViewChangeEvent event) {
		setCompositionRoot(buildLayout());
	}

	public Layout buildLayout(){

		//get propertyManager
		final PropertyManager propertyManager = getPropertyManager();
		//get access to DB
		final DaoIntrfc dao = getDao();	
		//get navigator 
		final Navigator navigator = getNavigator();
		//create component validator
		final ComponentValidator customValidator = new ComponentValidator(getPropertyManager());


		// Create the root layout
		final VerticalLayout root = new VerticalLayout();
		root.addStyleName("personcreate");
		root.setSizeFull();

		Header header = new Header(getUi());
		root.addComponent(header.getMainLayout());
		//root.setComponentAlignment(header, Alignment.TOP_RIGHT);

		Label title = new Label("Select Activity group to start an activity");
		title.addStyleName("title");
		root.addComponent(title);

		VerticalLayout verticalLayout = new VerticalLayout();
		verticalLayout.setSizeUndefined();

		Panel domainSelectionPanel = new Panel(propertyManager.getLabelDtl("domainSelection"));
		Panel activitySelectionPanel = new Panel(propertyManager.getLabelDtl("notCompletedActivities"));

		domainSelectionPanel.setSizeUndefined();
		activitySelectionPanel.setSizeUndefined();

		int rowsNumber = 0;
		int domainListSize = domainList.size();
		if (domainListSize % 2 == 0){
			rowsNumber = domainListSize / 2;
		}else{
			rowsNumber = domainListSize / 2 + 1;
		}

		GridLayout grid = new GridLayout(2, rowsNumber);
		grid.setSizeUndefined();
		grid.setSpacing(true);
		grid.addStyleName("test");
		grid.setMargin(new MarginInfo(true, true, true, true));

		for(int i = 0; i < domainListSize; i++){
			DomainSelectionForm domainSelectionForm = new DomainSelectionForm(this, domainList.get(i));
			domainSelectionForm.buildLayout("");
			grid.addComponent(domainSelectionForm);

			ActivitySelectionView activitySelectionView = new ActivitySelectionView(getUi());
			activitySelectionView.setDomain(domainList.get(i));
			navigator.addView(domainList.get(i).getLabel(), activitySelectionView);
		}

		//grid.setComponentAlignment(loginFormCorporate, Alignment.MIDDLE_LEFT);
		//grid.setComponentAlignment(loginFormIndividial, Alignment.MIDDLE_RIGHT);

		domainSelectionPanel.setContent(grid);

		String width = "180px", height = "-1px";

		HorizontalLayout horizontaLayout = new HorizontalLayout();
		VerticalLayout activityLayout = new VerticalLayout();
		verticalLayout.setWidth("800px");
		FormLayout formLayout1 = new FormLayout();
		FormLayout formLayout2 = new FormLayout();

		formLayout1.setMargin(new MarginInfo(true, true, true, true));
		formLayout2.setMargin(new MarginInfo(true, true, true, true));

		TextField referenceNumberTF = new TextField(propertyManager.getLabelDtl("referenceNumber"));
		referenceNumberTF.setImmediate(true);
		referenceNumberTF.setWidth(width);
		referenceNumberTF.setHeight(height);
		formLayout1.addComponent(referenceNumberTF);

		TextField createdByTF = new TextField(propertyManager.getLabelDtl("createdBy"));
		createdByTF.setImmediate(true);
		createdByTF.setWidth(width);
		createdByTF.setHeight(height);
		formLayout1.addComponent(createdByTF);

		TextField updatedByTF = new TextField(propertyManager.getLabelDtl("updatedBy"));
		updatedByTF.setImmediate(true);
		updatedByTF.setWidth(width);
		updatedByTF.setHeight(height);
		formLayout1.addComponent(updatedByTF);

		PopupDateField fromPDF = new PopupDateField(propertyManager.getLabelDtl("from"));
		fromPDF.setImmediate(true);
		fromPDF.setWidth(width);
		fromPDF.setHeight(height);
		formLayout2.addComponent(fromPDF);

		PopupDateField toPDF = new PopupDateField(propertyManager.getLabelDtl("to"));
		toPDF.setImmediate(true);
		toPDF.setWidth(width);
		toPDF.setHeight(height);
		formLayout2.addComponent(toPDF);

		horizontaLayout.addComponent(formLayout1);
		horizontaLayout.addComponent(formLayout2);

		activityLayout.addComponent(horizontaLayout);
		activitySelectionPanel.setContent(activityLayout);

		verticalLayout.addComponent(domainSelectionPanel);
		verticalLayout.addComponent(activitySelectionPanel);	

		root.addComponent(verticalLayout);
		verticalLayout.setMargin(new MarginInfo(true, false, false, false));
		root.setComponentAlignment(verticalLayout, Alignment.TOP_CENTER);

		return root;

	}

	public ArrayList<Domain> getDomainList() {
		return domainList;
	}

	public void setDomainList(ArrayList<Domain> domainList) {
		this.domainList = domainList;
	}

	@Override
	public Layout viewLayout(String mode) {
		// TODO Auto-generated method stub
		return null;
	}

}