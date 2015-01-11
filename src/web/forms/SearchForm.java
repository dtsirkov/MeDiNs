package web.forms;

import web.views.AbstractView;

import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.ListSelect;

public abstract class SearchForm extends Form {

	private static final long serialVersionUID = 1L;

	private FormLayout searchCriteria;
	private Button searchButton;
	private ListSelect resultList;
	
	public SearchForm(){}
	
	public SearchForm(FormLayout searchCriteria){
		setSearchCriteria(searchCriteria);
	}
	
	public SearchForm(SearchForm searchForm){
		setSearchCriteria(searchForm.getSearchCriteria());
		setSearchButton(searchForm.getSearchButton());
		setResultList(searchForm.getResultList());
	}
	
	public SearchForm(AbstractView view, String label, Layout layout){
		
		super(view, label, layout);
		
	}
	
	public FormLayout getSearchCriteria() {
		return searchCriteria;
	}
	public void setSearchCriteria(FormLayout searchCriteria) {
		this.searchCriteria = searchCriteria;
	}
	public Button getSearchButton() {
		return searchButton;
	}
	public void setSearchButton(Button searchButton) {
		this.searchButton = searchButton;
	}
	public ListSelect getResultList() {
		return resultList;
	}
	public void setResultList(ListSelect resultList) {
		this.resultList = resultList;
	}
	
	public Layout buildLayout(String mode){
		return getLayout();
	};
	
}
