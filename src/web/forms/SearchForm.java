package web.forms;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import property_pckg.PropertyManager;
import web.classes.ComponentValidator;
import web.views.AbstractView;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import dao_classes.DaoIntrfc;

public abstract class SearchForm <T extends Serializable> extends Form {

	private static final long serialVersionUID = 1L;

	private FormLayout searchByKeyLayout;
	private ArrayList<T> searchByKeyResult = new ArrayList<T>();
	
	private FormLayout searchByCriteriaLayout;
	private ArrayList<T> searchByCriteriaResult  = new ArrayList<T>();
	
	private HorizontalLayout searchByObjectLayout;
	private ArrayList<T> searchByObjectResult;
	
	private Button searchButton;
	private ListSelect resultListLayout;
	private String table;
	
	public SearchForm(){}
	
	public SearchForm(FormLayout searchByCriteria){
		setSearchByCriteriaLayout(searchByCriteria);
	}
	
	public SearchForm(SearchForm<T> searchForm){
		setSearchByCriteriaLayout(searchForm.getSearchByCriteriaLayout());
		setSearchButton(searchForm.getSearchButton());
		setResultListLayout(searchForm.getResultListLayout());
	}
	
	public SearchForm(AbstractView view, String label){
		
		super(view, label, new VerticalLayout());		
		
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

	public Button getSearchButton() {
		return searchButton;
	}

	public void setSearchButton(Button searchButton) {
		this.searchButton = searchButton;
	}

	public ListSelect getResultListLayout() {
		return resultListLayout;
	}

	public void setResultListLayout(ListSelect resultListLayout) {
		this.resultListLayout = resultListLayout;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public Layout buildLayout(String mode){
		
		//get main layout
		VerticalLayout root = (VerticalLayout)getLayout();
		//get component validator
		ComponentValidator componentValidator = getComponentValidator();
		//get propertyManager
		PropertyManager propertyManager = getPropertyManager();
		//get access to DB
		DaoIntrfc dao = getDao();
		
		//remove all current components
		root.removeAllComponents();

		//define measurements of the components 
		String width = "180px", height = "-1px";
		
		searchByKeyLayout = new FormLayout();
		
		final TextField keySearchTF = new TextField(propertyManager.getLabelDtl("keySearch"));
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
		
		root.addComponent(searchByCriteriaLayout);
		
		root.addComponent(searchByObjectLayout);
		
		searchButton = new Button(propertyManager.getButtonDtl("search"));
		
		searchButton.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				searchByCriteriaResult = search();
				
				ArrayList<T> result = new ArrayList<T>();
				result.addAll(searchByKeyResult);
				result.addAll(searchByCriteriaResult);
				result.addAll(searchByObjectResult);
				
				Set<T> hs = new HashSet<T>(result);
				result.clear();
				result.addAll(hs);
				
				resultListLayout.addItems(result);
				
			}
		});
		
		root.addComponent(searchButton);
		root.setComponentAlignment(searchButton, Alignment.MIDDLE_RIGHT);
		
		root.addComponent(resultListLayout);
		
		return getLayout();
	};
	
	public void buildSearchByObject(){
		
		searchByObjectLayout = new HorizontalLayout();
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

	}
	
	private ArrayList<T> searchByKey(String value){
		return searchByKeyResult;
	}
	
	private ArrayList<T> searchByObject(String table, String idntfr){
		return searchByObjectResult;
	};
	
	public abstract ArrayList<T> search();
	
}
