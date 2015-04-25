package windows;

import layout.ListPopupLayout;
import table.MyTable;
import table.TableInfo;

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
	 *            The osb table
	 */
	public ListPopupWindow(final MyTable osbTable) {
		generalSettings(osbTable.getTableInfo());
		
		setContent(new ListPopupLayout(osbTable, null));
	}
	
	/**
	 * General settings.
	 * @param tableInfo
	 *            The table info
	 */
	private void generalSettings(final TableInfo tableInfo) {
		center();
		setCaption(tableInfo.getPopupEditCaption());
		setWidth(tableInfo.getPopupEditWidth(), Unit.PIXELS);
		setHeight(tableInfo.getPopupEditHeight(), Unit.PIXELS);
	}
}
