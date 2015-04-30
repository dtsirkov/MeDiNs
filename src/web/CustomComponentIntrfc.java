package web;

import com.vaadin.ui.Layout;

public interface CustomComponentIntrfc {

	public Layout getLayout();
	public void setLayout(Layout layout);
	public String getLabel();
	public void setLabel(String label);
	public Layout buildLayout(String mode);
	public Layout viewLayout(String mode);

}
