package autogenerate;

import table.MyTable;

import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button.ClickListener;

public class GenerateTable extends MyTable {

	private static final long serialVersionUID = 1724231700657163458L;


	public GenerateTable(Class<?> clazz, BeanItemContainer<?> beanItemContainer) {
		this(clazz, beanItemContainer, false);
	}

	public GenerateTable(Class<?> clazz, BeanItemContainer<?> beanItemContainer, boolean includeId) {
		super(new GenerateTableInfo(clazz, beanItemContainer, true, includeId));
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
