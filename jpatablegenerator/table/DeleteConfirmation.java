package table;

import java.util.Collection;

import org.vaadin.dialogs.ConfirmDialog;

import ui.MyUI;

import com.vaadin.ui.Table;

/**
 * Description: Before a deletion occurs a dialog will get the confirmation from<br>
 * the user. This class uses {@link ConfirmDialog}.<br>
 * Filename: DeleteConfirmation.java <br>
 */
public final class DeleteConfirmation {

	/** The caption. */
	private static String caption = "Test";
	
	/** The message. */
	private static String message = "Test";
	
	/** The ok caption. */
	private static String okCaption = "Yes";
	
	/** The cancel caption. */
	private static String cancelCaption = "No";
	
	/**
	 * Instantiates a new DeleteConfirmation object.
	 */
	private DeleteConfirmation() {
	}
	
	/**
	 * Shows the dialog whether the user is sure about deleting the selected items from the given table.
	 * 
	 * @param table The table that the selected items are supposed to be deleted from.
	 */
	public static void show(final Table table) {
		ConfirmDialog.show(MyUI.getCurrent(), caption, message, okCaption, cancelCaption, new ConfirmDialog.Listener() {
			
			private static final long serialVersionUID = 2498283846710824877L;

			@SuppressWarnings("unchecked")
			@Override
			public void onClose(final ConfirmDialog dialog) {
				if (dialog.isConfirmed()) {
					Collection<Object> items2delete =  (Collection<Object>) table.getValue();
					for (Object object : items2delete) {
						try {
							table.getContainerDataSource().removeItem(object);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					table.setValue(null);
					table.select(null);
				}
			}
		});
	}

	/**
	 * Shows the dialog whether the user is sure about deleting the selected item from the given table.
	 * 
	 * @param table The table that the selected item are supposed to be deleted from.
	 * @param itemId The id which is supposed to be deleted.
	 */
	public static void show(final Table table, final Object itemId) {
		ConfirmDialog.show(MyUI.getCurrent(), caption, message, okCaption, cancelCaption, new ConfirmDialog.Listener() {
			private static final long serialVersionUID = 2498283846710824877L;
			@Override
			public void onClose(final ConfirmDialog dialog) {
				if (dialog.isConfirmed()) {
					table.getContainerDataSource().removeItem(itemId);
				}
			}
		});
	}

}
