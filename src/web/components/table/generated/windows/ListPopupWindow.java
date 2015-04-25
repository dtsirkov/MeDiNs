package web.components.table.generated.windows;

import web.components.table.generated.MyTable;
import web.components.table.generated.TableInfo;
import web.components.table.generated.layout.ListPopupLayout;

import com.vaadin.ui.Window;

/**
 * Description: The List pop-up window.<br>
 * <br>
 * Filename: ListPopupWindow.java <br>
 */
public class ListPopupWindow extends Window {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -328730814145776633L;

	/**
	 * Instantiates a new ListPopupWindow object.
	 * @param osbTable
	 *            The osb web.components.table.generated
	 */
	public ListPopupWindow(final MyTable osbTable) {
		generalSettings(osbTable.getTableInfo());
		
		setContent(new ListPopupLayout(osbTable, null));
	}
	
	/**
	 * General settings.
	 * @param tableInfo
	 *            The web.components.table.generated info
	 */
	private void generalSettings(final TableInfo tableInfo) {
		center();
		setCaption(tableInfo.getPopupEditCaption());
		setWidth(tableInfo.getPopupEditWidth(), Unit.PIXELS);
		setHeight(tableInfo.getPopupEditHeight(), Unit.PIXELS);
	}
}
