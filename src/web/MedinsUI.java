package web;

import javax.servlet.annotation.WebServlet;

import property_pckg.ManageProperty;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

//mnogo e smotan tozi github

@SuppressWarnings("serial")
@Theme("medins")
public class MedinsUI extends UI {

	@WebServlet(value = "/*", asyncSupported = true)
	@VaadinServletConfiguration(productionMode = false, ui = MedinsUI.class)
	public static class Servlet extends VaadinServlet {
	}

	@Override
	protected void init(VaadinRequest request) {

		String language = request.getLocale().getLanguage();

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
		Tree menu = new Tree();
		menu.setSizeUndefined();
		menuContainer.setContent(menu);

		// A panel for the main view area on the right side
		Panel detailspanel = new Panel(ManageProperty.getLabelDtl("personCreateDetails" + "_" + language));
		detailspanel.addStyleName("detailspanel");
		detailspanel.addStyleName("light"); // No borders
		detailspanel.setSizeFull();
		horlayout.addComponent(detailspanel);

		// Have a vertical layout in the Details panel.
		VerticalLayout detailslayout = new VerticalLayout();
		detailslayout.setSizeFull();
		detailspanel.setContent(detailslayout);

		// Put some stuff in the Details view.
		VerticalLayout detailsbox = new VerticalLayout();
		detailsbox.setSizeUndefined();
		/*
		final Label question = new Label("Where is the cat?");
		question.setSizeUndefined(); 
		detailsbox.addComponent(question);
		final Label location = new Label("I don't know! Tell me!");
		location.setSizeUndefined(); 
		detailsbox.addComponent(location);
		*/
		final PersonForm personForm = new PersonForm(request);
		personForm.setSizeUndefined(); 
		detailsbox.addComponent(personForm);
		detailslayout.addComponent(detailsbox);
		detailslayout.setComponentAlignment(detailsbox, Alignment.TOP_CENTER);
		
		VerticalLayout buttonsLayout = new VerticalLayout();
		Button nextButton = new Button(ManageProperty.getButtonDtl("nextButton" + "_" + language));
		nextButton.setSizeUndefined();
		buttonsLayout.addComponent(nextButton);
		detailslayout.addComponent(buttonsLayout);
		detailslayout.setComponentAlignment(buttonsLayout, Alignment.TOP_CENTER);

		// Let the details panel take as much space as possible and
		// have the selection tree to be as small as possible
		horlayout.setExpandRatio(detailspanel, 1);
		horlayout.setExpandRatio(menuContainer, 0);

		//////////////////////////////////////////////////////
		//Put in the application data and handle the UI logic

		final Object[][] stepCategoryArray = new Object[][] {
				new Object[] {ManageProperty.getLabelDtl("requiredSteps" + "_" + language),
						ManageProperty.getLabelDtl("personCreateMain" + "_" + language)},

						new Object[] {ManageProperty.getLabelDtl("optionalSteps" + "_" + language),   
						ManageProperty.getLabelDtl("noOptionalSteps" + "_" + language)}
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

					// Make the moons look like leaves.
					menu.setChildrenAllowed(step, false);
				}

				// Expand the subtree.
				//menu.expandItemsRecursively(stepList);
			}
		}

		menu.addValueChangeListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty() != null &&
						event.getProperty().getValue() != null) {
					//location.setValue("The cat is in " + event.getProperty().getValue());
				}
			}
		});
		menu.setImmediate(true);

		//PersonForm personForm = new PersonForm(request);

		//VerticalLayout content = new VerticalLayout();

		//content.addComponent(personForm);

		setContent(root);

		/*
		CssLayout layout = new CssLayout();
		layout.setWidth("100%");
		//layout.addStyleName("flexwrap");

		Label label = new Label("Test");
		label.addStyleName("title");
		//layout.addComponent(label);
		layout.addComponent(personForm);

		setContent(layout);
		 */
	}

}