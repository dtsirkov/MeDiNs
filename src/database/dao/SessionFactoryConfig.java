package database.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


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
			Configuration configuration = new Configuration();
			configuration.addResource("hibernate.cfg.xml").configure();
			StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder();
			ssrb.applySettings(configuration.getProperties());
			SessionFactory sessionFactory = configuration.buildSessionFactory(ssrb.build());
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
