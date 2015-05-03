package web.components.table.generated.windows;

import web.components.table.generated.TableInfo;

import com.vaadin.ui.Window;

/**
 * Description: The Edit pop-up window.<br>
 * <br>
 * Filename: EditPopupWindow.java <br>
 */
public class EditPopupWindow extends Window {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -328730814145776633L;

	/** The Constant MIN_HEIGHT. */
	//private static final int MIN_HEIGHT = 60;

	/** The Constant MIN_WIDTH. */
	//private static final int MIN_WIDTH = 20;

	public EditPopupWindow(final TableInfo tableInfo, Type type) {
		this(tableInfo, null, type);
	}
	/**
	 * Instantiates a new EditPopupWindow object.
	 * 
	 * @param tableInfo
	 *            The web.components.table.generated info
	 */
	public EditPopupWindow(final TableInfo tableInfo, Object itemId, Type type) {
		addStyleName("popUpWindowStyle");
		center();
		setCaption(tableInfo.getPopupEditCaption());
		setResizable(tableInfo.isPopupEditResizable());
		switch (type) {
		case NEW:
			setContent(tableInfo.getNewComponent());
			getContent().setSizeUndefined();
			break;
		case EDIT:
			setContent(tableInfo.getEditComponent(itemId));
			getContent().setSizeUndefined();
			break;
		case IMPORT:
			setContent(tableInfo.getImportComponent());
			getContent().setSizeUndefined();
			break;
		default:
			break;
		}
	}


}
