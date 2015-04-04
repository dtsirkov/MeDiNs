package autogenerate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import layout.GeneratedEdit;

import table.MyColumn;
import table.TableInfo;


import annotations.MyTable;
import com.vaadin.data.util.BeanItemContainer;
import common.MyUtil;
import components.MyEdit;
import components.MyImport;


/**
 * Description: The specific information about each {@link MyTable} that is supposed to be created.<br>
 * The visible columns, generated columns, searchability of each column, ...<br>
 * Filename: TableInfo.java <br>
 */
public class GenerateTableInfo extends TableInfo {
	private static final long serialVersionUID = -28713779327451123L;

	public GenerateTableInfo(Class<?> clazz, BeanItemContainer<?> beanItemContainer, boolean shallGenerate, boolean includeId) {

		this.setClazz(clazz);
		//this.setJPAFilters(filters);

		MyTable myTable = clazz.getAnnotation(MyTable.class);
		Table table = clazz.getAnnotation(Table.class);

		String caption = "";
		String popupCaption = "";

		boolean isSearchable = false;
		int width = -1;
		int height = -1;

		if (myTable != null) {
			caption = myTable.caption();
			popupCaption = myTable.popupCaption();
			width = myTable.width();
			height = myTable.height();
			isSearchable = myTable.isSearchable();
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
		this.setBeanItemContainer(beanItemContainer);

		if (shallGenerate) {
			List<MyColumn> columnsList = new ArrayList<MyColumn>();
			List<String> nested = new ArrayList<String>();

			boolean containsAnnot = false;
			for (Field f : clazz.getDeclaredFields()) {
				annotations.MyColumn myColumn = f.getAnnotation(annotations.MyColumn.class);
				if (myColumn != null) {
					MyColumn tableColumn;
					String id = myColumn.id();
					if ("".equals(id)) {
						id = f.getName();
					}
					String name = myColumn.name();
					if ("".equals(name)) {
						tableColumn = new MyColumn(id, myColumn.isSearchable(), myColumn.isExactMatch(), myColumn.isIgnoreCase(), myColumn.width(), myColumn.format());
					} else {
						tableColumn = new MyColumn(id, myColumn.name(), myColumn.isSearchable(), myColumn.isExactMatch(), myColumn.isIgnoreCase(), myColumn.width(), myColumn.format());
					}
					columnsList.add(tableColumn);
					if (myColumn.id().contains(".")) {
						nested.add(myColumn.id());
					}
					containsAnnot = true;
				}
			}

			if (!containsAnnot) {
				for (Field f : clazz.getDeclaredFields()) {
					Column column = f.getAnnotation(Column.class);
					Id id = f.getAnnotation(Id.class);

					// TODO for showing the sublist
					// OneToMany o2m = f.getAnnotation(OneToMany.class);

					// TODO better to find a way to get isSearchable, isExactMatch, isIgnoreCase and width from MyColumn annotation
					if (id != null && column != null && includeId) {
						columnsList.add(new MyColumn(f.getName(), MyUtil.getCaption(column.name()), true, false, true, -1, ""));
					} else if (id == null && column != null) {
						columnsList.add(new MyColumn(f.getName(), MyUtil.getCaption(column.name()), true, false, true, -1, ""));
					}
				}
			}

			this.setColumns(columnsList);
			this.setNestedProperties(nested.toArray(new String[nested.size()]));
		}
	}

	@Override
	public MyEdit getEditComponent(Object itemId) {
		return new GeneratedEdit(this, itemId);
	}

	@Override
	public MyEdit getNewComponent() {
		return new GeneratedEdit(this, null);
	}

	@Override
	public MyImport getImportComponent() {
		return null;
	}

}
