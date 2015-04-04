package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MyTable {
	public String caption() default "";
	public String popupCaption() default "";
	public int width() default 300;
	public int height() default 200;
	public String persistanceName() default "";
	public boolean isSearchable() default false;
}