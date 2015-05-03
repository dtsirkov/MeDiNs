package web.components.table.generated;

import java.util.Collection;

import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.dialogs.DefaultConfirmDialogFactory;

import web.MedinsUI;
import com.vaadin.ui.Table;

/**
 * Description: Before a deletion occurs a dialog will get the confirmation from<br>
 * the user. This class uses {@link ConfirmDialog}.<br>
 * Filename: DeleteConfirmation.java <br>
 */
public final class DeleteConfirmation {

	/**
	 * Instantiates a new DeleteConfirmation object.
	 */
	private DeleteConfirmation() {}

	/**
	 * Shows the dialog whether the user is sure about deleting the selected items from the given web.components.table.generated.
	 * 
	 * @param web.components.table.generated The web.components.table.generated that the selected items are supposed to be deleted from.
	 */
	public static void show(final Table table) {

		createCustomConfirmDialog();

		ConfirmDialog.show(MedinsUI.getCurrent(), new ConfirmDialog.Listener() {

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
	 * Shows the dialog whether the user is sure about deleting the selected item from the given web.components.table.generated.
	 * 
	 * @param web.components.table.generated The web.components.table.generated that the selected item are supposed to be deleted from.
	 * @param itemId The id which is supposed to be deleted.
	 */
	public static void show(final Table table, final Object itemId) {

		createCustomConfirmDialog();

		ConfirmDialog.show(MedinsUI.getCurrent(), new ConfirmDialog.Listener() {
			private static final long serialVersionUID = 2498283846710824877L;
			@Override
			public void onClose(final ConfirmDialog dialog) {
				if (dialog.isConfirmed()) {
					table.getContainerDataSource().removeItem(itemId);
				}
			}
		});
	}

	private static void createCustomConfirmDialog(){
		ConfirmDialog.Factory df = new DefaultConfirmDialogFactory() {

			private static final long serialVersionUID = 4610330837671531327L;

			String MY_CAPTION = MedinsUI.getPropertyManager().getLabelDtl("delete");
			String MY_MESSAGE = MedinsUI.getPropertyManager().getLabelDtl("deleteMessage");
			String MY_OK_CAPTION = MedinsUI.getPropertyManager().getLabelDtl("yes");
			String MY_NOTOK_CAPTION = MedinsUI.getPropertyManager().getLabelDtl("no");
			//String MY_CANCEL_CAPTION = MedinsUI.getPropertyManager().getLabelDtl("cancel");

			@Override
			public ConfirmDialog create(String caption, String message,
					String okCaption, String notOkCaption, String cancelCaption) {
				ConfirmDialog confirmDialog;
				confirmDialog = super.create(caption == null ? MY_CAPTION : caption,
						message == null ? MY_MESSAGE : message,
								okCaption == null ? MY_OK_CAPTION : okCaption,
										notOkCaption == null ? MY_NOTOK_CAPTION : notOkCaption,
												cancelCaption);
				confirmDialog.setWidth("300px");
				confirmDialog.setStyleName("popUpWindowStyle");
				return confirmDialog;
			}

		};
		ConfirmDialog.setFactory(df);
	}

}
