package web.components.table.generated.autogenerate;

import web.components.table.generated.MyTable;
import web.forms.Form;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button.ClickListener;

public class GenerateTable extends MyTable {

	private static final long serialVersionUID = 1724231700657163458L;


	public GenerateTable(Class<?> clazz, BeanItemContainer<?> beanItemContainer, Form editForm) {
		this(clazz, beanItemContainer, editForm, false);
	}

	public GenerateTable(Class<?> clazz, BeanItemContainer<?> beanItemContainer, Form editForm, boolean includeId) {
		super(new GenerateTableInfo(clazz, beanItemContainer, editForm, true, includeId));
	}


	@Override
	public ClickListener saveButtonListener() {
		return null;
	}

	@Override
	public String export2excel() {
		return null;
	}

}
