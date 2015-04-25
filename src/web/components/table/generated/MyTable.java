package web.components.table.generated;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import web.MedinsUI;
import web.classes.PropertyManager;
import web.components.table.generated.components.CancelButton;
import web.components.table.generated.components.MyButton;
import web.components.table.generated.windows.EditPopupWindow;
import web.components.table.generated.windows.Type;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.event.ShortcutAction;
import com.vaadin.event.ShortcutListener;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.Resource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.StreamResource.StreamSource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.Table.ColumnGenerator;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;


/**
 * Description: The web.components.table.generated component including Search, Table, Action Buttons.<br>
 * <br>
 * Filename: MyTable.java <br>
 */
public abstract class MyTable extends CustomComponent {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2902024011161679617L;

	/** The main web.components.table.generated.layout. */
	private VerticalLayout mainLayout;

	/** The action buttons hl. */
	private HorizontalLayout actionButtonsHL;

	/** The btn print export. */
	private MyButton btnPrintExport;

	/** The hl expand. */
	private HorizontalLayout hlExpand;

	/** The btn cancel. */
	private CancelButton btnCancel;

	/** The btn save. */
	private MyButton btnSave;

	/** The btn add. */
	private MyButton btnAdd;

	/** The btn import. */
	private MyButton btnImport;

	/** The btn delete multiple. */
	private MyButton btnDeleteMultiple;

	/** The btn new. */
	private MyButton btnNew;

	/** The web.components.table.generated. */
	private Table table;

	/** The search component hl. */
	private HorizontalLayout searchComponentHL;

	/** The btn search. */
	private Button btnSearch;

	/** The search texts web.components.table.generated.layout. */
	private HorizontalLayout searchTextsLayout;

	/** The web.components.table.generated info. */
	private TableInfo tableInfo;

	/** The parent web.components.table.generated. */
	private Table parentTable;

	/** The selected item id. */
	private Object selectedItemId;

	/**
	 * Instantiates a new MyTable object.
	 *
	 * @param pTableInfo
	 *            The {@link TableInfo} of this web.components.table.generated view.
	 */
	public MyTable(final TableInfo pTableInfo) {
		this(pTableInfo, null, null);
	}

