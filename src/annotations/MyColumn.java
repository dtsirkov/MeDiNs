package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MyColumn {
	public String id() default "";
	public String name() default "";
	public boolean isSearchable() default false;
	public boolean isExactMatch() default false;
	public boolean isIgnoreCase() default true;
	public boolean isVisible() default false;
	public boolean isVisibleByUser() default true;
	public boolean isCollapsed() default false;
	public int width() default -1;
	public String format() default "";
}
