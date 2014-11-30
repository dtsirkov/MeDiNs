package property_pckg;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;

import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;

public class PropertyManager {

	private String language;
	private ServletContext servletContext;

	public PropertyManager(){}
	
	public PropertyManager(String language){
		setLanguage(language);
	}

	public PropertyManager(VaadinRequest request){
		try{
			this.setLanguage(request.getLocale().getLanguage());
			this.setServletContext(VaadinServlet.getCurrent().getServletContext());
		}catch (Exception ex) {
			System.out.println("Can not create instance of class ManageProperty!");
		}
	}

	public PropertyManager(String language, ServletContext servletContext){
		this.setLanguage(language);
		this.setServletContext(servletContext);
	}

	public String getExceptionDtl(String propertyName){
		return getProperty(propertyName, "exception.properties");
	}

	public String getLabelDtl(String propertyName){
		return getProperty(propertyName, "label.properties");
	}

	public String getButtonDtl(String propertyName){
		return getProperty(propertyName, "button.properties");
	}

	public String getProperty(String propertyName, String fileName)
	{
		Properties prop = new Properties();
		String propertyValue = "";
		try {
			//load a properties file from class path, inside static method
			InputStream in = getServletContext().getResourceAsStream("/LANGUAGES/" + getLanguage() + "/" + fileName);
			prop.load(in);
			propertyValue = prop.getProperty(propertyName);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return propertyValue;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

}
