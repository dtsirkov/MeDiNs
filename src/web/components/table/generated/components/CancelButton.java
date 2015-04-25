package web.components.table.generated.components;

import web.components.table.generated.ui.MyUI;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;

public class CancelButton extends MyButton {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -5114249737817111534L;

	/**
	 * Instantiates a new CancelButton object.
	 * 
	 * @param isEdit Indicates the type of active window.  
	 */
	public CancelButton(final boolean isEdit) {
		super(new ThemeResource("myVaadin/buttons/notSave.png"));
		this.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = -9132805767679652865L;
			@Override
			public void buttonClick(final ClickEvent event) {
				if (isEdit) {
					((MyUI) UI.getCurrent()).removeActiveEditPopupWindow();
				} else {
					((MyUI) UI.getCurrent()).removeActiveListPopupWindow();
				}
			}
		});
	}
	
}
