package web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.WebServlet;

import pojo_classes.Enumerations;
import property_pckg.ManageProperty;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;

@SuppressWarnings("serial")
@Theme("medins")
public class MedinsUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = MedinsUI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {

		final String language = request.getLocale().getLanguage();

		// Create the root layout (VerticalLayout is actually the default).
		VerticalLayout root = new VerticalLayout();
		root.addStyleName("personcreate");
		root.setSizeFull();

		// Title bar
		HorizontalLayout titleBar = new HorizontalLayout();
		titleBar.setWidth("100%");
		root.addComponent(titleBar);

		Label title = new Label(ManageProperty.getLabelDtl("personCreate" + "_" + language));
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
		Panel menuContainer = new Panel(ManageProperty.getLabelDtl("stepList" + "_" + language));
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
		Panel detailspanel = new Panel(ManageProperty.getLabelDtl("personCreateDetails" + "_" + language));
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

		final Label startActivity = new Label(ManageProperty.getLabelDtl("startActivity" + "_" + language));
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

		final Button prevButton = new Button(ManageProperty.getButtonDtl("prevButton" + "_" + language));
		final Button nextButton = new Button(ManageProperty.getButtonDtl("nextButton" + "_" + language));
		buttonsLayout.addComponent(prevButton);
		buttonsLayout.addComponent(nextButton);

		// Let the details panel take as much space as possible and
		// have the selection tree to be as small as possible
		horlayout.setExpandRatio(detailspanel, 1);
		horlayout.setExpandRatio(menuContainer, 0);

		//////////////////////////////////////////////////////
		//Put in the application data and handle the UI logic

		// manage STEPS
		final HashMap <String, CustomComponent> stepsHM = new HashMap<String, CustomComponent>();
		stepsHM.put(ManageProperty.getLabelDtl("stepCreatePerson" + "_" + language), new PersonForm(request));
		stepsHM.put(ManageProperty.getLabelDtl("stepCreateContact" + "_" + language), new ContactForm(request));

		Object [] tmpStepsArray = stepsHM.keySet().toArray();
		final Object [] stepsArray = new Object [tmpStepsArray.length];
		for(int i = 0; i < tmpStepsArray.length; i++){
			stepsArray[tmpStepsArray.length - i - 1] = tmpStepsArray[i];
		}

		final Object [] requiredStepsArray = new Object[stepsArray.length + 1];	
		requiredStepsArray[0] = ManageProperty.getLabelDtl("requiredSteps" + "_" + language);
		for(int i = 0; i < stepsArray.length; i++){
			requiredStepsArray[i+1] = stepsArray[i];
		}

		final Object [] optionalStepsArray = new Object[] {
				ManageProperty.getLabelDtl("optionalSteps" + "_" + language),   
				ManageProperty.getLabelDtl("noOptionalSteps" + "_" + language)
		};

		final Object[][] stepCategoryArray = new Object[][] {
				requiredStepsArray,
				optionalStepsArray
		};

		//Add step categories as root items in the tree.
		for (int i = 0; i < stepCategoryArray.length; i++) {
			final String stepList = (String) (stepCategoryArray[i][0]);
			menu.addItem(stepList);

			if (stepCategoryArray[i].length == 1) {
				// The stepList has no steps so make it a leaf.
				menu.setChildrenAllowed(stepList, false);
			} else {
				// Add children (step) under the stepList.
				for (int j = 1; j < stepCategoryArray[i].length; j++) {
					final String step = (String) stepCategoryArray[i][j];

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


		//manage nextButton
		final int stepsHMSize = stepsHM.size();	
		nextButton.addClickListener(new Button.ClickListener() {
			@Override		
			public void buttonClick(ClickEvent event) {
				CustomComponent currentComponent = (CustomComponent)customComponentLayout.getComponent(0);
				String nextStep;
				int stepPosition = 0;
				boolean isValid = false;
				for (Map.Entry<String, CustomComponent> entry : stepsHM.entrySet()) {	
					if (currentComponent.equals(entry.getValue())) {
						String currentStep = entry.getKey();
						for(int i = 0; i < stepsArray.length; i++){
							if(stepsArray[i].equals(currentStep)){
								stepPosition = i;
							}
						}
						isValid = CustomValidator.validate(currentComponent);
					}
				}
				if (stepPosition < stepsHMSize - 1 && isValid){
					nextStep = (String)stepsArray[stepPosition + 1];
				}else{
					nextStep = (String)stepsArray[stepPosition];
				}
				menu.select(nextStep);	
			}
		});

		//manage prevButton 
		prevButton.addClickListener(new Button.ClickListener() {
			@Override		
			public void buttonClick(ClickEvent event) {
				Component currentComponent = customComponentLayout.getComponent(0);
				String prevStep;
				int stepPosition = 0;
				for (Map.Entry<String, CustomComponent> entry : stepsHM.entrySet()) {	
					if (currentComponent.equals(entry.getValue())) {
						String currentStep = entry.getKey();
						for(int i = 0; i < stepsArray.length; i++){
							if(stepsArray[i].equals(currentStep)){
								stepPosition = i;
							}
						}
					}
				}
				if (stepPosition - 1 > 0){
					prevStep = (String)stepsArray[stepPosition - 1];
				}else{
					prevStep = (String)stepsArray[0];
				}
				menu.select(prevStep);
			}
		});

		//select an item from menu
		menu.addValueChangeListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty() != null &&
						event.getProperty().getValue() != null){

					detailsbox.removeAllComponents();

					String value = event.getProperty().getValue().toString();					
					CustomComponent customComponent = stepsHM.get(value);	

					if(customComponent != null){

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
						if(value.equals(stepsArray[stepsArray.length - 1]))
						{
							nextButton.setCaption(ManageProperty.getButtonDtl("nextButton" + "_" + language));
						}else{
							nextButton.setCaption(ManageProperty.getButtonDtl("nextButton" + "_" + language));
						}				
						detailsbox.addComponent(buttonsLayout);
						detailsbox.setComponentAlignment(buttonsLayout, Alignment.BOTTOM_CENTER);
					}else{
						detailsbox.addComponent(new Label(ManageProperty.getLabelDtl("noStepSelected" + "_" + language)));
					}
				}
			}
		});

		setContent(root);

	}

}