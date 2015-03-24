package web.forms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import web.classes.PropertyManager;
import web.components.PagedTable;
import web.views.AbstractView;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.IndexedContainer;
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
import com.vaadin.event.ItemClickEvent;

import dao.classes.DaoIntrfc;

@Theme("pagedtabletheme")
@Title("PagedTable Example")
public abstract class SearchForm <T extends Serializable> extends Form {

	private static final long serialVersionUID = 1L;

	private FormLayout searchByIdLayout;
	private ArrayList<T> searchByIdResult = new ArrayList<T>();

	private FormLayout searchByKeyLayout;
	private  ArrayList<T> searchByKeyResult = new ArrayList<T>();

	private FormLayout searchByCriteriaLayout;

	private HorizontalLayout searchByObjectLayout;
	private  ArrayList<T> searchByObjectResult = new ArrayList<T>();

	private HashMap<String, String> searchConstraint = new HashMap<String, String>();

	private ArrayList<T> searchResult = new ArrayList<T>();

	private Button searchButton;
	private VerticalLayout resultTableLayout;
	private String dBTableName;
	private String id;
	private T selectedItem;

	public SearchForm(){}

	public SearchForm(FormLayout searchByCriteria){
		setSearchByCriteriaLayout(searchByCriteria);
	}

	public SearchForm(SearchForm<T> searchForm){
		setSearchByCriteriaLayout(searchForm.getSearchByCriteriaLayout());
		setSearchButton(searchForm.getSearchButton());
		setResultTableLayout(searchForm.getResultTableLayout());
	}

	public SearchForm(AbstractView view, String label){

		super(view, label, new VerticalLayout());	
		setCompositionRoot(getLayout());

	}

	public FormLayout getSearchByIdntfrLayout() {
		return searchByIdLayout;
	}

	public void setSearchByIdntfrLayout(FormLayout searchByIdLayout) {
		this.searchByIdLayout = searchByIdLayout;
	}

	public void setSearchByIdntfr(FormLayout searchByIdLayout) {
		this.searchByIdLayout = searchByIdLayout;
	}

	public ArrayList<T> getSearchByIdntfrResult() {
		return searchByIdResult;
	}

	public void setSearchByIdntfrResult(ArrayList<T> searchByIdResult) {
		this.searchByIdResult = searchByIdResult;
	}

	public FormLayout getSearchByKeyLayout() {
		return searchByKeyLayout;
	}

	public void setSearchByKeyLayout(FormLayout searchByKeyLayout) {
		this.searchByKeyLayout = searchByKeyLayout;
	}

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

	public HashMap<String, String> getSearchConstraint() {
		return searchConstraint;
	}

	public void setSearchConstraint(HashMap<String, String> searchConstraint) {
		this.searchConstraint = searchConstraint;
	}

	public ArrayList<T> getSearchResult() {
		return searchResult;
	}

	public void setSearchResult(ArrayList<T> searchResult) {
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

	public T getSelectedItem() {
		return selectedItem;
	}

	@SuppressWarnings("unchecked")
	public Layout buildLayout(String mode){

		//get main layout
		VerticalLayout root = (VerticalLayout)getLayout();
		//get propertyManager
		final PropertyManager propertyManager = getPropertyManager();
		//get access to DB
		final DaoIntrfc dao = getDao();

		//remove all current components
		root.removeAllComponents();

		//define measurements of the components 
		//String width = "180px", height = "-1px";

		resultTableLayout = new VerticalLayout();	
		resultTableLayout.setSizeUndefined();
		final PagedTable resultTable = createTable();
		resultTableLayout.addComponent(resultTable);
		resultTableLayout.addComponent(resultTable.createControls());

		resultTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {

			private static final long serialVersionUID = 1L;

			public void itemClick(ItemClickEvent event) {
				selectedItem = (T)event.getItemId();
			}
		});

		searchByKeyLayout = buildSearchByKeyLayout();
		searchByCriteriaLayout = buildSearchByCriteriaLayout();
		searchByIdLayout = buildSearchByIdLayout();
		searchByObjectLayout = buildSearchByObject(); 

		searchByIdLayout.setSizeUndefined();
		searchByKeyLayout.setSizeFull();
		searchByCriteriaLayout.setSizeUndefined();
		searchByObjectLayout.setSizeUndefined();

		root.addComponent(searchByIdLayout);
		root.addComponent(searchByKeyLayout);
		root.addComponent(searchByCriteriaLayout);
		root.addComponent(searchByObjectLayout);

		searchButton = new Button(propertyManager.getButtonDtl("search"));

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

		root.addComponent(searchButton);
		root.setComponentAlignment(searchButton, Alignment.MIDDLE_RIGHT);

		root.addComponent(resultTableLayout);
		root.setComponentAlignment(resultTableLayout, Alignment.MIDDLE_CENTER);

		selectedItem = (T)resultTable.getSelectedItem();
		setData(selectedItem);
		System.out.println(selectedItem);

		return root;
	};

	private FormLayout buildSearchByIdLayout(){

		FormLayout searchByIdLayout = new FormLayout();

		final TextField  idSearchTF = new TextField(getPropertyManager().getLabelDtl("identificator"));
		idSearchTF.setImmediate(true);
		idSearchTF.setWidth("180px");
		idSearchTF.setHeight("-1px");
		searchByIdLayout.addComponent(idSearchTF);		
		addToSearchConstraint(idSearchTF, getId());

		idSearchTF.addValueChangeListener(new Property.ValueChangeListener(){

			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				String value = event.getProperty().getValue().toString();
				idSearchTF.setData(value);
				searchByIdResult = searchById(value);
			}

		});

		return searchByIdLayout;
	}

	private FormLayout buildSearchByKeyLayout(){

		FormLayout searchByKeyLayout = new FormLayout();

		final TextField keySearchTF = new TextField(getPropertyManager().getLabelDtl("searchByKey"));
		keySearchTF.setImmediate(true);
		keySearchTF.setWidth("180px");
		keySearchTF.setHeight("-1px");
		searchByKeyLayout.addComponent(keySearchTF);	
		addToSearchConstraint(keySearchTF, "keySearch");

		return searchByKeyLayout;
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

	private ArrayList<T> searchByKey(String value){
		return searchByKeyResult;
	}

	private ArrayList<T> searchById(String value){
		ArrayList<T> searchByIdResult = new ArrayList<T>();
		@SuppressWarnings("unchecked")
		T object = (T)getDao().findById(dBTableName, value);
		if (object != null)
			searchByIdResult.add(object);
		return searchByIdResult;
	}

	private ArrayList<T> searchByObject(String table, String idntfr){
		return new ArrayList<T>();
	};

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

	public HashMap<String, String> addToSearchConstraint(final ComboBox comboBox, final String key){

		comboBox.addValueChangeListener(new Property.ValueChangeListener(){
			private static final long serialVersionUID = 1L;
			@Override
			public void valueChange(ValueChangeEvent event) {
				String value = event.getProperty().getValue().toString();
				comboBox.setData(value);
				searchConstraint.put(key, value);
			}

		});
		return searchConstraint;
	}
	
	protected abstract ArrayList<T> search();

	protected abstract FormLayout buildSearchByCriteriaLayout();

	protected abstract IndexedContainer createContainer();

	protected abstract String[] getTableHeader();

}