	/**
	 * Instantiates a new MyTable object.
	 *
	 * @param pTableInfo
	 *            The {@link TableInfo} of this web.components.table.generated view.
	 * @param pParentTable
	 *            The parent web.components.table.generated which is useful when having a list pop-up and the web.components.table.generated which <br>
	 *            leads to it is also needed.
	 * @param pSelectedItemId
	 *            The selected item id
	 */
	public MyTable(final TableInfo pTableInfo, final Table pParentTable, final Object pSelectedItemId) {

		this.tableInfo = pTableInfo;
		this.parentTable = pParentTable;
		this.selectedItemId = pSelectedItemId;


		mainLayout = new VerticalLayout();
		mainLayout.setSizeFull();
		mainLayout.setMargin(true);

		setSizeFull();

		//create "Search" web.components.table.generated.layout
		if(tableInfo.isSearchable()){
			generateSearch();
		}

		generateTable();
		tableInfo.setTable(table);

		actionButtonsHL = new HorizontalLayout();
		actionButtonsHL.setWidth("100.0%");
		actionButtonsHL.setMargin(true);
		actionButtonsHL.setSpacing(true);

		/*
		 * Import Button
		 */
		if (tableInfo.isImportable()) {
			btnImport = new MyButton(new ThemeResource("myVaadin/buttons/import.png"));
			btnImport.addClickListener(importButtonListener());
			actionButtonsHL.addComponent(btnImport);
		}

		if (tableInfo.isEditable() && !tableInfo.isNested()) {
			btnNew = new MyButton(new ThemeResource("myVaadin/buttons/new.png"));
			btnNew.addClickListener(newButtonListener());
			actionButtonsHL.addComponent(btnNew);
			actionButtonsHL.setSizeUndefined();
		}

		/*
		 * Delete Multiple Button
		 */
		if (tableInfo.isDeletable()) {
			btnDeleteMultiple = new MyButton(new ThemeResource("myVaadin/buttons/deleteMultiple.png"));
			btnDeleteMultiple.addClickListener(deleteMultipleButtonListener());
			btnDeleteMultiple.setVisible(false);
			actionButtonsHL.addComponent(btnDeleteMultiple);
		}

		/*
		 * Save and Cancel Buttons
		 */
		if ((tableInfo.isInlineEdit() && !tableInfo.isNested()) || tableInfo.isForSelection()) {
			btnSave = new MyButton(new ThemeResource("myVaadin/buttons/save.png"));
			btnSave.addClickListener(saveButtonListener());
			actionButtonsHL.addComponent(btnSave);

			btnCancel = new CancelButton(false);
			actionButtonsHL.addComponent(btnCancel);
		}

		hlExpand = new HorizontalLayout();
		actionButtonsHL.addComponent(hlExpand);
		actionButtonsHL.setExpandRatio(hlExpand, 1.0f);


		/*
		 * Add Button
		 */
		if (tableInfo.isInlineEdit() && tableInfo.isNested()) {
			btnAdd = new MyButton(new ThemeResource("myVaadin/buttons/inlineAdd.png"));
			actionButtonsHL.addComponent(btnAdd);
		}

		/*
		 * Export Button
		 */
		if (tableInfo.isExportEnabled()) {
			btnPrintExport = new MyButton(new ThemeResource("myVaadin/buttons/excel.png"));
			if (tableInfo.isSelectionExport()) {
				btnPrintExport.setVisible(false);
			} else {
				btnPrintExport.setVisible(false);
			}

			StreamSource stream = new StreamSource() {
				private static final long serialVersionUID = -712525138090838610L;
				@Override
				public InputStream getStream() {
					String fileName = export2excel();
					FileInputStream fis = null;
					try {
						fis = new FileInputStream(fileName);
						File f = new File(fileName);
						f.deleteOnExit();
					} catch (FileNotFoundException e) {
					} catch (Exception e) {
					}
					return fis;
				}
			};
			Resource resource = new StreamResource(stream, "ExcelExport.xlsx");
			FileDownloader fd = new FileDownloader(resource);
			fd.extend(btnPrintExport);
			actionButtonsHL.addComponent(btnPrintExport);
		}


		table.addValueChangeListener(new Table.ValueChangeListener() {
			private static final long serialVersionUID = -990297116955846583L;

			@Override
			public void valueChange(final ValueChangeEvent event) {

				if (table.isMultiSelect()) {
					@SuppressWarnings("unchecked")
					Collection<Object> itemsSelected = (Collection<Object>) table.getValue();
					if (tableInfo.isDeletable()) {
						btnDeleteMultiple.setVisible(false);
						if (itemsSelected.size() > 1) {
							btnDeleteMultiple.setVisible(true);
						}
					}
					if (tableInfo.isExportEnabled() && tableInfo.isSelectionExport()) {
						btnPrintExport.setVisible(false);
						if (itemsSelected.size() > 0) {
							btnPrintExport.setVisible(true);
						}
					}
				}

			}
		});


		mainLayout.addComponent(actionButtonsHL);
		mainLayout.setComponentAlignment(actionButtonsHL, Alignment.MIDDLE_RIGHT);

		setCompositionRoot(mainLayout);

		table.setEditable(pTableInfo.isInlineEdit());

		for (Filter filter : pTableInfo.getJPAFilters()) {
			pTableInfo.getBeanItemContainer().addContainerFilter(filter);
		}
	}

