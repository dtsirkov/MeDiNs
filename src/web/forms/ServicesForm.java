package web.forms;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import web.beans.ServiceBean;
import web.components.table.generated.autogenerate.GenerateTable;
import web.views.AbstractView;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

import database.dao.DaoIntrfc;
import database.pojo.Services;

public class ServicesForm extends Form {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private VerticalLayout tableVerticalLayout;
	/**
	 * 
	 */
	private static final long serialVersionUID = 7630409571364397734L;
	/**
	 * The constructor should first build the main web.components.table.generated.layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public ServicesForm() {

		buildMainLayout();
		setCompositionRoot(mainLayout);

	}

	public ServicesForm(AbstractView view, String label) {

		super(view, label, new AbsoluteLayout());
		setCompositionRoot(getLayout());

	}

	public Layout buildLayout(String mode) {

		//get access to DB
		DaoIntrfc dao = getDao();	

		//build main layout
		Layout absoluteLayout = buildMainLayout();

		ServiceBean.setTypeEnum(dao.getEnumeration("service type"));

		BeanItemContainer<ServiceBean> serviceBeanItemContainer = new BeanItemContainer<ServiceBean>(ServiceBean.class);
		if(getData() != null){
			@SuppressWarnings("unchecked")
			Set<Services> services = (Set<Services>)getData();
			Iterator<Services> iterator = services.iterator();
			Services service;
			while(iterator.hasNext()){
				service = iterator.next();
				if(service != null){
					ServiceBean serviceBean = new ServiceBean(service);
					serviceBeanItemContainer.addItem(serviceBean);
				}
				dao.evict(service);
			}
			serviceBeanItemContainer.sort(new Object[] { "name" }, new boolean[] { true });
		}

		EditServiceForm editServiceForm;
		editServiceForm = new EditServiceForm(getView(), "editService");
		setLayout(absoluteLayout);
		setCompositionRoot(absoluteLayout);

		GenerateTable table = new GenerateTable(ServiceBean.class, serviceBeanItemContainer, editServiceForm);
		table.setWidth("100%");
		tableVerticalLayout.addComponent(table);
		tableVerticalLayout.setComponentAlignment(table, Alignment.MIDDLE_CENTER);

		this.setData(serviceBeanItemContainer);

		return absoluteLayout;
	}

	public Layout viewLayout(String mode) {

		//get access to DB
		DaoIntrfc dao = getDao();	

		//build main layout
		VerticalLayout absoluteLayout = new VerticalLayout();
		absoluteLayout.setWidth("600px");

		ServiceBean.setTypeEnum(dao.getEnumeration("service type"));

		BeanItemContainer<ServiceBean> serviceBeanItemContainer = new BeanItemContainer<ServiceBean>(ServiceBean.class);
		if(getData() != null){
			@SuppressWarnings("unchecked")
			Set<Services> services = (Set<Services>)getData();
			Iterator<Services> iterator = services.iterator();
			Services service;
			while(iterator.hasNext()){
				service = iterator.next();
				if(service != null){
					ServiceBean serviceBean = new ServiceBean(service);
					serviceBeanItemContainer.addItem(serviceBean);
				}
			}
			serviceBeanItemContainer.sort(new Object[] { "name" }, new boolean[] { true });
		}

		GenerateTable table = new GenerateTable(ServiceBean.class, serviceBeanItemContainer, null);
		table.setWidth("100%");
		absoluteLayout.addComponent(table);
		absoluteLayout.setComponentAlignment(table, Alignment.MIDDLE_CENTER);

		return absoluteLayout;
	}

	@Override
	public boolean process(HashMap<String, Form> steps) {

		@SuppressWarnings("unchecked")
		BeanItemContainer<ServiceBean> serviceBeanItemContainer = (BeanItemContainer<ServiceBean>)getData();

		List<ServiceBean> itemIdsLs = serviceBeanItemContainer.getItemIds();
		Iterator<ServiceBean> iterator = itemIdsLs.iterator();
		Services service;
		Set<Services> services = new HashSet<Services>(0);
		while(iterator.hasNext()){
			service = iterator.next().createPersistenceObject();
			services.add(service);
		}

		steps.get("stepServices").setData(services);		

		return true;
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("1000px");
		mainLayout.setHeight("350px");

		// tableVerticalLayout
		tableVerticalLayout = new VerticalLayout();
		tableVerticalLayout.setImmediate(false);
		tableVerticalLayout.setWidth("1000px");
		tableVerticalLayout.setHeight("320px");
		tableVerticalLayout.setMargin(false);
		mainLayout.addComponent(tableVerticalLayout, "top:20.0px;left:0.0px;");

		return mainLayout;
	}

}