package web.components.table.generated.components;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;

public class MyButton extends Button {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5063884137760745432L;

	/**
	 * Instantiates a new MyButton object.
	 * 
	 * @param themeResource Indicates the icon that is supposed to be shown instead. <br>
	 * If <code>null</code> the normal button will be shown. 
	 */
	public MyButton(final ThemeResource themeResource) {
		this.setImmediate(true);
		this.setStyleName("tableBtn");
		this.setWidth("-1px");
		this.setHeight("-1px");
		if (themeResource != null) {
			this.setCaption("");
			this.addStyleName("link");
			this.setIcon(themeResource);
		}
	}
	
}
