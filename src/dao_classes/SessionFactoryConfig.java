package dao_classes;

import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;


public class SessionFactoryConfig {

	private static final Log log = LogFactory.getLog(SessionFactoryConfig.class);
	
	private Session session;
	
	public SessionFactoryConfig(){
		SessionFactory sessionFactory = SessionFactoryConfig.getSessionFactory();
		this.setSession(sessionFactory.openSession());
	}

	protected static SessionFactory getSessionFactory() {
		try {
			log.info("Trying to create a test connection with the database.");
			InputStream in = DaoImpl.class.getResourceAsStream("/hibernate.cfg.xml");
			Configuration configuration = new Configuration();
			configuration.addInputStream(in);
			configuration.configure();
			ServiceRegistryBuilder serviceRegistryBuilder = 
					new ServiceRegistryBuilder().applySettings(configuration.getProperties());
			SessionFactory sessionFactory = 
					configuration.buildSessionFactory(serviceRegistryBuilder.buildServiceRegistry());
			sessionFactory.openSession();
			//Session session = sessionFactory.openSession();
			//session.close();
			log.info("Test connection with the database created successfuly.");
			return sessionFactory;
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

}
