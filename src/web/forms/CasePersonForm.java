package web.forms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;




import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import database.dao.DaoIntrfc;
import database.pojo.Persons;

import web.StepIntrfc;
import web.classes.PropertyManager;
import web.components.table.paged.PagedTable;
import web.views.AbstractView;

public class CasePersonForm<T> extends SearchPersonForm implements StepIntrfc {

	private FormLayout searchByIdLayout;
	private FormLayout searchByCriteriaLayout;	
	private HorizontalLayout searchByObjectLayout;
	private  ArrayList<T> searchByObjectResult = new ArrayList<T>();
	private VerticalLayout resultTableLayout;

	private HashMap<String, String> searchConstraint = new HashMap<String, String>();

	private ArrayList<T> searchResult = new ArrayList<T>();

	private T selectedItem;
	private PagedTable resultTable;	

	public ArrayList<Persons> getSearchResult() {
		return (ArrayList<Persons>) searchResult;
	}

	private FormLayout searchLayout;
	private String dBTableName;
	public String getdBTableName() {
		return dBTableName;
	}

	public void setdBTableName(String dBTableName) {
		this.dBTableName = dBTableName;
	}

	private String id;
	public void setId(String id) {
		this.id = id;
	}

	private static final long serialVersionUID = 1L;

	public CasePersonForm(AbstractView view, String label) {
		super(view, label);
		setdBTableName("Persons");
		setId("socialNumber");
	}

