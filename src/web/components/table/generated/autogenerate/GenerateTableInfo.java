package web.components.table.generated.autogenerate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import web.MedinsUI;
import web.classes.PropertyManager;
import web.components.table.generated.MyColumn;
import web.components.table.generated.TableInfo;
import web.components.table.generated.annotations.MyTable;
import web.components.table.generated.common.MyUtil;
import web.components.table.generated.components.MyEdit;
import web.components.table.generated.components.MyImport;
import web.forms.Form;


import com.vaadin.data.util.BeanItemContainer;


/**
 * Description: The specific information about each {@link MyTable} that is supposed to be created.<br>
 * The visible columns, generated columns, searchability of each column, ...<br>
 * Filename: TableInfo.java <br>
 */
public class GenerateTableInfo extends TableInfo {
	private static final long serialVersionUID = -28713779327451123L;

	public GenerateTableInfo(Class<?> clazz, BeanItemContainer<?> beanItemContainer, Form editForm, boolean shallGenerate, boolean includeId) {

		this.setClazz(clazz);
		this.setEditForm(editForm);

		MyTable myTable = clazz.getAnnotation(MyTable.class);
		Table table = clazz.getAnnotation(Table.class);

		String caption = "";
		String popupCaption = "";

		boolean isSearchable = false, isColumnCollapsingAllowed = false;
		int width = -1;
		int height = -1;

		PropertyManager propertyManager = MedinsUI.getPropertyManager();
		this.setPropertyManager(propertyManager);

		if (myTable != null) {
			caption = propertyManager.getLabelDtl(myTable.caption());
			popupCaption = propertyManager.getLabelDtl(myTable.popupCaption());
			width = myTable.width();
			height = myTable.height();
			isSearchable = myTable.isSearchable();
			isColumnCollapsingAllowed = myTable.isColumnCollapsingAllowed();
		} else {
			popupCaption = MyUtil.getCaption(table.name());
			caption = popupCaption + "s";
			// TODO better to find a way to get width and height from MyTable annotation default values
			width = 300;
			height = 200;
		}

		this.setCaption(caption);
		this.setPopupEditCaption(popupCaption);
		this.setPopupEditWidth(width);
		this.setPopupEditHeight(height);
		this.setSearchable(isSearchable);
		this.setColumnCollapsingAllowed(isColumnCollapsingAllowed);
		this.setBeanItemContainer(beanItemContainer);

		if (shallGenerate) {
			List<MyColumn> columnsList = new ArrayList<MyColumn>();
			List<String> nested = new ArrayList<String>();

			boolean containsAnnot = false;
			String columnLabel = "";
			boolean adminisrator = false;
			for (Field f : clazz.getDeclaredFields()) {
				web.components.table.generated.annotations.MyColumn myColumn = f.getAnnotation(web.components.table.generated.annotations.MyColumn.class);
				if (myColumn != null) {
					MyColumn tableColumn;
					String id = myColumn.id();
					if ("".equals(id)) {
						id = f.getName();
					}
					columnLabel = propertyManager.getButtonDtl(myColumn.name());
					if ("".equals(columnLabel)) {
						tableColumn = new MyColumn(
								id, 
								myColumn.isSearchable(), 
								myColumn.isExactMatch(), 
								myColumn.isIgnoreCase(), 
								myColumn.isCollapsed(),
								myColumn.width(), 
								myColumn.format()
								);
					} else {		
						System.out.println(columnLabel);
						tableColumn = new MyColumn(
								id, 
								columnLabel, 
								myColumn.isSearchable(), 
								myColumn.isExactMatch(), 
								myColumn.isIgnoreCase(), 
								myColumn.isCollapsed(),
								myColumn.width(), 
								myColumn.format()
								);
					}
					if(adminisrator == true && myColumn.isVisible()){
						columnsList.add(tableColumn);
					}else if(myColumn.isVisible() && myColumn.isVisibleByUser()){
						columnsList.add(tableColumn);
					}
					if (myColumn.id().contains(".")) {
						nested.add(myColumn.id());
					}
					containsAnnot = true;
				}
			}

			if (!containsAnnot) {
				String fName = "", columnName = "";
				for (Field f : clazz.getDeclaredFields()) {
					Column column = f.getAnnotation(Column.class);
					Id id = f.getAnnotation(Id.class);

					// TODO for showing the sublist
					// OneToMany o2m = f.getAnnotation(OneToMany.class);

					// TODO better to find a way to get isSearchable, isExactMatch, isIgnoreCase and width from MyColumn annotation
					fName = propertyManager.getLabelDtl(f.getName());
					columnName = propertyManager.getLabelDtl(MyUtil.getCaption(column.name()));
					if (id != null && column != null && includeId) {
						columnsList.add(new MyColumn(fName, columnName, true, false, true, -1, ""));
					} else if (id == null && column != null) {
						columnsList.add(new MyColumn(fName, columnName, true, false, true, -1, ""));
					}
				}
			}

			this.setColumns(columnsList);
			this.setNestedProperties(nested.toArray(new String[nested.size()]));
		}
	}

	@Override
	public MyEdit getEditComponent(Object itemId) {
		return new MyEdit(this, itemId);
	}

	@Override
	public MyEdit getNewComponent() {
		return new MyEdit(this, null);
	}

	@Override
	public MyImport getImportComponent() {
		return null;
	}

}
