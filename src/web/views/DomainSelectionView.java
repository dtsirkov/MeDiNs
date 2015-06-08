package web.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import web.MedinsUI;
import web.classes.ComponentValidator;
import web.classes.Domain;
import web.classes.Header;
import web.classes.PropertyManager;
import web.components.table.paged.PagedTable;
import web.forms.DomainSelectionForm;

import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import database.dao.DaoIntrfc;
import database.pojo.CaseInfo;

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


		//create the root layout
		final VerticalLayout root = new VerticalLayout();
		root.addStyleName("personcreate");
		root.setSizeFull();

		//create main layout
		VerticalLayout mainLayout = new VerticalLayout();

		//create and add header
		Header header = new Header(getUi(), "domainSelection");
		mainLayout.addComponent(header.getMainLayout());

		//build domain selection layout
		VerticalLayout domainLayout = buildDomainSelectionLayout();
		mainLayout.addComponent(domainLayout);	
		domainLayout.setMargin(new MarginInfo(true, false, true, false));
		mainLayout.setComponentAlignment(domainLayout, Alignment.TOP_CENTER);

		//build activity selection layout
		VerticalLayout activityPanelLayout = buildActivitySelectionLayout();
		mainLayout.addComponent(activityPanelLayout);
		activityPanelLayout.setMargin(new MarginInfo(true, false, true, false));
		mainLayout.setComponentAlignment(activityPanelLayout, Alignment.TOP_CENTER);

		root.addComponent(mainLayout);
		root.setComponentAlignment(mainLayout, Alignment.TOP_CENTER);

		return root;

	}

	public VerticalLayout buildDomainSelectionLayout(){

		//damain selection layout
		VerticalLayout domainLayout = new VerticalLayout();
		domainLayout.setSizeUndefined();

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
			getNavigator().addView(domainList.get(i).getLabel(), activitySelectionView);
		}
		domainLayout.addComponent(grid);

		return domainLayout;
	}

	public VerticalLayout buildActivitySelectionLayout(){

		//get propertyManager
		final PropertyManager propertyManager = getPropertyManager();

		//build panel layout
		VerticalLayout activityPanelLayout = new VerticalLayout();
		activityPanelLayout.setSizeUndefined();

		//activity selection panel
		Panel activitySelectionPanel = new Panel(propertyManager.getLabelDtl("notCompletedActivities"));
		activitySelectionPanel.setSizeUndefined();

		HorizontalLayout searchLayout = new HorizontalLayout();
		VerticalLayout activityLayout = new VerticalLayout();
		activityLayout.setWidth("800px");

		FormLayout formLayout1 = new FormLayout();
		FormLayout formLayout2 = new FormLayout();

		formLayout1.setMargin(new MarginInfo(true, true, true, true));
		formLayout2.setMargin(new MarginInfo(true, true, true, true));

		String width = "180px", height = "-1px";

		//reference number text field
		TextField referenceNumberTF = new TextField(propertyManager.getLabelDtl("referenceNumber"));
		referenceNumberTF.setImmediate(true);
		referenceNumberTF.setWidth(width);
		referenceNumberTF.setHeight(height);
		formLayout1.addComponent(referenceNumberTF);

		//createdBy text field
		TextField createdByTF = new TextField(propertyManager.getLabelDtl("createdBy"));
		createdByTF.setImmediate(true);
		createdByTF.setWidth(width);
		createdByTF.setHeight(height);
		formLayout1.addComponent(createdByTF);

		//updatedBy text field
		TextField updatedByTF = new TextField(propertyManager.getLabelDtl("updatedBy"));
		updatedByTF.setImmediate(true);
		updatedByTF.setWidth(width);
		updatedByTF.setHeight(height);
		formLayout1.addComponent(updatedByTF);

		//from date
		PopupDateField fromPDF = new PopupDateField(propertyManager.getLabelDtl("from"));
		fromPDF.setImmediate(true);
		fromPDF.setWidth(width);
		fromPDF.setHeight(height);
		formLayout2.addComponent(fromPDF);

		//to date
		PopupDateField toPDF = new PopupDateField(propertyManager.getLabelDtl("to"));
		toPDF.setImmediate(true);
		toPDF.setWidth(width);
		toPDF.setHeight(height);
		formLayout2.addComponent(toPDF);

		//add search layout
		searchLayout.addComponent(formLayout1);
		searchLayout.addComponent(formLayout2);
		activityLayout.addComponent(searchLayout);

		//create activity table
		final PagedTable resultTable = new PagedTable(); 
		resultTable.setContainerDataSource(createContainer());
		resultTable.setWidth("800px");
		resultTable.setPageLength(10);
		resultTable.setImmediate(true);
		resultTable.setSelectable(true);
		resultTable.setAlwaysRecalculateColumnWidths(false);
		resultTable.setColumnHeaders(getTableHeader());
		resultTable.createControls();
		activityLayout.addComponent(resultTable);
		activityLayout.setComponentAlignment(resultTable, Alignment.MIDDLE_CENTER);

		activitySelectionPanel.setContent(activityLayout);
		activityPanelLayout.addComponent(activitySelectionPanel);

		return activityPanelLayout;

	}

	public String[] getTableHeader(){

		PropertyManager propertyManager = getPropertyManager();

		String caseDate = propertyManager.getLabelDtl("caseDate");
		String referanceNumber1 = propertyManager.getLabelDtl("referanceNumber1");
		String patientInfo = propertyManager.getLabelDtl("patientInfo");

		return new String[] {caseDate, referanceNumber1, patientInfo};

	}

	public IndexedContainer createContainer() {

		String key, value;
		Map<String, String> searchConstraintFiltered = new HashMap<String, String>();	
		Map<String, String> searchConstraint = new HashMap<String, String>();

		searchConstraint.put("status_1", "yes");
		Iterator<String> constraintIterator = searchConstraint.keySet().iterator();

		while (constraintIterator.hasNext()) {
			key = constraintIterator.next();	
			value = searchConstraint.get(key);
			if (!value.equals(""))
				searchConstraintFiltered.put(key, value);
		} 

		System.out.println(searchConstraint);
		@SuppressWarnings("unchecked")
		List<CaseInfo> resultList = (List<CaseInfo>) this.getDao().searchByConstaint("CaseInfo", searchConstraintFiltered);

		ArrayList<CaseInfo> searchResult;
		if(resultList.isEmpty())
			searchResult = new ArrayList<CaseInfo>();
		else
			searchResult = new ArrayList<CaseInfo>(resultList);

		IndexedContainer container = new IndexedContainer();
		container.addContainerProperty("caseDate", String.class, null);
		container.addContainerProperty("referanceNumber1", String.class, null);
		container.addContainerProperty("patientInfo", String.class, null);
		for (int i = 0; i < searchResult.size(); i++) {
			Item item = container.addItem(searchResult.get(i));
			item.getItemProperty("caseDate").setValue(searchResult.get(i).getCaseDate().toString());
			item.getItemProperty("referanceNumber1").setValue(searchResult.get(i).getReferanceNumber1());
			item.getItemProperty("patientInfo").setValue(searchResult.get(i).getPersonsByPatientInfo().getFirstName());
		}
		container.sort(new Object[] { "firstName" }, new boolean[] { true });
		return container;
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