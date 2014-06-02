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
 * Home object for domain model class TouristVisit.
 * @see pojo_classes.TouristVisit
 * @author Hibernate Tools
 */
public class TouristVisitHome {

	private static final Log log = LogFactory.getLog(TouristVisitHome.class);

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

	public void persist(TouristVisit transientInstance) {
		log.debug("persisting TouristVisit instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TouristVisit instance) {
		log.debug("attaching dirty TouristVisit instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TouristVisit instance) {
		log.debug("attaching clean TouristVisit instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TouristVisit persistentInstance) {
		log.debug("deleting TouristVisit instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TouristVisit merge(TouristVisit detachedInstance) {
		log.debug("merging TouristVisit instance");
		try {
			TouristVisit result = (TouristVisit) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TouristVisit findById(java.lang.Integer id) {
		log.debug("getting TouristVisit instance with id: " + id);
		try {
			TouristVisit instance = (TouristVisit) sessionFactory
					.getCurrentSession().get("pojo_classes.TouristVisit", id);
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

	public List findByExample(TouristVisit instance) {
		log.debug("finding TouristVisit instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("pojo_classes.TouristVisit")
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
