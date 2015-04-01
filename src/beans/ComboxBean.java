package beans;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import pojo.classes.Enumerations;

public class ComboxBean {

	private String value;
	private String label;

	public ComboxBean() {
	}

	public ComboxBean(String value,String label) {
		setValue(value);
		setLabel(label);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public static List<ComboxBean> getComboxBeanList(Map<Enumerations, String> enumMap){
		List<ComboxBean> outList = new ArrayList<ComboxBean>();
		Iterator<Enumerations> it = enumMap.keySet().iterator();
		while (it.hasNext()) {
			Enumerations key = it.next();
			String value = enumMap.get(key);
			outList.add(new ComboxBean(key.getCode(),value));
		}
		return outList;		
	}

}
