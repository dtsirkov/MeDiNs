package web;

import com.vaadin.ui.Layout;

public interface CustomComponentIntrfc {

	public Layout getLayout();
	public void setLayout(Layout layout);
	public Layout buildFormLayout(String mode);

}
