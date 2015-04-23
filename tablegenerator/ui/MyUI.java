package ui;

import windows.EditPopupWindow;
import windows.ListPopupWindow;

import com.vaadin.ui.UI;

public abstract class MyUI extends UI {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4468500468417479189L;

	/**
	 * The active edit popup window. <br>
	 * This will indicate which popup window for edit is shown.
	 */
	private EditPopupWindow activeEditPopupWindow;

	/**
	 * The active list popup window. <br>
	 * This will indicate which popup window for list is shown.
	 */
	private ListPopupWindow activeListPopupWindow;

	/**
	 * Removes the previously active edit popup and sets the active edit popup window.
	 *
	 * @param activeEditPopup
	 *            the new active edit popup window
	 */
	public void setActiveEditPopupWindow(final EditPopupWindow activeEditPopup) {
		removeActiveEditPopupWindow();
		activeEditPopupWindow = activeEditPopup;
	}

	/**
	 * Removes the previously active list popup and sets the active list popup window.
	 *
	 * @param activeListPopup
	 *            the new active list popup window
	 */
	public void setActiveListPopupWindow(final ListPopupWindow activeListPopup) {
		removeActiveListPopupWindow();
		activeListPopupWindow = activeListPopup;
	}

	/**
	 * Removes the active edit popup window.
	 */
	public void removeActiveEditPopupWindow() {
		getCurrent().removeWindow(activeEditPopupWindow);
		if (activeEditPopupWindow != null) {
			activeEditPopupWindow.close();
		}
	}

	/**
	 * Removes the active list popup window.
	 */
	public void removeActiveListPopupWindow() {
		getCurrent().removeWindow(activeListPopupWindow);
		if (activeListPopupWindow != null) {
			activeListPopupWindow.close();
		}
	}

	/**
	 * Used to show the specified edit page as a popup window.
	 *
	 * @param editPopup The specified edit popup.
	 */
	public void showEditPopup(final EditPopupWindow editPopup) {
		getCurrent().addWindow(editPopup);
		setActiveEditPopupWindow(editPopup);
	}

	/**
	 * Used to show the specified list page as a popup window.
	 *
	 * @param listPopup The specified list popup.
	 */
	public void showListPopup(final ListPopupWindow listPopup) {
		removeActiveListPopupWindow();
		getCurrent().addWindow(listPopup);
		setActiveListPopupWindow(listPopup);
	}

	/**
	 * Removes all active popup windows (both edit and list).
	 */
	public void removeActivePopupWindows() {
		removeActiveEditPopupWindow();
		removeActiveListPopupWindow();
	}

	public EditPopupWindow getActiveEditPopupWindow() {
		return activeEditPopupWindow;
	}

	public ListPopupWindow getActiveListPopupWindow() {
		return activeListPopupWindow;
	}

}
