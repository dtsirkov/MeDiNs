package components;

import table.TableInfo;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.VerticalLayout;

public abstract class MyImport extends CustomComponent {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4020534777141098215L;

	/** The main layout. */
	private VerticalLayout mainLayout;

	/** The cancel btn. */
	private CancelButton cancelBtn;

	/** The actions layout. */
	private HorizontalLayout actionsLayout;

	/** The dynamic layout. */
	private Layout dynamicLayout;

	/**
	 * Instantiates a new MyEdit object.
	 * 
	 * @param tableInfo
	 *            The table info
	 */
	public MyImport(final TableInfo tableInfo) {
		mainLayout = new VerticalLayout();
		mainLayout.setSizeFull();

		dynamicLayout = generateContent();
		mainLayout.addComponent(dynamicLayout);
		mainLayout.setExpandRatio(dynamicLayout, 1.0f);
		mainLayout.setComponentAlignment(dynamicLayout, Alignment.MIDDLE_CENTER);

		generateActionsLayout();
		
		setCompositionRoot(mainLayout);
		setSizeFull();
	}

	/**
	 * Generate actions layout.
	 */
	private void generateActionsLayout() {
		actionsLayout = new HorizontalLayout();
		actionsLayout.setMargin(new MarginInfo(false, true, true, true));
		cancelBtn = new CancelButton(true);
		actionsLayout.addComponent(cancelBtn);
		mainLayout.addComponent(actionsLayout);
		mainLayout.setComponentAlignment(actionsLayout, Alignment.BOTTOM_LEFT);
	}

	/**
	 * Generate components.
	 * 
	 * @return The layout
	 */
	public abstract Layout generateContent();

}
