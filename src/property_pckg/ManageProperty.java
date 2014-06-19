package property_pckg;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ManageProperty {

	public static final String getExceptionDtl(String propertyName){
		return ManageProperty.getProperty(propertyName, "exception.properties");
	}
	
	public static final String getLabelDtl(String propertyName){
		return ManageProperty.getProperty(propertyName, "label.properties");
	}
	
	public static final String getButtonDtl(String propertyName){
		return ManageProperty.getProperty(propertyName, "button.properties");
	}

	public static final String getProperty(String propertyName, String fileName)
	{
		Properties prop = new Properties();
		String propertyValue = "";
		try {
			//load a properties file from class path, inside static method
			InputStream in = ManageProperty.class.getResourceAsStream(fileName);
			prop.load(in);
			propertyValue = prop.getProperty(propertyName);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return propertyValue;
	}

	public static void main(String[] args){
		System.out.println(ManageProperty.getExceptionDtl("IncorrectSocialNumber_en"));
	}

}
