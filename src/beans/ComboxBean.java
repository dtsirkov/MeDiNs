package beans;

import java.util.ArrayList;
import java.util.HashMap;
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

	public static Map<String, String> getCodeDisplayMap(Map<Enumerations, String> enumMap){
		Map<String, String> codeDisplayMap = new HashMap<String, String>();
		Iterator<Enumerations> it = enumMap.keySet().iterator();
		while (it.hasNext()) {
			Enumerations key = it.next();
			codeDisplayMap.put(key.getCode(), enumMap.get(key));
		}
		return codeDisplayMap;
	}

	public static Map<String, String> getDisplayCodeMap(Map<Enumerations, String> enumMap){
		Map<String, String> displayCodeMap = new HashMap<String, String>();
		Iterator<Enumerations> it = enumMap.keySet().iterator();
		while (it.hasNext()) {
			Enumerations key = it.next();
			displayCodeMap.put(enumMap.get(key), key.getCode());
		}
		return displayCodeMap;
	}

}
