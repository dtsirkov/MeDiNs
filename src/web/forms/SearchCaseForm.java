package web.forms;

import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import web.StepIntrfc;
import web.beans.ComboxBean;
import web.classes.PropertyManager;
import web.components.table.paged.PagedTable;
import web.views.AbstractView;
import database.dao.DaoIntrfc;
import database.pojo.CaseInfo;
import database.pojo.Contacts;
import database.pojo.Enumerations;
import database.pojo.MedicalReport;
import database.pojo.Organizations;
import database.pojo.Persons;
import database.pojo.Policies;
import database.pojo.Services;
import database.pojo.TouristVisit;

@SuppressWarnings("hiding")
public class SearchCaseForm<CaseInfo> extends Form implements StepIntrfc{

	private static final long serialVersionUID = 1L;

	private FormLayout searchByCriteriaLayout;

	private HorizontalLayout searchByObjectLayout;
	private  ArrayList<CaseInfo> searchByObjectResult = new ArrayList<CaseInfo>();

	private HashMap<String, ArrayList> searchConstraint = new HashMap<String, ArrayList>();

	private ArrayList<CaseInfo> searchResult = new ArrayList<CaseInfo>();

	private Button searchButton;
	private VerticalLayout resultTableLayout;
	private String dBTableName;
	private String id;
	private CaseInfo selectedItem;
	private String dtOp="=";

	public SearchCaseForm(){}

	public SearchCaseForm(FormLayout searchByCriteria){
		setSearchByCriteriaLayout(searchByCriteria);
	}

	public SearchCaseForm(AbstractView view, String label){

		super(view, label, new VerticalLayout());	
		setCompositionRoot(getLayout());
		setdBTableName("CaseInfo");

	}

	public Layout buildLayout(String mode){
		Layout root=buildMainLayout(mode);
		getSearchByObjectLayout().setVisible(false);
		return root;
	};

	public FormLayout getSearchByCriteriaLayout() {
		return searchByCriteriaLayout;
	}

	public void setSearchByCriteriaLayout(FormLayout searchByCriteriaLayout) {
		this.searchByCriteriaLayout = searchByCriteriaLayout;
	}

	public HorizontalLayout getSearchByObjectLayout() {
		return searchByObjectLayout;
	}

	public void setSearchByObjectLayout(HorizontalLayout searchByObjectLayout) {
		this.searchByObjectLayout = searchByObjectLayout;
	}

	public Map<String, ArrayList> getSearchConstraint() {
		return searchConstraint;
	}

	public void setSearchConstraint(HashMap<String, ArrayList> searchConstraint) {
		this.searchConstraint = searchConstraint;
	}

	public ArrayList<CaseInfo> getSearchResult() {
		return searchResult;
	}

	public void setSearchResult(ArrayList<CaseInfo> searchResult) {
		this.searchResult = searchResult;
	}

	public Button getSearchButton() {
		return searchButton;
	}

	public void setSearchButton(Button searchButton) {
		this.searchButton = searchButton;
	}

	public VerticalLayout getResultTableLayout() {
		return resultTableLayout;
	}

	public void setResultTableLayout(VerticalLayout resultTableLayout) {
		this.resultTableLayout = resultTableLayout;
	}

	public String getdBTableName() {
		return dBTableName;
	}

	public void setdBTableName(String dBTableName) {
		this.dBTableName = dBTableName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public CaseInfo getSelectedItem() {
		return selectedItem;
	}

	public String getDtOp() {
		return dtOp;
	}

	public void setDtOp(String dtOp) {
		this.dtOp = dtOp;
	}

	@SuppressWarnings("unchecked")
	public Layout buildMainLayout(String mode){

		//get main web.components.table.generated.layout
		VerticalLayout root = (VerticalLayout)getLayout();
		//get propertyManager
		final PropertyManager propertyManager = getPropertyManager();
		//get access to DB
		final DaoIntrfc dao = getDao();

		//remove all current web.components.table.generated.components
		root.removeAllComponents();

		//define measurements of the web.components.table.generated.components 
		//String width = "180px", height = "-1px";

		resultTableLayout = new VerticalLayout();	
		resultTableLayout.setSizeUndefined();
		final PagedTable resultTable = createTable();
		resultTableLayout.addComponent(resultTable);
		resultTableLayout.addComponent(resultTable.createControls());

		resultTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
			private static final long serialVersionUID = 1L;

			public void itemClick(ItemClickEvent event) {
				selectedItem = (CaseInfo)event.getItemId();
			}
		});

