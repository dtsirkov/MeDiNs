package web;

import java.util.HashMap;

import web.forms.Form;

public interface StepIntrfc {
	public void process(HashMap<String, Form> steps);
}
