package pojo.classes;

// Generated May 18, 2014 7:33:39 PM by Hibernate Tools 4.0.0

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class EnumerationLabels.
 * @see pojo.classes.EnumerationLabels
 * @author Hibernate Tools
 */
public class EnumerationLabelsHome {

	private static final Log log = LogFactory
			.getLog(EnumerationLabelsHome.class);

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

	public void persist(EnumerationLabels transientInstance) {
		log.debug("persisting EnumerationLabels instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(EnumerationLabels instance) {
		log.debug("attaching dirty EnumerationLabels instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(EnumerationLabels instance) {
		log.debug("attaching clean EnumerationLabels instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(EnumerationLabels persistentInstance) {
		log.debug("deleting EnumerationLabels instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public EnumerationLabels merge(EnumerationLabels detachedInstance) {
		log.debug("merging EnumerationLabels instance");
		try {
			EnumerationLabels result = (EnumerationLabels) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public EnumerationLabels findById(java.lang.Integer id) {
		log.debug("getting EnumerationLabels instance with id: " + id);
		try {
			EnumerationLabels instance = (EnumerationLabels) sessionFactory
					.getCurrentSession().get("pojo.classes.EnumerationLabels",
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

	public List findByExample(EnumerationLabels instance) {
		log.debug("finding EnumerationLabels instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("pojo.classes.EnumerationLabels")
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