	/**
	 * Setup the {@link Table}.
	 */
	private void generateTable() {

		table = new Table();
		table.setPageLength(0);
		table.setSizeFull();
		table.setSelectable(tableInfo.isSelectable());
		table.setMultiSelect(tableInfo.isMultiSelect());
		table.setImmediate(true);
		table.setFooterVisible(false);
		
		table.setColumnReorderingAllowed(true);
		table.setColumnCollapsingAllowed(tableInfo.isColumnCollapsingAllowed());
		table.setContainerDataSource(tableInfo.getBeanItemContainer());

		mainLayout.addComponent(table);
		mainLayout.setExpandRatio(table, 1.0f);
		mainLayout.setComponentAlignment(table, Alignment.MIDDLE_CENTER);

		if (tableInfo.getNestedProperties().length > 0) {
			for (String nestedProp : tableInfo.getNestedProperties()) {
				tableInfo.getBeanItemContainer().addNestedContainerProperty(nestedProp);
			}
		}

		PropertyManager propertyManager = tableInfo.getPropertyManager();

		Object[] visibleColumns = new Object[tableInfo.getColumns().size()];
		for (int i = 0; i < tableInfo.getColumns().size(); i++) {
			MyColumn column = tableInfo.getColumns().get(i);
			visibleColumns[i] = column.getId();
			table.setColumnHeader(column.getId(), propertyManager.getLabelDtl(column.getName()));
			table.setColumnCollapsed(column.getId(), column.isCollapsed());
			table.setColumnExpandRatio(column.getId(), 1);
			if (column.getWidth() > 0) {
				table.setColumnWidth(column.getId(), column.getWidth());
			}
		}
		table.setVisibleColumns(visibleColumns);

		String column = "";

		if (tableInfo.getGeneratedColumns() != null) {
			for (CustomColumn gc : tableInfo.getGeneratedColumns()) {
				table.addGeneratedColumn(gc.getColumnName(), gc.getColumn());
				table.setColumnAlignment(gc.getColumnName(), gc.getColumnAlignment());
				table.setColumnWidth(gc.getColumnName(), gc.getColumnWidth());
			}
		}

		if (tableInfo.isEditable()) {
			ColumnGenerator editColumn = generateEditColumn();
			column = propertyManager.getLabelDtl("editColumn");
			table.addGeneratedColumn(column, editColumn);
			table.setColumnAlignment(column, Align.CENTER);
			table.setColumnWidth(column, 60);
		}

		if (tableInfo.isDeletable()) {
			ColumnGenerator deleteColumn = generateDeleteColumn();
			column = propertyManager.getLabelDtl("deleteColumn");
			table.addGeneratedColumn(column, deleteColumn);
			table.setColumnAlignment(column, Align.CENTER);
			table.setColumnWidth(column, 60);
		}


	}

	/**
	 * Generate edit column.
	 *
	 * @return The "Edit" Generated column.
	 */
	private ColumnGenerator generateEditColumn() {
		return new ColumnGenerator() {
			private static final long serialVersionUID = 6805608260458466901L;
			@Override
			public Object generateCell(final Table source, final Object itemId, final Object columnId) {
				Button editCell = new Button("");
				editCell.addStyleName("tableCellIcon");
				editCell.setData(itemId);
				editCell.addClickListener(new Button.ClickListener() {
					private static final long serialVersionUID = -7781579576610805407L;
					public void buttonClick(final ClickEvent event) {
						((MedinsUI) UI.getCurrent()).showEditPopup(new EditPopupWindow(tableInfo, itemId, Type.EDIT));
					}
				});
				editCell.setIcon(new ThemeResource("myVaadin/icons/edit.png"));
				editCell.addStyleName("link");
				return editCell;
			}
		};
	}

	/**
	 * Generate delete column.
	 *
	 * @return The "Delete" Generated column.
	 */
	private ColumnGenerator generateDeleteColumn() {
		return new ColumnGenerator() {
			private static final long serialVersionUID = 6805608260458466901L;
			@Override
			public Object generateCell(final Table source, final Object itemId, final Object columnId) {
				Button deleteCell = new Button("");
				deleteCell.addStyleName("tableCellIcon");
				deleteCell.setData(itemId);
				deleteCell.addClickListener(new Button.ClickListener() {
					private static final long serialVersionUID = 7853224328437899372L;
					public void buttonClick(final ClickEvent event) {
						DeleteConfirmation.show(table, itemId);
					}
				});
				deleteCell.setIcon(new ThemeResource("myVaadin/icons/delete.png"));
				deleteCell.addStyleName("link");
				return deleteCell;
			}
		};
	}


