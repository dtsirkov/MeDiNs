package components;

import com.vaadin.server.ThemeResource;

/**
 * Description: Specialization from {@link MyButton} which is responsible for saving<br>
 * the entities. Each entity shall be saved with respect to its own structure.<br>
 * For this reason the save action is considered as an abstract that will force the initialization to 
 * implement it.<br>
 * Filename: SaveButton.java <br>
 */
public class SaveButton extends MyButton {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5114249737817111534L;

	/**
	 * Instantiates a new SaveButton object.
	 * 
	 * @param isEdit Indicates if the save button is in an edit form or a list.
	 */
	public SaveButton(final boolean isEdit) {
		super(new ThemeResource("myVaadin/buttons/save.png"));
//		this.addClickListener(new ClickListener() {
//			private static final long serialVersionUID = -7410882591286678401L;
//
//			@Override
//			public void buttonClick(ClickEvent event) {
//				saveAction();
//			}
//		});
	}
	
//	/**
//	 * Does the save action. This code shall be implemented by the user anywhere that it is initialized.
//	 * 
//	 * @return Returns the ClickListener specific to the initialization of objects of this type.
//	 */
//	public abstract void saveAction();
	
}
