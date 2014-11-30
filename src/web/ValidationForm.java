package web;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Iterator;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Layout;

public class ValidationForm extends Form{

	private static final long serialVersionUID = 1L;

	public ValidationForm(){}

	public ValidationForm(VaadinRequest request, String label){
		super(request, label, new VerticalLayout());
	}

	public ValidationForm(ValidationForm validationForm) {

		super(validationForm);
		setLayout(new VerticalLayout());
		
		buildFormLayout(validationForm);
		setCompositionRoot(getLayout());

	}

	private void buildFormLayout(ValidationForm validationForm) {

		getLayout().setSizeUndefined();
		
		for(int i = 0; i < getObjectArray().length - 1; i++){
			addValidationComponent(getObjectArray()[i]);
		}

	}

	private void addValidationComponent(Object object){

		Layout componentLayout = ((CustomComponentIntrfc)object).getLayout();

		int componentCount = componentLayout.getComponentCount();
		GridLayout grid = new GridLayout(1, componentCount + 1);
		grid.setWidth("400px");

		Label label = new Label(((Form)object).getLabel());
		label.setStyleName("validationCompenentTitle");
		label.setSizeUndefined();
		grid.addComponent(label);
		grid.setComponentAlignment(label, Alignment.MIDDLE_LEFT);

		Iterator<Component> iterator = componentLayout.iterator();
		AbstractComponent abstractComponent;
		Panel panel;
		String caption, value;
		Label valueLabel;
		TextArea area;

		BufferedImage bufferedImage = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR_PRE);
		Graphics graphics = bufferedImage.getGraphics();
		FontMetrics metrics = graphics.getFontMetrics(graphics.getFont());
		graphics.dispose();

		while(iterator.hasNext()){	
			abstractComponent = (AbstractComponent)iterator.next();

			caption = abstractComponent.getCaption();
			//System.out.println(caption);		
			panel = new Panel(caption);
			//panel.setWidth("400px");
			panel.setStyleName("validation_panel");

			if (abstractComponent.getData() != null){
				value = abstractComponent.getData().toString();                

				int valueWidth = metrics.charsWidth(value.toCharArray(), 0, value.length());

				//System.out.println(value);
				
				if (valueWidth < 400){
					valueLabel = new Label(value);
					valueLabel.setSizeUndefined();
					panel.setContent(valueLabel);
				}else{
					area = new TextArea();
					area.setValue(value);
					area.setWordwrap(true);
					area.setWidth(panel.getWidth() + panel.getWidthUnits().toString());
					area.setStyleName("validation_textarea");
					area.setReadOnly(true);
					panel.setContent(area);
				}
			}else{
				valueLabel = new Label("-");
				valueLabel.setSizeUndefined();
				panel.setContent(valueLabel);
			}
			grid.addComponent(panel);
			grid.setComponentAlignment(panel, Alignment.MIDDLE_CENTER);
		}
		this.getLayout().addComponent(grid);
	}
	
}
