package autogenerate;

import web.forms.Form;

import com.vaadin.ui.Panel;
import com.vaadin.data.util.BeanItemContainer;

public class GenerateTableInPanel extends Panel {

	private static final long serialVersionUID = 4661895296440961807L;

	public GenerateTableInPanel(Class<?> clazz, BeanItemContainer<?> beanItemContainer, Form editForm) {
		GenerateTable gt = new GenerateTable(clazz, beanItemContainer, editForm);
		this.setContent(gt);
		this.setCaption(gt.getTableInfo().getCaption());
	}

}
