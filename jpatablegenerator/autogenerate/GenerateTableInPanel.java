package autogenerate;

import com.vaadin.ui.Panel;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.util.BeanItemContainer;

public class GenerateTableInPanel extends Panel {

	private static final long serialVersionUID = 4661895296440961807L;

	public GenerateTableInPanel(Class<?> clazz, BeanItemContainer<?> beanItemContainer) {
		GenerateTable gt = new GenerateTable(clazz, beanItemContainer);
		this.setContent(gt);
		this.setCaption(gt.getTableInfo().getCaption());
	}

}
