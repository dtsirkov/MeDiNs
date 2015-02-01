package web.forms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import pojo.classes.Persons;
import web.classes.ComponentValidator;
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
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.data.Item;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.Table.ColumnGenerator;

import dao.classes.DaoIntrfc;

@Theme("pagedtabletheme")
@Title("PagedTable Example")
public abstract class SearchForm <T extends Serializable> extends Form {

	private static final long serialVersionUID = 1L;

	private FormLayout searchByIdntfrLayout;
	private ArrayList<T> searchByIdntfrResult = new ArrayList<T>();

	private FormLayout searchByKeyLayout;
	private ArrayList<T> searchByKeyResult = new ArrayList<T>();

	private FormLayout searchByCriteriaLayout;
	private ArrayList<T> searchByCriteriaResult  = new ArrayList<T>();

	private HorizontalLayout searchByObjectLayout;
	private ArrayList<T> searchByObjectResult = new ArrayList<T>();

	private ArrayList<T> searchResult = new ArrayList<T>();

	private Button searchButton;
	private VerticalLayout resultTableLayout;
	private String dBTableName;
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
		return searchByIdntfrLayout;
	}

	public void setSearchByIdntfrLayout(FormLayout searchByIdntfrLayout) {
		this.searchByIdntfrLayout = searchByIdntfrLayout;
	}

	public void setSearchByIdntfr(FormLayout searchByIdntfrLayout) {
		this.searchByIdntfrLayout = searchByIdntfrLayout;
	}

	public ArrayList<T> getSearchByIdntfrResult() {
		return searchByIdntfrResult;
	}

	public void setSearchByIdntfrResult(ArrayList<T> searchByIdntfrResult) {
		this.searchByIdntfrResult = searchByIdntfrResult;
	}

	public FormLayout getSearchByKeyLayout() {
		return searchByKeyLayout;
	}

	public void setSearchByKeyLayout(FormLayout searchByKeyLayout) {
		this.searchByKeyLayout = searchByKeyLayout;
	}

	public ArrayList<T> getSearchByKeyResult() {
		return searchByKeyResult;
	}

	public void setSearchByKeyResult(ArrayList<T> searchByKeyResult) {
		this.searchByKeyResult = searchByKeyResult;
	}

	public FormLayout getSearchByCriteriaLayout() {
		return searchByCriteriaLayout;
	}

	public void setSearchByCriteriaLayout(FormLayout searchByCriteriaLayout) {
		this.searchByCriteriaLayout = searchByCriteriaLayout;
	}

	public ArrayList<T> getSearchByCriteriaResult() {
		return searchByCriteriaResult;
	}

	public void setSearchByCriteriaResult(ArrayList<T> searchByCriteriaResult) {
		this.searchByCriteriaResult = searchByCriteriaResult;
	}

	public HorizontalLayout getSearchByObjectLayout() {
		return searchByObjectLayout;
	}

	public void setSearchByObjectLayout(HorizontalLayout searchByObjectLayout) {
		this.searchByObjectLayout = searchByObjectLayout;
	}

	public ArrayList<T> getSearchByObjectResult() {
		return searchByObjectResult;
	}

	public void setSearchByObjectResult(ArrayList<T> searchByObjectResult) {
		this.searchByObjectResult = searchByObjectResult;
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

	public T getSelectedItem() {
		return selectedItem;
	}

	@SuppressWarnings("unchecked")
	public Layout buildLayout(String mode){

		//get main layout
		VerticalLayout root = (VerticalLayout)getLayout();
		//get component validator
		ComponentValidator componentValidator = getComponentValidator();
		//get propertyManager
		PropertyManager propertyManager = getPropertyManager();
		//get access to DB
		final DaoIntrfc dao = getDao();

		//remove all current components
		root.removeAllComponents();

		//define measurements of the components 
		String width = "180px", height = "-1px";

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


		searchByIdntfrLayout = new FormLayout();

		final TextField idSearchTF = new TextField(propertyManager.getLabelDtl("identificator"));
		idSearchTF.setImmediate(true);
		idSearchTF.setWidth(width);
		idSearchTF.setHeight(height);
		searchByIdntfrLayout.addComponent(idSearchTF);		

		idSearchTF.addValueChangeListener(new Property.ValueChangeListener(){

			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				String value = event.getProperty().getValue().toString();
				idSearchTF.setData(value);
				searchByIdntfrResult = searchByIdntfr(value);
			}

		});

		searchByKeyLayout = new FormLayout();

		final TextField keySearchTF = new TextField(propertyManager.getLabelDtl("searchByKey"));
		keySearchTF.setImmediate(true);
		keySearchTF.setWidth(width);
		keySearchTF.setHeight(height);
		searchByKeyLayout.addComponent(keySearchTF);		

		keySearchTF.addValueChangeListener(new Property.ValueChangeListener(){

			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				String value = event.getProperty().getValue().toString();
				keySearchTF.setData(value);
				searchByKeyResult = searchByKey(value);
			}

		});

		searchByObjectResult = searchByObject(dBTableName, "");

		searchByCriteriaLayout = buildSearchByCriteriaLayout();

		searchByObjectLayout = buildSearchByObject(); 

		searchByIdntfrLayout.setSizeUndefined();
		searchByKeyLayout.setSizeFull();
		searchByCriteriaLayout.setSizeUndefined();
		searchByObjectLayout.setSizeUndefined();

		root.addComponent(searchByIdntfrLayout);

		root.addComponent(searchByKeyLayout);

		root.addComponent(searchByCriteriaLayout);

		root.addComponent(searchByObjectLayout);

		searchButton = new Button(propertyManager.getButtonDtl("search"));

		searchButton.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {

				searchByCriteriaResult = search();

				searchResult = new ArrayList<T>();
				searchResult.addAll(searchByIdntfrResult);
				searchResult.addAll(searchByKeyResult);
				searchResult.addAll(searchByCriteriaResult);
				searchResult.addAll(searchByObjectResult);

				Set<T> hs = new HashSet<T>(searchResult);
				searchResult.clear();
				searchResult.addAll(hs);

				resultTable.setContainerDataSource(createContainer());

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

	public HorizontalLayout buildSearchByObject(){

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

	private ArrayList<T> searchByIdntfr(String value){
		ArrayList<T> searchByIdntfrResult = new ArrayList<T>();
		@SuppressWarnings("unchecked")
		T object = (T)getDao().findById(dBTableName, value);
		searchByIdntfrResult.add(object);
		return searchByIdntfrResult;
	}

	private ArrayList<T> searchByObject(String table, String idntfr){
		return new ArrayList<T>();
	};

	public PagedTable createTable() {
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

	public abstract ArrayList<T> search();

	public abstract FormLayout buildSearchByCriteriaLayout();

	public abstract IndexedContainer createContainer();

	public abstract String[] getTableHeader();

}