	public Layout buildLayout(String mode){

		Layout root = (VerticalLayout)getLayout();
		HorizontalLayout hlayout=new HorizontalLayout();
		//remove all current web.components.table.generated.components
		root.removeAllComponents();
		Button createPerson=new Button("Create Person");
		createPerson.addClickListener(new Button.ClickListener() {
			final Navigator navigator =getView().getUI().getNavigator();	
			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {				
				navigator.navigateTo("createCase2");
			}});
		Button searchPerson = new Button("Search Person");

		hlayout.addComponent(createPerson);
		hlayout.addComponent(searchPerson);
		root.addComponent(hlayout);
		final Layout searchLayout=buildSearchLayout();
		root.addComponent(searchLayout);
		searchLayout.setVisible(false);

		searchPerson.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				searchLayout.setVisible(true);				
			}});
		return root;
	}

	public Layout buildSearchLayout(){

		//get main web.components.table.generated.layout
		VerticalLayout root = new VerticalLayout();
		//get propertyManager
		final PropertyManager propertyManager = getPropertyManager();
		//get access to DB
		final DaoIntrfc dao=getDao();

		//remove all current web.components.table.generated.components
		root.removeAllComponents();	

		searchByIdLayout=buildSearchByIdLayout();
		root.addComponent(searchByIdLayout);
		searchByCriteriaLayout = buildSearchByCriteriaLayout();
		root.addComponent(searchByCriteriaLayout);

		Button searchButton = new Button(propertyManager.getButtonDtl("search"));
		resultTable = createTable();
		searchButton.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			String key, value;
			Map<String, String> searchConstraintFiltered;
			Iterator<String> constraintIterator;

			public void buttonClick(ClickEvent event) {

				searchConstraintFiltered = new HashMap<String, String>();
				constraintIterator = searchConstraint.keySet().iterator();

				while (constraintIterator.hasNext()) {
					key = constraintIterator.next();	
					value = searchConstraint.get(key);
					if (!value.equals(""))
						searchConstraintFiltered.put(key, value);
				} 

				System.out.println(searchConstraint);
				List resultList = dao.searchByConstaint(dBTableName, searchConstraintFiltered);

				if(resultList.isEmpty())
					searchResult = new ArrayList<T>();
				else
					searchResult = new ArrayList<T>(resultList);

				resultTable.setContainerDataSource(createContainer());

				if(searchResult.isEmpty())
					Notification.show(propertyManager.getLabelDtl("stepObjectFound"));

			}
		});

		resultTableLayout = new VerticalLayout();	
		resultTableLayout.setSizeUndefined();
		resultTableLayout.addComponent(resultTable);
		resultTableLayout.addComponent(resultTable.createControls());

		resultTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {

			private static final long serialVersionUID = 1L;

			public void itemClick(ItemClickEvent event) {
				selectedItem = (T)event.getItemId();
			}
		});
		searchByObjectLayout = buildSearchByObject();
		root.addComponent(searchByObjectLayout);
		root.addComponent(searchButton);
		root.setComponentAlignment(searchButton, Alignment.MIDDLE_RIGHT);
		root.addComponent(resultTableLayout);
		root.setComponentAlignment(resultTableLayout, Alignment.MIDDLE_CENTER);

		selectedItem = (T)resultTable.getSelectedItem();
		setData(selectedItem);
		System.out.println(selectedItem);		

		return root;
	}

	public FormLayout buildSearchByCriteriaLayout(){

		//get propertyManager
		PropertyManager propertyManager = getPropertyManager();

		//define measurements of the web.components.table.generated.components 
		String width = "180px", height = "-1px";

		FormLayout formLayout = new FormLayout();
		formLayout.removeAllComponents();

		// personFirstNameTF
		TextField personFirstNameTF = new TextField(propertyManager.getLabelDtl("personFirstName"));
		personFirstNameTF.setImmediate(true);
		personFirstNameTF.setWidth(width);
		personFirstNameTF.setHeight(height);
		formLayout.addComponent(personFirstNameTF);
		addToSearchConstraint(personFirstNameTF, "firstName");

		// personLastNameTF
		TextField personLastNameTF = new TextField(propertyManager.getLabelDtl("personLastName"));
		personLastNameTF.setImmediate(true);
		personLastNameTF.setWidth(width);
		personLastNameTF.setHeight(height);
		formLayout.addComponent(personLastNameTF);
		addToSearchConstraint(personLastNameTF, "lastName");	

		setSearchByCriteriaLayout(formLayout);
		return formLayout;
	}

	private FormLayout buildSearchByIdLayout(){
		FormLayout searchByIdLayout = new FormLayout();
		final TextField  idSearchTF = new TextField(getPropertyManager().getLabelDtl("identificator"));
		idSearchTF.setImmediate(true);
		idSearchTF.setWidth("180px");
		idSearchTF.setHeight("-1px");
		searchByIdLayout.addComponent(idSearchTF);		
		addToSearchConstraint(idSearchTF, getId());
		return searchByIdLayout;
	}

	private PagedTable createTable() {
		final PagedTable pagedTable = new PagedTable();
		pagedTable.setContainerDataSource(createContainer());
		//pagedTable.setRowHeaderMode(Table.RowHeaderMode.ICON_ONLY);
		pagedTable.setWidth("400px");
		pagedTable.setPageLength(10);
		pagedTable.setImmediate(true);
		pagedTable.setSelectable(true);
		pagedTable.setAlwaysRecalculateColumnWidths(false);
		pagedTable.setColumnHeaders(getTableHeader());
		return pagedTable;
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

	public IndexedContainer createContainer() {
		IndexedContainer container = new IndexedContainer();
		container.addContainerProperty("firstName", String.class, null);
		container.addContainerProperty("lastName", String.class, null);
		container.addContainerProperty("socialNumber", String.class, null);
		ArrayList<Persons> searchResult = getSearchResult();
		for (int i = 0; i < searchResult.size(); i++) {
			Item item = container.addItem(searchResult.get(i));
			item.getItemProperty("firstName").setValue(searchResult.get(i).getFirstName());
			item.getItemProperty("lastName").setValue(searchResult.get(i).getLastName());
			item.getItemProperty("socialNumber").setValue(searchResult.get(i).getSocialNumber());
		}
		container.sort(new Object[] { "firstName" }, new boolean[] { true });
		return container;
	}
	private ArrayList<T> searchByObject(String table, String idntfr){
		return new ArrayList<T>();
	};

	public FormLayout getSearchLayout() {
		return (FormLayout) buildSearchLayout();
	}

	public void setSearchLayout(FormLayout searchLayout) {
		this.searchLayout = searchLayout;
	}

	public HashMap<String, String> addToSearchConstraint(final TextField textField, final String key){

		textField.addValueChangeListener(new Property.ValueChangeListener(){
			private static final long serialVersionUID = 1L;
			@Override
			public void valueChange(ValueChangeEvent event) {
				String value = event.getProperty().getValue().toString();
				textField.setData(value);
				searchConstraint.put(key, value);
			}

		});
		return searchConstraint;
	}

	public String getId() {
		return id;
	}
	public Persons getSelectedItem() {
		return (Persons) selectedItem;
	}

	@Override
	public boolean process(HashMap<String, Form> steps) {
		//get access to DB
		DaoIntrfc dao = getDao();

		Persons selectedPerson = getSelectedItem();
		dao.evict(selectedPerson);

		steps.get("stepCreatePerson").setData(selectedPerson);

		return true;
	}

}
