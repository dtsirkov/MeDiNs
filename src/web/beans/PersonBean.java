package web.beans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import database.pojo.Enumerations;
import database.pojo.Persons;

public class PersonBean {

	private Persons value;
	private String label;

	public PersonBean(){
	}

	public PersonBean(Persons value,String label) {
		setValue(value);
		setLabel(label);
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Persons getValue() {
		return value;
	}

	public void setValue(Persons value) {
		this.value = value;
	}

	public static List<PersonBean> getPersonBeanList(Set<Persons> persons){
		List<PersonBean> outList = new ArrayList<PersonBean>();
		Iterator<Persons> it = persons.iterator();
		while (it.hasNext()) {
			Persons key = it.next();
			String label=key.getSocialNumber()+" "+key.getFirstName()+ " "+key.getLastName();
			outList.add(new PersonBean(key,label));
		}
		return outList;		
	}
	
	public static PersonBean getPersonBean(List<PersonBean> personBeans,Persons person){
		PersonBean out = new PersonBean();
		Iterator<PersonBean> it = personBeans.iterator();
		while (it.hasNext()) {
			PersonBean key = it.next();
			if (key.getValue().equals(person)) {
				out = key;
			}
		}
		return out;		
	}

}
