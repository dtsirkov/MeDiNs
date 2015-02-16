package web.views;

import java.util.ArrayList;
import java.util.HashMap;

import org.hibernate.Transaction;


import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.MouseEvents;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import dao.classes.DaoIntrfc;
import web.classes.ComponentValidator;
import web.classes.Domain;
import web.classes.PropertyManager;
import web.classes.ValidationClass;
import web.forms.Form;
import web.forms.SearchForm;
import web.forms.ValidationForm;

public abstract class AbstractActivityView extends AbstractView {

	private static final long serialVersionUID = 1L;

	private Form[] requiredSteps = new Form[]{};
	private Form[] optionalSteps = new Form[]{};
	private String mode = "view";
	private String validationMethod;
	private Domain domain;

	public AbstractActivityView(){};

	public AbstractActivityView(UI ui){

		super(ui);

	};

	public AbstractActivityView(AbstractActivityView abstractActivityView){
		setRequiredSteps(abstractActivityView.getRequiredSteps());
		setOptionalSteps(abstractActivityView.getOptionalSteps());
		setMode(abstractActivityView.getMode());
		setValidationMethod(abstractActivityView.getValidationMethod());
		setDomain(abstractActivityView.getDomain());
	}

	public Form[] getRequiredSteps() {
		return requiredSteps;
	}
	public void setRequiredSteps(Form[] requiredSteps) {
		this.requiredSteps = requiredSteps;
	}
	public Form[] getOptionalSteps() {
		return optionalSteps;
	}
	public void setOptionalSteps(Form[] optionalSteps) {
		this.optionalSteps = optionalSteps;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getValidationMethod() {
		return validationMethod;
	}
	public void setValidationMethod(String validationMethod) {
		this.validationMethod = validationMethod;
	}

	public Domain getDomain() {
		return domain;
	}

	public void setDomain(Domain domain) {
		this.domain = domain;
	}

	public Layout buildLayout(){

		//get propertyManager
		final PropertyManager propertyManager = getPropertyManager();
		//get access to DB
		final DaoIntrfc dao = getDao();	
		//begin transaction
		dao.getTransaction().rollback();
		final Transaction transaction = dao.getTransaction();

		//create main layout
		final VerticalLayout root = new VerticalLayout();
		//create component validater
		final ComponentValidator customValidator = new ComponentValidator(getPropertyManager());

		// Create the root layout (VerticalLayout is actually the default).
		root.addStyleName("personcreate");
		root.setSizeFull();

		// Title bar
		HorizontalLayout titleBar = new HorizontalLayout();
		titleBar.setWidth("100%");
		root.addComponent(titleBar);

		final String label = getLabel();
		final Label title = new Label(propertyManager.getLabelDtl(label));
		title.addStyleName("title");
		titleBar.addComponent(title);

		// home image
		Embedded homeEm = new Embedded("", new ThemeResource("images/home.png"));
		homeEm.setHeight("50px");
		titleBar.addComponent(homeEm);
		titleBar.setComponentAlignment(homeEm, Alignment.MIDDLE_RIGHT);

		// Horizontal layout with selection tree on the left and 
		// a details panel on the right.
		HorizontalLayout horlayout = new HorizontalLayout();
		horlayout.setSizeFull();
		horlayout.setSpacing(true);
		root.addComponent(horlayout);
		root.setExpandRatio(horlayout, 1);

		// Layout for the menu area. Wrap the menu in a Panel to allow
		// scrollbar.
		Panel menuContainer = new Panel(propertyManager.getLabelDtl("stepList"));
		menuContainer.addStyleName("menucontainer");
		menuContainer.addStyleName("light"); // No border
		menuContainer.setWidth("-1px"); // Undefined width
		menuContainer.setHeight("100%");
		//menuContainer.getContent().setWidth("-1px"); // Undefined width
		horlayout.addComponent(menuContainer);

		// A menu tree, fill it later.
		final Tree menu = new Tree();
		menu.setSizeUndefined();
		menu.setImmediate(true);
		menuContainer.setContent(menu);

		// A panel for the main view area on the right side
		Panel detailspanel = new Panel(propertyManager.getLabelDtl("details"));
		detailspanel.addStyleName("detailspanel");
		detailspanel.addStyleName("light"); // No borders
		detailspanel.setSizeFull();
		horlayout.addComponent(detailspanel);

		// Have a vertical layout in the Details panel.
		final VerticalLayout detailslayout = new VerticalLayout();
		detailslayout.setImmediate(true);
		detailslayout.setSizeFull();
		detailspanel.setContent(detailslayout);


		// Put some stuff in the Details view.
		final VerticalLayout detailsbox = new VerticalLayout();
		detailsbox.setSizeUndefined();
		detailsbox.setMargin(new MarginInfo(true, false, true, false));

		final Label startActivity = new Label(propertyManager.getLabelDtl("startActivity"));
		startActivity.addStyleName("stepTitle");
		startActivity.setSizeUndefined(); 
		detailsbox.addComponent(startActivity);
		detailslayout.addComponent(detailsbox);
		detailslayout.setComponentAlignment(detailsbox, Alignment.MIDDLE_CENTER);

		//create step title layout
		final HorizontalLayout stepTitleLayout = new HorizontalLayout();
		stepTitleLayout.setMargin(new MarginInfo(true, false, true, false));

		final Label stepTitle = new Label();
		stepTitle.setSizeUndefined();
		stepTitle.addStyleName("stepTitle");		
		stepTitleLayout.addComponent(stepTitle);
		stepTitleLayout.setComponentAlignment(stepTitle, Alignment.MIDDLE_CENTER);

		//create custom component layout
		final VerticalLayout customComponentLayout = new VerticalLayout();
		customComponentLayout.setMargin(new MarginInfo(false, false, false, false));

		//create button layout
		final HorizontalLayout buttonsLayout = new HorizontalLayout();
		buttonsLayout.setMargin(new MarginInfo(true, false, true, false));

		buttonsLayout.setSpacing(true);
		buttonsLayout.setImmediate(true);

		final Button prevButton = new Button(propertyManager.getButtonDtl("prev"));
		final Button nextButton = new Button(propertyManager.getButtonDtl("next"));
		final Button validateButton = new Button(propertyManager.getButtonDtl("validate"));
		final Button domainSelectionButton = new Button(propertyManager.getButtonDtl("domainSelection"));
		final Button activitySelectionButton = new Button(propertyManager.getButtonDtl("activitySelection"));

		buttonsLayout.addComponent(prevButton);
		buttonsLayout.addComponent(nextButton);

		// Let the details panel take as much space as possible and
		// have the selection tree to be as small as possible
		horlayout.setExpandRatio(detailspanel, 1);
		horlayout.setExpandRatio(menuContainer, 0);

		//////////////////////////////////////////////////////
		//Put in the application data and handle the UI logic

		final String validationMethod = getValidationMethod();
		final String mode = getMode();

		//put required steps in array
		String requiredStepLabel;
		final HashMap<String, Form> hmRequiredSteps = new HashMap<String, Form>();
		final Form[] requiredSteps = getRequiredSteps();
		final Form [] requiredStepsDisplay = new Form[requiredSteps.length + 1];	

		requiredStepsDisplay[0] = new Form(this, "requiredSteps");

		for(int i = 0; i < requiredSteps.length; i++){
			requiredStepsDisplay[i+1] = requiredSteps[i];
			requiredStepLabel = requiredSteps[i].getLabel();
			hmRequiredSteps.put(requiredStepLabel, requiredSteps[i]);
		}

		//put additional steps in array
		String optionalStepLabel;
		final HashMap<String, Form> hmOptionalSteps = new HashMap<String, Form>();
		final Form[] optionalSteps = getOptionalSteps();

		final Form [] optionalStepsDisplay;
		if(optionalSteps.length == 0){
			optionalStepsDisplay = new Form[2];
			optionalStepsDisplay[0] = new Form(this, "optionalSteps");
			optionalStepsDisplay[1] = new Form(this, "noOptionalSteps");
		}else{
			optionalStepsDisplay = new Form[optionalSteps.length + 1];
			optionalStepsDisplay[0] = new Form(this, "optionalSteps");
			for(int i = 0; i < optionalSteps.length; i++){
				optionalStepsDisplay[i+1] = optionalSteps[i];
				optionalStepLabel = optionalSteps[i].getLabel();
				hmOptionalSteps.put(optionalStepLabel, optionalSteps[i]);
			}
		}

		//put required and additional steps in array
		final Form[][] stepCategory = new Form[][] {
				requiredStepsDisplay,
				optionalStepsDisplay
		};

		//enable selection of initially validated steps
		final ArrayList<String> validatedSteps = new ArrayList<String>();
		validatedSteps.add(requiredSteps[0].getLabel());
		validatedSteps.add(requiredStepsDisplay[0].getLabel());
		for (int i = 0; i < optionalStepsDisplay.length; i++) {
			validatedSteps.add(optionalStepsDisplay[i].getLabel());
		}

		//create a list of required steps' labels 
		final ArrayList<String> requiredStepsLabels = new ArrayList<String>();
		for	(int i = 0; i < requiredSteps.length; i++){
			requiredStepsLabels.add(requiredSteps[i].getLabel());
		}
		//create a list of step categories' labels
		final ArrayList<String> stepCategoryLabels = new ArrayList<String>();
		stepCategoryLabels.add(requiredStepsDisplay[0].getLabel());
		stepCategoryLabels.add(optionalStepsDisplay[0].getLabel());

		//Add step categories as root items in the tree.
		for (int i = 0; i < stepCategory.length; i++) {
			String stepList = stepCategory[i][0].getLabel();
			menu.addItem(stepList);
			menu.setItemCaption(stepList, propertyManager.getLabelDtl(stepList));
			if (stepCategory[i].length == 1) {
				// The stepList has no steps so make it a leaf.
				menu.setChildrenAllowed(stepList, false);
			} else {
				// Add children (step) under the stepList.
				for (int j = 1; j < stepCategory[i].length; j++) {
					final String step = stepCategory[i][j].getLabel();
					menu.setItemCaption(step, propertyManager.getLabelDtl(step));
					// Add the item as a regular item.
					menu.addItem(step);
					// Set it to be a child.
					menu.setParent(step, stepList);
					// Make the steps look like leaves.
					menu.setChildrenAllowed(step, false);
				}
				// Expand the subtree.
				//menu.expandItemsRecursively(stepList);
			}
		}

		//colour validated, not validated steps and step categories
		menu.addStyleName("stepStyle");
		Tree.ItemStyleGenerator itemStyleGenerator = new Tree.ItemStyleGenerator() {
			private static final long serialVersionUID = -7016120138582433243L;
			public String getStyle(Tree source, Object itemId) {
				String itemLabel = itemId.toString();
				String style = "notValidated";
				if (stepCategoryLabels.contains(itemLabel)){
					style =  "category";
				} else if (validatedSteps.contains(itemLabel)){
					style = "validated";
				}
				return style;
			}
		}; 
		menu.setItemStyleGenerator(itemStyleGenerator);

		//add warning message when not validated step is selected
		menu.addItemClickListener(new ItemClickEvent.ItemClickListener() {

			private static final long serialVersionUID = 1L;

			public void itemClick(ItemClickEvent event) {
				String selectedItem = event.getItemId().toString();
				if (requiredStepsLabels.contains(selectedItem) && !validatedSteps.contains(selectedItem))
					Notification.show(propertyManager.getLabelDtl("stepNotValidated"));
			}
		});

		//select an item from menu
		menu.addValueChangeListener(new Property.ValueChangeListener() {

			private static final long serialVersionUID = 1L;
			private boolean searchFormFlag = true;
			Object previous = requiredSteps[0];
			public void valueChange(ValueChangeEvent event) {
				//CustomComponent validationForm;
				Form customComponent, validationForm; 
				if (event.getProperty() != null && event.getProperty().getValue() != null){

					String value = event.getProperty().getValue().toString();	

					//manage selection of not validated step
					if (!validatedSteps.contains(value)) 
						menu.setValue(previous);
					else
						previous = menu.getValue();

					//a category is selected 
					if ((stepCategory[0][0]).getLabel().equals(value) || 
							(stepCategory[1][0]).getLabel().equals(value)){

						detailsbox.removeAllComponents();
						Label noStepSelected = new Label(propertyManager.getLabelDtl("noStepSelected"));
						noStepSelected.addStyleName("stepTitle");
						detailsbox.addComponent(noStepSelected);

					}else{
						//a step is selected
						String stepLabel;
						for(int i = 0;  i < requiredSteps.length; i++){
							stepLabel = (requiredSteps[i]).getLabel();
							//if the previous step is validated and the next step is reachable - add next step to detailsbox
							if (stepLabel.equals(value) && validatedSteps.contains((requiredSteps[i]).getLabel())) {

								detailsbox.removeAllComponents();
								buttonsLayout.removeAllComponents();

								//this is the last step in the requiredSteps array
								validationForm  = requiredSteps[requiredSteps.length - 1];
								stepLabel = validationForm.getLabel();
								if(value.equals(stepLabel))
								{	
									customComponent = new ValidationForm((ValidationForm)validationForm);
									customComponent.setData(requiredSteps);
									buttonsLayout.addComponent(validateButton);
								}else{
									customComponent = requiredSteps[i];
									buttonsLayout.addComponent(prevButton);
									buttonsLayout.addComponent(nextButton);	
								}
								customComponent.buildLayout(mode);



								if(!(customComponent instanceof SearchForm) || searchFormFlag){

									if(customComponent instanceof SearchForm)
										searchFormFlag = false;

									//add stepTitleLayout
									stepTitle.setValue(propertyManager.getLabelDtl(value));					
									detailsbox.addComponent(stepTitleLayout);
									detailsbox.setComponentAlignment(stepTitleLayout, Alignment.TOP_CENTER);

									//add customComponentLayout
									customComponent.setSizeUndefined();
									customComponentLayout.removeAllComponents();
									customComponentLayout.addComponent(customComponent);
									customComponentLayout.setComponentAlignment(customComponent, Alignment.MIDDLE_CENTER);	
									detailsbox.addComponent(customComponentLayout);
									detailsbox.setComponentAlignment(customComponentLayout, Alignment.MIDDLE_CENTER);

									//add buttonsLayout 
									detailsbox.addComponent(buttonsLayout);
									detailsbox.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_CENTER);

								}else{

									Label noStepSelected = new Label(propertyManager.getLabelDtl("noReExecution"));
									noStepSelected.addStyleName("stepTitle");
									detailsbox.addComponent(noStepSelected);

								}
							}
						}	
					}
				}
			}
		});

		//manage nextButton
		nextButton.addClickListener(new Button.ClickListener() {	

			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				Form currentComponent = (Form)customComponentLayout.getComponent(0);
				String nextStep;
				int stepPosition = 0;
				boolean isValid = false;
				for (int i = 0; i < requiredSteps.length; i++) {	
					if (currentComponent.equals(requiredSteps[i])) {
						stepPosition = i;
						isValid = customValidator.validate(currentComponent, "EmptyValueException");
					}
				}
				int nextStepPosition;
				if (stepPosition < requiredSteps.length - 1 && isValid){
					nextStepPosition = stepPosition + 1;
				}else{
					nextStepPosition = stepPosition;
				}
				nextStep = (requiredSteps[nextStepPosition]).getLabel();

				if(isValid && !validatedSteps.contains(nextStep)){
					validatedSteps.add(nextStep);
					currentComponent.setValidated(isValid);
					currentComponent.process(hmRequiredSteps);
				}

				menu.select(nextStep);	
			}
		});

