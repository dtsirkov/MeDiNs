package web.forms;

import java.util.ArrayList;

import pojo_classes.Persons;
import web.views.AbstractView;

public class SearchPersonForm extends SearchForm<Persons>{

	private static final long serialVersionUID = 1L;

	public SearchPersonForm(AbstractView view, String label){
		super(view, label);
	}

	public ArrayList<Persons> search() {
		// TODO Auto-generated method stub
		return null;
	}

}
