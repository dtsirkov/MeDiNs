package web;

import java.util.ArrayList;

import property_pckg.PropertyManager;

import com.vaadin.annotations.Theme;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

import dao_classes.DaoIntrfc;

@SuppressWarnings("serial")
@Theme("medins")
public class ActivityView extends AbstractView implements View{

	public ActivityView(PropertyManager propertyManager, DaoIntrfc dao, Navigator navigator) {

		super(propertyManager, dao, navigator);
		setLabel("createPerson");

	}

	@Override
	public void enter(ViewChangeEvent event) {
		setCompositionRoot(buildViewLayout());
	}

	public Layout buildViewLayout(){

		//get propertyManager
		final PropertyManager propertyManager = getPropertyManager();
		//get access to DB
		final DaoIntrfc dao = getDao();	

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

		Label title = new Label(propertyManager.getLabelDtl("personCreate"));
		title.addStyleName("title");
		titleBar.addComponent(title);

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
		Panel detailspanel = new Panel(propertyManager.getLabelDtl("personCreateDetails"));
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

		final Button prevButton = new Button(propertyManager.getButtonDtl("prevButton"));
		final Button nextButton = new Button(propertyManager.getButtonDtl("nextButton"));
		final Button validateButton = new Button(propertyManager.getButtonDtl("validateButton"));
		buttonsLayout.addComponent(prevButton);
		buttonsLayout.addComponent(nextButton);

		// Let the details panel take as much space as possible and
		// have the selection tree to be as small as possible
		horlayout.setExpandRatio(detailspanel, 1);
		horlayout.setExpandRatio(menuContainer, 0);

		//////////////////////////////////////////////////////
		//Put in the application data and handle the UI logic

		final String validationMethod = "validatePerson";
		final String mode = "no_update";

		//put required steps in array
		final Form[] requiredSteps = {
				new PersonForm(this, "stepCreatePerson"), 
				new ContactForm(this, "stepCreateContact"), 
				new ValidationForm(this, "stepValidate")
		};

		/*
		Persons person = (Persons)dao.findById("Persons", "411515");
		requiredSteps[0].setData(person);

		Map<Object, List<Object>> hmPerson = new HashMap<Object, List<Object>>();
		List<Object> personsLs = new ArrayList<Object>();
		personsLs.add(person);
		hmPerson.put("persons", personsLs);
		List<Object> personAddressLinks = dao.findByExample(new PersonContactLink(), hmPerson);

		Enumerations enumeration;
		Contacts contact;
		for(Object personAddressLink : personAddressLinks){
			contact = ((PersonContactLink) personAddressLink).getContacts();
			enumeration = contact.getEnumerationsByActive();
			if(enumeration.getCode().equals("yes")){
				requiredSteps[1].setData(contact);
			}
		}
		 */

		final Form [] requiredStepsDisplay = new Form[requiredSteps.length + 1];	
		requiredStepsDisplay[0] = new Form(this, "requiredSteps");
		for(int i = 0; i < requiredSteps.length; i++){
			requiredStepsDisplay[i+1] = requiredSteps[i];
		}

		//put additional steps in array
		final Form[] optionalSteps = {};

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
			}
		}

		//put required and additional steps in array
		final Form[][] stepCategory = new Form[][] {
				requiredStepsDisplay,
				optionalStepsDisplay
		};

		//enable selection of initially validated steps
		final ArrayList<String> validatedSteps = new ArrayList<String>();
		validatedSteps.add((requiredSteps[0]).getLabel());
		validatedSteps.add((requiredStepsDisplay[0]).getLabel());
		for (int i = 0; i < optionalStepsDisplay.length; i++) {
			validatedSteps.add((optionalStepsDisplay[i]).getLabel());
		}

		//create a list of required steps' labels 
		final ArrayList<String> requiredStepsLabels = new ArrayList<String>();
		for	(int i = 0; i < requiredSteps.length; i++){
			requiredStepsLabels.add((requiredSteps[i]).getLabel());
		}
		//create a list of step categories' labels
		final ArrayList<String> stepCategoryLabels = new ArrayList<String>();
		stepCategoryLabels.add((requiredStepsDisplay[0]).getLabel());
		stepCategoryLabels.add((optionalStepsDisplay[0]).getLabel());

		//Add step categories as root items in the tree.
		for (int i = 0; i < stepCategory.length; i++) {
			String stepList = (stepCategory[i][0]).getLabel();
			menu.addItem(stepList);
			if (stepCategory[i].length == 1) {
				// The stepList has no steps so make it a leaf.
				menu.setChildrenAllowed(stepList, false);
			} else {
				// Add children (step) under the stepList.
				for (int j = 1; j < stepCategory[i].length; j++) {
					final String step = (stepCategory[i][j]).getLabel();
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
			private static final long serialVersionUID = 5548609446898735032L;
			public void itemClick(ItemClickEvent event) {
				String selectedItem = event.getItemId().toString();
				if (requiredStepsLabels.contains(selectedItem) && !validatedSteps.contains(selectedItem))
					Notification.show(propertyManager.getLabelDtl("stepNotValidated"));
			}
		});

		//select an item from menu
		menu.addValueChangeListener(new Property.ValueChangeListener() {
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
								stepLabel = (validationForm).getLabel();
								if(value.equals(stepLabel))
								{	
									validationForm.setData(requiredSteps);
									customComponent = new ValidationForm((ValidationForm)validationForm);
									buttonsLayout.addComponent(validateButton);
								}else{
									customComponent = requiredSteps[i];
									buttonsLayout.addComponent(prevButton);
									buttonsLayout.addComponent(nextButton);	
								}
								customComponent.buildFormLayout(mode);

								//add stepTitleLayout
								stepTitle.setValue(value);					
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
							}
						}	
					}
				}
			}
		});

		//manage nextButton
		nextButton.addClickListener(new Button.ClickListener() {	
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
				}

				menu.select(nextStep);	
			}
		});

		//manage prevButton 
		prevButton.addClickListener(new Button.ClickListener() {
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
			public void buttonClick(ClickEvent event) {
				ValidationClass validationClass = new ValidationClass(validationMethod, requiredSteps, optionalSteps);
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
			}
		});

		return root;

	}

}