		//manage prevButton 
		prevButton.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				Component currentComponent = customComponentLayout.getComponent(0);
				String prevStep;
				int stepPosition = 0;
				for (int i = 0; i < requiredSteps.length; i++) {	
					if (currentComponent.equals(requiredSteps[i])) {
						stepPosition = i;
					}
				}
				int prevStepPosition = 0;
				if (stepPosition - 1 > 0){
					prevStepPosition = stepPosition - 1;
				}else{
					prevStepPosition = 0;
				}
				prevStep = (requiredSteps[prevStepPosition]).getLabel();
				CustomComponent prevComponent = requiredSteps[prevStepPosition];
				prevComponent.setComponentError(null);
				menu.select(prevStep);
			}
		});

		//manage validateButton 
		validateButton.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {

				ValidationClass validationClass = new ValidationClass(validationMethod, hmRequiredSteps, hmOptionalSteps);
				validationClass.setDao(dao);
				String validationResult = "not_validated";
				if(validationClass.validate(mode)){
					validationResult = "validated";
				}else{
					validatedSteps.remove((requiredSteps[requiredSteps.length-1]).getLabel());
				}
				menu.expandItemsRecursively((requiredStepsDisplay[0]).getLabel());
				menu.unselect((requiredStepsDisplay[requiredSteps.length]).getLabel());
				menu.setReadOnly(true);
				detailsbox.removeAllComponents();
				Label result = new Label(propertyManager.getLabelDtl(validationResult));
				result.addStyleName("stepTitle");
				detailsbox.addComponent(result);

				buttonsLayout.removeAllComponents();
				buttonsLayout.addComponent(domainSelectionButton);
				buttonsLayout.addComponent(activitySelectionButton);
				detailsbox.addComponent(buttonsLayout);
				detailsbox.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_CENTER);
			}
		});

		final Navigator navigator = getNavigator();

		domainSelectionButton.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				navigator.navigateTo("domainSelectionView");
			}
		});

		activitySelectionButton.addClickListener(new Button.ClickListener() {

			private static final long serialVersionUID = 1L;

			public void buttonClick(ClickEvent event) {
				navigator.navigateTo(domain.getLabel());
			}
		});

		homeEm.addClickListener(new MouseEvents.ClickListener() {

			private static final long serialVersionUID = 1L;

			@Override
			public void click(com.vaadin.event.MouseEvents.ClickEvent event) {
				transaction.rollback();
				navigator.navigateTo("domainSelectionView");	
			}
		});

		setLayout(root);

		return root;

	}

}