		searchByCriteriaLayout = buildSearchByCriteriaLayout();
		searchByObjectLayout = buildSearchByObject(); 

		searchByCriteriaLayout.setSizeUndefined();
		searchByObjectLayout.setSizeUndefined();

		root.addComponent(searchByCriteriaLayout);
		root.addComponent(searchByObjectLayout);

		searchButton = new Button(propertyManager.getButtonDtl("search"));

		searchButton.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			ArrayList value;
			String key;
			HashMap<String, ArrayList> searchConstraintFiltered;
			Iterator<String> constraintIterator;

			public void buttonClick(ClickEvent event) {

				searchConstraintFiltered = new HashMap<String, ArrayList>();
				constraintIterator = searchConstraint.keySet().iterator();

				while (constraintIterator.hasNext()) {
					key = constraintIterator.next();	
					value = searchConstraint.get(key);
					if (!value.equals(""))
						searchConstraintFiltered.put(key, value);
				} 

				System.out.println(searchConstraint);
				List<CaseInfo> resultList = (List<CaseInfo>) searchByConstraintsWithOp(dao.getSession(),dBTableName, searchConstraintFiltered);

				if(resultList.isEmpty())
					searchResult = new ArrayList<CaseInfo>();
				else
					searchResult = new ArrayList<CaseInfo>(resultList);

				resultTable.setContainerDataSource(createContainer());

				if(searchResult.isEmpty())
					Notification.show(propertyManager.getLabelDtl("stepObjectFound"));
			}
		});

		root.addComponent(searchButton);
		root.setComponentAlignment(searchButton, Alignment.MIDDLE_RIGHT);

		root.addComponent(resultTableLayout);
		root.setComponentAlignment(resultTableLayout, Alignment.MIDDLE_CENTER);

		selectedItem = (CaseInfo)resultTable.getSelectedItem();
		setData(selectedItem);
		System.out.println(selectedItem);

		return root;
	};

	public FormLayout buildSearchByCriteriaLayout(){
		//get access to DB
		DaoIntrfc dao = getDao();

		//get propertyManager
		PropertyManager propertyManager = getPropertyManager();

		FormLayout formLayout = new FormLayout();

		final TextField  idSearchTF = new TextField(getPropertyManager().getLabelDtl("identificator"));
		idSearchTF.setImmediate(true);
		idSearchTF.setWidth("180px");
		idSearchTF.setHeight("-1px");
		formLayout.addComponent(idSearchTF);	
		idSearchTF.setNullRepresentation("");
		//addToSearchConstraint(idSearchTF,"=", "id");

		// textFieldName
		final TextField referenceNumberTF = new TextField();
		referenceNumberTF.setCaption(propertyManager.getLabelDtl("reference number"));
		referenceNumberTF.setImmediate(false);
		referenceNumberTF.setWidth("207px");
		referenceNumberTF.setHeight("-1px");
		referenceNumberTF.setRequired(false);
		formLayout.addComponent(referenceNumberTF);
		addToSearchConstraint(referenceNumberTF,"=", "referanceNumber1");

		HorizontalLayout hlDate=new HorizontalLayout();
		hlDate.setCaption(propertyManager.getLabelDtl("case date"));

		final ComboBox dateOp = new ComboBox();
		dateOp.setImmediate(false);
		dateOp.setWidth("50px");
		dateOp.setHeight("-1px");
		dateOp.setNullSelectionAllowed(false);
		Object[] ops={">=",">","=","<","<="};
		dateOp.addItems(ops);

		hlDate.addComponent(dateOp);

		final PopupDateField caseStartDate = new PopupDateField();
		caseStartDate.setImmediate(true);
		caseStartDate.setInvalidAllowed(false);
		caseStartDate.setRequired(false);
		caseStartDate.setWidth("200px");
		caseStartDate.setHeight("-1px");

		hlDate.addComponent(caseStartDate);

		formLayout.addComponent(hlDate);

		//add listeners
		dateOp.addValueChangeListener(new Property.ValueChangeListener(){
			private static final long serialVersionUID = 1L;
			@Override
			public void valueChange(ValueChangeEvent event) {
				Object value = event.getProperty().getValue();
				if (value != null){
					dateOp.setData(value);
					setDtOp(value.toString());
					ArrayList<Object> mapValue=new ArrayList<Object>();
					mapValue.add(getDtOp());
					mapValue.add((Date) caseStartDate.getValue());
					searchConstraint.put("caseDate", mapValue);
				}					
			}

		});

		caseStartDate.addValueChangeListener(new Property.ValueChangeListener(){
			private static final long serialVersionUID = 1L;
			@Override
			public void valueChange(ValueChangeEvent event) {
				Object value = event.getProperty().getValue();
				if (value != null){
					ArrayList<Object> mapValue=new ArrayList<Object>();
					mapValue.add(getDtOp());
					caseStartDate.setData(value);
					mapValue.add((Date) value);
					searchConstraint.put("caseDate", mapValue);
				}
			}

		});

		idSearchTF.addValueChangeListener(new Property.ValueChangeListener(){
			private static final long serialVersionUID = 1L;
			@Override
			public void valueChange(ValueChangeEvent event) {
				Object value = event.getProperty().getValue();
				if (value != null){
					ArrayList<Object> mapValue=new ArrayList<Object>();
					mapValue.add("=");
					idSearchTF.setData(value);
					mapValue.add(Integer.valueOf(value.toString()));
					searchConstraint.put("id", mapValue);
				}					
			}

		});

		setSearchByCriteriaLayout(formLayout);
		setId("id");

		return formLayout;
	}

	private HorizontalLayout buildSearchByObject(){

		HorizontalLayout searchByObjectLayout = new HorizontalLayout();
		searchByObjectLayout.setSizeUndefined();

		Label idntfrLbl = new Label(getPropertyManager().getLabelDtl("idntfr"));
		searchByObjectLayout.addComponent(idntfrLbl);

		final ComboBox tableCB = new ComboBox();
		tableCB.addItem("Contact");
		searchByObjectLayout.addComponent(tableCB);

		final TextField idntfrValueTF = new TextField();
		searchByObjectLayout.addComponent(idntfrValueTF);

		Button searchByObjectBtn = new Button("...");
		searchByObjectLayout.addComponent(searchByObjectBtn);

		idntfrValueTF.addValueChangeListener(new Property.ValueChangeListener(){

			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				String idntfr = event.getProperty().getValue().toString();
				idntfrValueTF.setData(idntfr);
				String table = tableCB.getValue().toString();
				searchByObjectResult = searchByObject(table, idntfr);
			}

		});

		return searchByObjectLayout;

	}

	private ArrayList<CaseInfo> searchByObject(String table, String idntfr){
		return new ArrayList<CaseInfo>();
	};

	public IndexedContainer createContainer() {
		IndexedContainer container = new IndexedContainer();
		container.addContainerProperty("referance_number_1", String.class, null);
		container.addContainerProperty("case_date", String.class, null);
		ArrayList<CaseInfo> searchResult = getSearchResult();
		for (int i = 0; i < searchResult.size(); i++) {
			Item item = container.addItem(searchResult.get(i));
			item.getItemProperty("referance_number_1").setValue(((database.pojo.CaseInfo) searchResult.get(i)).getReferanceNumber1());
			item.getItemProperty("case_date").setValue(((database.pojo.CaseInfo) searchResult.get(i)).getCaseDate().toString());
		}
		container.sort(new Object[] { "case_date" }, new boolean[] { true });
		return container;
	}

	public String[] getTableHeader(){

		PropertyManager propertyManager = getPropertyManager();		
		String refNumber = propertyManager.getLabelDtl("referance_number_1");
		String caseDate = propertyManager.getLabelDtl("case_date");

		return new String[] { refNumber, caseDate};

	}

	private PagedTable createTable() {
		final PagedTable pagedTable = new PagedTable();
		pagedTable.setContainerDataSource(createContainer());
		//pagedTable.setRowHeaderMode(Table.RowHeaderMode.ICON_ONLY);
		pagedTable.setWidth("600px");
		pagedTable.setPageLength(10);
		pagedTable.setImmediate(true);
		pagedTable.setSelectable(true);
		pagedTable.setAlwaysRecalculateColumnWidths(false);
		pagedTable.setColumnHeaders(getTableHeader());
		return pagedTable;
	};

	public static List<?> searchByConstraintsWithOp(Session session,String dBTableName, Map<String, ArrayList> constraint){	
		@SuppressWarnings("rawtypes")
		List result = new ArrayList();
		try{
			if(!constraint.isEmpty()){

				String key, concat;
				String searchQuery = "from " + dBTableName + " where ";
				Iterator<String> queryIterator = constraint.keySet().iterator();
				Iterator<String> constrIterator = constraint.keySet().iterator();

				while (queryIterator.hasNext()) {
					key = queryIterator.next();	
					ArrayList arrayConstr = constraint.get(key);
					String op = (String) arrayConstr.get(0);
					concat = key + " "+op+ " :"+key;
					searchQuery += concat;
					if (queryIterator.hasNext())
						searchQuery += " and ";		
				}  
				Query query = session.createQuery(searchQuery);
				while (constrIterator.hasNext()) {
					key = constrIterator.next();
					ArrayList arrayConstr=constraint.get(key);
					Object obj = arrayConstr.get(1);
					query.setParameter(key, obj);
				}

				result =  query.list();	
			}

		} catch (RuntimeException re) {
			re.printStackTrace();	
		}

		return result;
	}

	public HashMap<String, ArrayList> addToSearchConstraint(final TextField textField,final String op, final String key){

		textField.addValueChangeListener(new Property.ValueChangeListener(){
			private static final long serialVersionUID = 1L;
			@Override
			public void valueChange(ValueChangeEvent event) {
				Object value = event.getProperty().getValue();
				if (value != null){
					ArrayList<Object> mapValue=new ArrayList<Object>();
					mapValue.add(op);
					textField.setData(value);
					mapValue.add(value.toString());
					searchConstraint.put(key, mapValue);
				}
			}

		});
		return searchConstraint;
	}

	@Override
	public boolean process(HashMap<String, Form> steps) {

		boolean processed = false;

		//get access to DB
		DaoIntrfc dao = getDao();

		CaseInfo selectedCase = getSelectedItem();

		steps.get("stepSearchCase").setData(selectedCase);
		steps.get("stepCaseGeneralInfo").setData(selectedCase);

		Persons person=((database.pojo.CaseInfo) selectedCase).getPersonsByPatientInfo();
		steps.get("stepCreatePerson").setData(person);

		Set<Contacts> contacts=person.getContactses();
		contacts.size();
		if (contacts.iterator().hasNext()) {
			Contacts contact=contacts.iterator().next();
			steps.get("stepCreateContact").setData(contact);
		}

		Set<TouristVisit> touristVisits=person.getTouristVisits();
		touristVisits.size();
		if (touristVisits.iterator().hasNext()){
			TouristVisit touristVisit=touristVisits.iterator().next();
			steps.get("stepTouristVisit").setData(touristVisit);
		}

		Policies policy=((database.pojo.CaseInfo) selectedCase).getPolicies();
		steps.get("stepPolicy").setData(policy);

		MedicalReport medicalReport=((database.pojo.CaseInfo) selectedCase).getMedicalReport();
		steps.get("stepMedicalReport").setData(medicalReport);

		Set<Services> servises=((database.pojo.CaseInfo) selectedCase).getServiceses();
		servises.size();
		steps.get("stepServices").setData(servises);

		dao.evict(selectedCase);

		processed = true;
		return processed;
	}

}
