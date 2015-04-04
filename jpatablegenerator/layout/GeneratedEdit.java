package layout;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;


import table.TableInfo;
import ui.MyUI;

import annotations.MyColumn;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.ErrorMessage;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import components.MyEdit;
import components.MyNumberField;
import components.MyTextField;

@SuppressWarnings("rawtypes")
public class GeneratedEdit extends MyEdit {

	private static final long serialVersionUID = 1736627586496278968L;

	private List<AbstractField> components = new ArrayList<AbstractField>();
	private List<Class> fieldTypes = new ArrayList<Class>();
	private List<Field> fields = new ArrayList<Field>();
	private FormLayout formLayout;

	private boolean hasError = false;

	public GeneratedEdit(final TableInfo tableInfo, final Object itemId) {
		super(tableInfo);

		BeanItem<?> beanItem = null;
		if (itemId != null) {
			beanItem = tableInfo.getBeanItemContainer().getItem(itemId);
			setIdentification(beanItem);
		}

		for (Field f : tableInfo.getClazz().getDeclaredFields()) {
			annotations.MyEdit et = f.getAnnotation(annotations.MyEdit.class);
			Column c = f.getAnnotation(Column.class);
			MyColumn mc = f.getAnnotation(MyColumn.class);
			Id id = f.getAnnotation(Id.class);
			if (id == null && et != null && (c != null || mc != null) ) {

				boolean required;
				if (c == null) {
					JoinColumn jc = f.getAnnotation(JoinColumn.class);
					required = jc.nullable();
				} else {
					required = !c.nullable();
				}

				if (et.componentType().equals(com.vaadin.ui.AbstractField.class)) {
					AbstractField field = null;
					if (f.getType() == String.class) {
						String validationMsg = "Invalid String value";
						if (!"".equals(et.validationMessage())) {
							validationMsg = et.validationMessage();
						}

						String requiredMsg = "Field cannot be empty";
						if (!"".equals(et.requiredMessage())) {
							requiredMsg = et.requiredMessage();
						}

						String validationRegex = "(?=\\s*\\S).*";
						if (!"".equals(et.validationRegex())) {
							validationRegex = et.validationRegex();
						}

						field = new MyTextField(et.caption(), required, requiredMsg, c.length(), validationRegex, validationMsg);

					} else if (f.getType() == Integer.class) {

						String validationMsg = "Invalid Integer value";
						if (!"".equals(et.validationMessage())) {
							validationMsg = et.validationMessage();
						}

						String requiredMsg = "Field cannot be empty";
						if (!"".equals(et.requiredMessage())) {
							requiredMsg = et.requiredMessage();
						}

						String validationRegex = "(\\d+)|(\\d+[\\.,]\\d+)*";
						if (!"".equals(et.validationRegex())) {
							validationRegex = et.validationRegex();
						}

						field = new MyNumberField(et.caption(), required, requiredMsg, c.length(), validationRegex, validationMsg, et.format());

					} else if (f.getType() == Boolean.class) {
						field = new CheckBox(et.caption());
					} else if (f.getType() == Date.class) {
						field = new DateField(et.caption());
					} else {
						field = new TextField(et.caption());
					}

					if (field != null) {
						field.setImmediate(false);
						field.setRequired(required);
						if (required) {
							if ("".equals(et.requiredMessage())) {
								field.setRequiredError("The field shall not be empty");
							} else {
								field.setRequiredError(et.requiredMessage());
							}
						}
						if (beanItem != null) {
							field.setPropertyDataSource(beanItem.getItemProperty(c.name()));
						}
						field.addValueChangeListener(new Property.ValueChangeListener() {
							private static final long serialVersionUID = -4112453798709845208L;

							@Override
							public void valueChange(ValueChangeEvent event) {
								hasError = false;
							}
						});
						components.add(field);
						fieldTypes.add(f.getType());
						fields.add(f);
						formLayout.addComponent(field);
					}
				} else {
					if (et.id().contains(".")) {
						if (et.componentType().equals(com.vaadin.ui.ComboBox.class)) {

							/*
							GenerateTableInfo gti = new GenerateTableInfo(f.getType(), tableInfo.getJPAFilters(), false, required);
							ComboBox field = new ComboBox();
							field.setContainerDataSource(gti.getJpaContainer());

							field.setFilteringMode(FilteringMode.CONTAINS);
							 */

							ComboBox field = new ComboBox();
							//final Map<Enumerations, String> personSexEnum = dao.getEnumeration("person sex");
							//System.out.println(personSexEnum.values().toArray());
							//field.addItems(personSexEnum.values().toArray());

							//String[] strArray = et.itemCaption().split("\\.");
							//field.setItemCaptionPropertyId(strArray[strArray.length - 1]);

							field.setImmediate(true);
							field.setRequired(required);
							field.setNullSelectionAllowed(required);
							if (required) {
								if ("".equals(et.requiredMessage())) {
									field.setRequiredError("The field shall not be empty");
								} else {
									field.setRequiredError(et.requiredMessage());
								}
							}
							if (beanItem != null) {
								//field.setPropertyDataSource(entityItem.getItemProperty(c.name()));
								//field.select(entityItem.getItemId());
								System.out.println(c.name());
								System.out.println(beanItem.getItemProperty(c.name()));
								//System.out.println(personSexEnum.get(beanItem.getItemProperty(c.name())));
								//if mode is equal to "update" display selected value
								String codeValue = "";
								//field.select(entityItem.getItemProperty(c.name()));
							}
							components.add(field);
							fieldTypes.add(f.getType());
							formLayout.addComponent(field);
							fields.add(f);
						}
					}
				}

			}

		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public final void saveAction(final TableInfo tableInfo) {
		if (checkValidation()) {
			if (getIdentification() == null) {
				try {
					Constructor c = tableInfo.getClazz().getConstructor(fieldTypes.toArray(new Class[fieldTypes.size()]));
					List<Object> list = new ArrayList<Object>();
					int i = 0;
					for (Object o : components) {
						Object val = o.getClass().getMethod("getValue").invoke(o);
						Class fieldType = fieldTypes.get(i);
						if (fieldType.equals(Integer.class)) {
							if (val == null || "".equals(val.toString())) {
								list.add(null);
							} else {
								list.add(Integer.parseInt(val.toString()));
							}
						} else if (fieldType.equals(Double.class)) {
							if (val == null || "".equals(val.toString())) {
								list.add(null);
							} else {
								list.add(Double.parseDouble(val.toString()));
							}
						} else if (fieldType.equals(Long.class)) {
							if (val == null || "".equals(val.toString())) {
								list.add(null);
							} else {
								list.add(Long.parseLong(val.toString()));
							}
						} else if (fieldType.equals(String.class)) {
							list.add(val);
						} else {
							annotations.MyEdit et = fields.get(i).getAnnotation(annotations.MyEdit.class);
							if (et != null && et.id().contains(".")) {
								Object fcObject = fieldType.getConstructor().newInstance();
								String[] strArray = et.id().split("\\.");
								String ssttrr = strArray[1].substring(0,1).toUpperCase() + strArray[1].substring(1);
								fcObject.getClass().getMethod("set" + ssttrr, val.getClass()).invoke(fcObject, val);
								list.add(fcObject);
							}
						}
						i++;
					}
					BeanItem<?> beanItem = tableInfo.getBeanItemContainer().addItem((c.newInstance(list.toArray(new Object[list.size()]))));
					setIdentification(beanItem);

					if (!hasError) {
						((MyUI) UI.getCurrent()).removeActiveEditPopupWindow();
						Notification.show("Saved successfully");
					}

				} catch (NoSuchMethodException | SecurityException | UnsupportedOperationException | IllegalStateException
						| InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
					handleError(e.getCause());
				}
			} else {
				if (!hasError) {
					((MyUI) UI.getCurrent()).removeActiveEditPopupWindow();
					ErrorMessage errorMssg = getErrorMessage();
					Notification.show("Saved successfully" + (errorMssg != null ? errorMssg.toString() : ""));
				}
			}
		}
	}

	private void handleError(Throwable throwable) {
		String cause = "";
		for (Throwable t = throwable; t != null; t = t.getCause()) {
			if (t.getCause() == null) {
				// We're at final cause
				cause += t.getMessage();
			}
		}
		// Display the error message in a custom fashion
		Notification.show(cause, Type.ERROR_MESSAGE);
		hasError = true;
	}

	@Override
	public final Layout generateContent() {
		formLayout = new FormLayout();
		formLayout.setImmediate(true);
		formLayout.setMargin(true);
		formLayout.setSpacing(true);

		UI.getCurrent().setErrorHandler(new DefaultErrorHandler() {
			private static final long serialVersionUID = 6120431091175509219L;

			@Override
			public void error(com.vaadin.server.ErrorEvent event) {
				handleError(event.getThrowable());
			}
		});


		return formLayout;
	}

	/**
	 * Check validation.
	 *
	 * @return true, if successful
	 */
	private boolean checkValidation() {
		try {
			for (AbstractField field : components) {
				if (!field.isValid()) {
					Notification.show("Validation failed " + field.getCaption(), Type.WARNING_MESSAGE);
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}