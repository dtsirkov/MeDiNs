package web.components.table.generated.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.vaadin.ui.AbstractField;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MyEdit {
	public String id() default "";
	public String caption() default "";
	public Class<?> componentType() default AbstractField.class;
	public String requiredMessage() default "";
	public String validationMessage() default "";
	public String validationRegex() default "";
	public String format() default "";
	public String itemCaption() default "";
}
