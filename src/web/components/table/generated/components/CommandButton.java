package web.components.table.generated.components;

import web.components.table.generated.MyTable;
import web.components.table.generated.TableInfo;
import web.components.table.generated.autogenerate.GenerateTableInfo;
import web.components.table.generated.ui.MyUI;
import web.forms.Form;


import com.vaadin.server.Page;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.data.util.BeanItemContainer;

public abstract class CommandButton extends MyButton {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5063884137760745432L;

	/**
	 * Instantiates a new MyButton object.
	 * @param themeResource
	 *            Indicates the icon that is supposed to be shown instead. <br>
	 *            If <code>null</code> the normal button will be shown.
	 * @param classForName The string value of the entity class path. 
	 * @param mainViewPanel The component which is supposed to contain the web.components.table.generated.
	 */
	public CommandButton(final ThemeResource themeResource, final Class<?> clazz, BeanItemContainer<?> beanItemContainer, final Form form, final Panel mainViewPanel) {
		super(themeResource);
		this.addClickListener(commandListener(clazz, beanItemContainer, form, mainViewPanel));
	}

	/**
	 * When a command button is clicked the corresponding web.components.table.generated is shown.
	 * 
	 * @param classForName The string value of the entity class path. 
	 * @param mainViewPanel The component which is supposed to contain the web.components.table.generated.
	 * @return The action dedicated to the command button when it is clicked.
	 */
	private ClickListener commandListener(final Class<?> clazz, final BeanItemContainer<?> beanItemContainer, final Form form, final Panel mainViewPanel) {
		return new ClickListener() {
			private static final long serialVersionUID = -5812537411555467805L;

			@SuppressWarnings("unchecked")
			@Override
			public void buttonClick(final ClickEvent event) {
				((MyUI) UI.getCurrent()).removeActivePopupWindows();
				TableInfo tableInfo = null;
				try {
					Class<TableInfo> classTableInfo = (Class<TableInfo>) Class.forName(clazz.getCanonicalName() + "TableInfo");
					tableInfo = classTableInfo.newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					web.components.table.generated.annotations.MyTable classAnnotation = clazz.getAnnotation(web.components.table.generated.annotations.MyTable.class);
					if (classAnnotation != null) {
						tableInfo = new GenerateTableInfo(clazz, beanItemContainer, form, true, true);
					} else {
						e.printStackTrace();
					}
				}
				assert (tableInfo != null); 
				Page.getCurrent().setTitle(tableInfo.getCaption());
				mainViewPanel.setCaption(tableInfo.getCaption());
				mainViewPanel.setContent(setContent(tableInfo));	
			}

		};
	}

	/**
	 * Sets the content of the mainViewPanel to the intended component.
	 * 
	 * @param tableInfo
	 *            The {@link TableInfo}.
	 * @return The component that is supposed to fill the mainViewPanel.
	 */
	public abstract MyTable setContent(TableInfo tableInfo);
}
