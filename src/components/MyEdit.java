package components;


import table.TableInfo;
import ui.MyUI;
import web.classes.ComponentValidator;
import web.forms.Form;

import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class MyEdit extends CustomComponent {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4020534777141098215L;

	/** The txt id. */
	private TextField txtId;

	/** The main layout. */
	private VerticalLayout mainLayout;

	/** The save btn. */
	private MyButton saveBtn;

	/** The cancel btn. */
	private CancelButton cancelBtn;

	/** The actions layout. */
	private HorizontalLayout actionsLayout;

	/** The dynamic layout. */
	private Form editForm;

	/**
	 * Instantiates a new MyEdit object.
	 * 
	 * @param tableInfo
	 *            The table info
	 */
	public MyEdit(final TableInfo tableInfo, final Object itemId) {
		mainLayout = new VerticalLayout();
		mainLayout.setSizeFull();
		mainLayout.setSpacing(true);
		mainLayout.setMargin(true);
		txtId = new TextField();
		txtId.setEnabled(false);
		txtId.setVisible(false);
		mainLayout.addComponent(txtId);

		BeanItem<?> beanItem = null;
		String mode = "";
		if (itemId != null) {
			beanItem = tableInfo.getBeanItemContainer().getItem(itemId);
			setIdentification(beanItem);
			tableInfo.getEditForm().setData(beanItem.getBean());
			mode = "update";
		}

		editForm = tableInfo.getEditForm();
		editForm.buildLayout(mode);
		editForm.setSizeUndefined();

		mainLayout.addComponent(editForm);
		mainLayout.setExpandRatio(editForm, 1.0f);
		mainLayout.setComponentAlignment(editForm, Alignment.MIDDLE_CENTER);

		generateActionsLayout();

		saveBtn.addClickListener(new ClickListener() {
			private static final long serialVersionUID = -1609549147129628107L;

			@Override
			public void buttonClick(final ClickEvent event) {
				try {
					saveAction(tableInfo);
					txtId.setReadOnly(true);
				} catch (Exception e) {
					e.printStackTrace();
					Notification.show(e.getMessage(), Type.ERROR_MESSAGE);
				}
			}
		});
		setCompositionRoot(mainLayout);
		setSizeFull();
	}

	/**
	 * Generate actions layout.
	 */
	private void generateActionsLayout() {
		actionsLayout = new HorizontalLayout();
		actionsLayout.setMargin(new MarginInfo(false, true, true, true));
		saveBtn = new MyButton(new ThemeResource("myVaadin/buttons/save.png"));
		actionsLayout.addComponent(saveBtn);
		cancelBtn = new CancelButton(true);
		actionsLayout.addComponent(cancelBtn);
		mainLayout.addComponent(actionsLayout);
		mainLayout.setComponentAlignment(actionsLayout, Alignment.BOTTOM_RIGHT);
	}

	/**
	 * Save action.
	 * 
	 * @param tableInfo
	 *            The table info
	 */
	public final void saveAction(TableInfo tableInfo){
		ComponentValidator componentValidator = new ComponentValidator(tableInfo.getPropertyManager());
		boolean isValid = componentValidator.validate(tableInfo.getEditForm(), "EmptyValueException");
		BeanItemContainer<?> bic = tableInfo.getBeanItemContainer();
		if(isValid){

			tableInfo.getTable().refreshRowCache();

			BeanItem<?> beanItem  = bic.addItem(tableInfo.getEditForm().getData());
			setIdentification(beanItem);
			((MyUI) UI.getCurrent()).removeActiveEditPopupWindow();

		}
	};

	/**
	 * Gets the identification.
	 * 
	 * @return The identification
	 */
	public final BeanItem<?> getIdentification() {
		if ("".equals(txtId.getValue())) {
			return null;
		}
		return (BeanItem<?>)txtId.getData();
	}

	/**
	 * Sets the identification.
	 * 
	 * @param value
	 *            The new identification
	 */
	public final void setIdentification(BeanItem<?> beanItem) {
		txtId.setReadOnly(false);
		txtId.setData(beanItem);
		txtId.setValue(beanItem + "");
		txtId.setReadOnly(true);
	}

}