	private boolean hasSearchField() {
		for (MyColumn column : tableInfo.getColumns()) {
			if (column.isSearchable()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Generate search web.components.table.generated.components including "Search button" and {@link SearchText}s.
	 */
	private void generateSearch() {
		if (hasSearchField()) {

			searchComponentHL = new HorizontalLayout();
			searchComponentHL.setMargin(true);
			searchComponentHL.setSpacing(true);

			searchTextsLayout = new HorizontalLayout();
			searchTextsLayout.setSpacing(true);
			searchComponentHL.addComponent(searchTextsLayout);

			btnSearch = new MyButton(new ThemeResource("myVaadin/icons/search.png"));
			btnSearch.setImmediate(true);
			searchComponentHL.addComponent(btnSearch);
			mainLayout.addComponent(searchComponentHL);
			mainLayout.setComponentAlignment(searchComponentHL, new Alignment(6));

			final List<SearchText> stfs = new ArrayList<SearchText>();

			if (!tableInfo.isImmediateSearch()) {
				btnSearch.addClickListener(new ClickListener() {
					private static final long serialVersionUID = 5396737083597044287L;
					@Override
					public void buttonClick(final ClickEvent event) {
						doSearchClickAction(stfs);
					}
				});
			} else {
				btnSearch.setVisible(false);
			}

			for (MyColumn column : tableInfo.getColumns()) {
				if (column.isSearchable()) {
					final SearchText searchField = new SearchText();
					stfs.add(searchField);
					searchTextsLayout.addComponent(searchField);

					if (tableInfo.isImmediateSearch()) {
						searchField.addValueChangeListener(new com.vaadin.data.Property.ValueChangeListener() {
							private static final long serialVersionUID = 7261179516934915376L;
							@Override
							public void valueChange(final ValueChangeEvent event) {
								doSearchClickAction(stfs);
							}
						});
					} else {
						searchField.addShortcutListener(new ShortcutListener("Shortcut Name", ShortcutAction.KeyCode.ENTER, null) {
							private static final long serialVersionUID = -4319360555953651250L;
							@Override
							public void handleAction(final Object sender, final Object target) {
								doSearchClickAction(stfs);
							}
						});
					}
					searchField.setImmediate(true);
					searchField.setWidth("100%");
					searchField.setPropertyId(column.getId());
					searchField.setInputPrompt(column.getName());
					searchField.setExactMatch(column.isExactMatch());
					searchField.setIgnoreCase(column.isIgnoreCase());
					searchTextsLayout.setExpandRatio(searchField, 1);
				}
			}
		}

	}

	/**
	 * Do search click action.
	 *
	 * @param stfs
	 *            The {@link SearchText}s.
	 */
	private void doSearchClickAction(final List<SearchText> stfs) {


		for (SearchText tf : stfs) {
			tableInfo.getBeanItemContainer().removeContainerFilters(tf.getPropertyId());
		}

		/*
		 * Very important. Keep in mind that there might be some filters before getting to this level of search again.
		 * For example if the result is restricted to a specific user. Then it must maintain as the result of the user.
		 */
		if (tableInfo.getJPAFilters() != null) {
			for (Filter filter : tableInfo.getJPAFilters()) {
				tableInfo.getBeanItemContainer().addContainerFilter(filter);
			}
		}

		for (SearchText tf : stfs) {
			if (tf.getValue().length() > 0) {
				if (tf.isExactMatch()) {
					Filter f = new Compare.Equal(tf.getPropertyId(), tf.getValue());
					tableInfo.getBeanItemContainer().addContainerFilter(f);
				} else {
					tableInfo.getBeanItemContainer().addContainerFilter(tf.getPropertyId(), tf.getValue(), tf.isIgnoreCase(), tf.isOnlyMatchPrefix());
				}
			}
		}
		Notification.show(tableInfo.getBeanItemContainer().size() + " items found");
	}


	/**
	 * Save button listener. Based on each Entity the action varies. That's why it
	 * is considered as an abstract method.
	 *
	 * @return The {@link ClickListener} for "Save" button.
	 */
	public abstract ClickListener saveButtonListener();

	/**
	 * New button listener.
	 *
	 * @return The {@link ClickListener} for "New" button.
	 */
	private ClickListener newButtonListener() {
		return new Button.ClickListener() {
			private static final long serialVersionUID = -5000801077096604587L;
			@Override
			public void buttonClick(final ClickEvent event) {
				((MedinsUI) UI.getCurrent()).showEditPopup(new EditPopupWindow(tableInfo, Type.NEW));
			}
		};
	}

	/**
	 * New button listener.
	 *
	 * @return The {@link ClickListener} for "New" button.
	 */
	private ClickListener importButtonListener() {
		return new Button.ClickListener() {
			private static final long serialVersionUID = -484761074161106972L;

			@Override
			public void buttonClick(final ClickEvent event) {
				((MedinsUI) UI.getCurrent()).showEditPopup(new EditPopupWindow(tableInfo, Type.IMPORT));
			}
		};
	}

	/**
	 * Delete multiple button listener.
	 *
	 * @return The {@link ClickListener} for "DeleteMultiple" button.
	 */
	private ClickListener deleteMultipleButtonListener() {
		return new Button.ClickListener() {
			private static final long serialVersionUID = 2638728823846336383L;
			@Override
			public void buttonClick(final ClickEvent event) {
				DeleteConfirmation.show(table);
			}
		};
	}

	/**
	 * Adds the button listener.
	 *
	 * @return The {@link ClickListener}.
	 */
	public final ClickListener addButtonListener()	{
		return new Button.ClickListener() {
			private static final long serialVersionUID = 3770965974062696300L;
			@Override
			public void buttonClick(final ClickEvent event) {
				table.getContainerDataSource().addItem();
			}
		};
	}

	/**
	 * Adds the button listener for the "Add" button.
	 *
	 * @param cbl The {@link ClickListener} for "Add" button.
	 *
	 */
	public final void setAddButtonListener(final ClickListener cbl) {
		btnAdd.addClickListener(cbl);
	}

	/**
	 * Gets the web.components.table.generated.
	 *
	 * @return The web.components.table.generated
	 */
	public final Table getTable() {
		return table;
	}

	/**
	 * Gets the parent web.components.table.generated.
	 *
	 * @return The parent web.components.table.generated
	 */
	public final Table getParentTable() {
		return parentTable;
	}

	/**
	 * Sets the parent web.components.table.generated.
	 *
	 * @param pParentTable
	 *            The new parent web.components.table.generated
	 */
	public final void setParentTable(final Table pParentTable) {
		this.parentTable = pParentTable;
	}

	/**
	 * Gets the selected item id.
	 *
	 * @return The selected item id
	 */
	public final Object getSelectedItemId() {
		return selectedItemId;
	}

	/**
	 * Sets the selected item id.
	 *
	 * @param pSelectedItemId
	 *            The new selected item id
	 */
	public final void setSelectedItemId(final Object pSelectedItemId) {
		this.selectedItemId = pSelectedItemId;
	}

	/**
	 * Gets the {@link TableInfo}.
	 *
	 * @return The web.components.table.generated info
	 */
	public final TableInfo getTableInfo() {
		return tableInfo;
	}

	/**
	 * Sets the {@link TableInfo}.
	 *
	 * @param pTableInfo
	 *            The new web.components.table.generated info
	 */
	public final void setTableInfo(final TableInfo pTableInfo) {
		this.tableInfo = pTableInfo;
	}

	/**
	 * This abstract method shall be implemented for each specific project.
	 *
	 * @return The file name is created.
	 */
	public abstract String export2excel();

}
