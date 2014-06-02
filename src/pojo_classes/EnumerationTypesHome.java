package pojo_classes;

// Generated May 18, 2014 7:33:39 PM by Hibernate Tools 4.0.0

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class EnumerationTypes.
 * @see pojo_classes.EnumerationTypes
 * @author Hibernate Tools
 */
public class EnumerationTypesHome {

	private static final Log log = LogFactory
			.getLog(EnumerationTypesHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext()
					.lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(EnumerationTypes transientInstance) {
		log.debug("persisting EnumerationTypes instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(EnumerationTypes instance) {
		log.debug("attaching dirty EnumerationTypes instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(EnumerationTypes instance) {
		log.debug("attaching clean EnumerationTypes instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(EnumerationTypes persistentInstance) {
		log.debug("deleting EnumerationTypes instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public EnumerationTypes merge(EnumerationTypes detachedInstance) {
		log.debug("merging EnumerationTypes instance");
		try {
			EnumerationTypes result = (EnumerationTypes) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public EnumerationTypes findById(java.lang.String id) {
		log.debug("getting EnumerationTypes instance with id: " + id);
		try {
			EnumerationTypes instance = (EnumerationTypes) sessionFactory
					.getCurrentSession().get("pojo_classes.EnumerationTypes",
							id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(EnumerationTypes instance) {
		log.debug("finding EnumerationTypes instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("pojo_classes.EnumerationTypes")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
