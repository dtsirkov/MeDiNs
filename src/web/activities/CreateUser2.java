package web.activities;

import java.util.HashMap;

import web.forms.Form;
import web.forms.SearchPersonForm;
import web.forms.UserForm;
import web.forms.ValidationForm;
import web.views.AbstractActivityView;


import com.vaadin.annotations.Theme;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Layout;
import com.vaadin.ui.UI;

import database.pojo.Persons;
import database.pojo.Users;

@Theme("medins")
public class CreateUser2 extends AbstractActivityView{

	private static final long serialVersionUID = 1L;

	public CreateUser2(UI ui) {
		super(ui);
		setLabel("userCreate2");
	}

	@Override
	public void enter(ViewChangeEvent event) {
		setCompositionRoot(buildLayout());
	}

	public Layout buildLayout(){

		Form[] requiredSteps = {
				new SearchPersonForm(this, "stepCreatePerson"),  
				new UserForm(this,"stepCreateUser"),
				new ValidationForm(this, "stepValidate")
		};
		setRequiredSteps(requiredSteps);

		Form[] optionalSteps = {};
		setOptionalSteps(optionalSteps);

		setValidationMethod("validateUser2");
		setMode("create");

		return super.buildLayout();
	}

	@Override
	protected boolean validate(HashMap<String, Form> hmRequiredSteps,HashMap<String, Form> hmOptionalSteps) {
		Persons person = (Persons)hmRequiredSteps.get("stepCreatePerson").getData();
		Users user = (Users)hmRequiredSteps.get("stepCreateUser").getData();

		Object[] objects  = {
				person,
				user
		};

		for(int i = 0; i < objects.length; i++){
			getDao().saveOrUpdate(objects[i]);
		}

		return true;
	}


}