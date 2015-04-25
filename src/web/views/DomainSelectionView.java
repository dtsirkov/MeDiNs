package web.views;

import java.util.ArrayList;

import web.MedinsUI;
import web.classes.ComponentValidator;
import web.classes.Domain;
import web.classes.PropertyManager;
import web.components.Header;
import web.forms.DomainSelectionForm;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import dao.classes.DaoIntrfc;

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

		//create main layout
		final VerticalLayout root = new VerticalLayout();
		//create component validater
		final ComponentValidator customValidator = new ComponentValidator(getPropertyManager());

		// Create the root layout (VerticalLayout is actually the default).
		root.addStyleName("personcreate");
		root.setSizeFull();

		Header header=new Header(getUI());
		root.addComponent(header);
		root.setComponentAlignment(header,Alignment.TOP_RIGHT);

		Label title = new Label("Select Activity group to start an activity");
		title.addStyleName("title");
		root.addComponent(title);
		
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

		root.addComponent(grid);
		root.setComponentAlignment(grid, Alignment.MIDDLE_CENTER);
		root.setExpandRatio(grid, 1);

		return root;

	}

	public ArrayList<Domain> getDomainList() {
		return domainList;
	}

	public void setDomainList(ArrayList<Domain> domainList) {
		this.domainList = domainList;
	}

}