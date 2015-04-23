package windows;

public enum Type {

	NEW,
	EDIT,
	IMPORT;
	
	public static Type get(int i) {
		return valueOf(i + "");
	}
	
}