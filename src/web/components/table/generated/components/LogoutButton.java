package web.components.table.generated.components;

import web.MedinsUI;

import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.UI;

public abstract class LogoutButton extends MyButton {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8616061697550604698L;

	/**
	 * Instantiates a new LogoutButton object. When clicked the user is set to null 
	 * and all popped up web.components.table.generated.windows will be closed.
	 */
	public LogoutButton(final String userClassPath) {
		super(new ThemeResource("myVaadin/buttons/logout.png"));
		this.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = -6024482019178835620L;
			@Override
			public void buttonClick(final ClickEvent event) {
				((MedinsUI) UI.getCurrent()).removeActivePopupWindows();
				try {
					VaadinSession.getCurrent().setAttribute(Class.forName(userClassPath), null);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				MedinsUI.getCurrent().setContent(setContent());
			}
		});
	}

	/**
	 * Sets the content of the View to the intended View.
	 * 
	 * @return The component that is supposed to fill the View.
	 */
	public abstract Component setContent();
}
