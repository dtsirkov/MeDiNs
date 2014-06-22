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
 * Home object for domain model class DeceaseTreatmentLink.
 * @see pojo_classes.DeceaseTreatmentLink
 * @author Hibernate Tools
 */
public class DeceaseTreatmentLinkHome {

	private static final Log log = LogFactory
			.getLog(DeceaseTreatmentLinkHome.class);

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

	public void persist(DeceaseTreatmentLink transientInstance) {
		log.debug("persisting DeceaseTreatmentLink instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(DeceaseTreatmentLink instance) {
		log.debug("attaching dirty DeceaseTreatmentLink instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DeceaseTreatmentLink instance) {
		log.debug("attaching clean DeceaseTreatmentLink instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(DeceaseTreatmentLink persistentInstance) {
		log.debug("deleting DeceaseTreatmentLink instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DeceaseTreatmentLink merge(DeceaseTreatmentLink detachedInstance) {
		log.debug("merging DeceaseTreatmentLink instance");
		try {
			DeceaseTreatmentLink result = (DeceaseTreatmentLink) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public DeceaseTreatmentLink findById(java.lang.Integer id) {
		log.debug("getting DeceaseTreatmentLink instance with id: " + id);
		try {
			DeceaseTreatmentLink instance = (DeceaseTreatmentLink) sessionFactory
					.getCurrentSession().get(
							"pojo_classes.DeceaseTreatmentLink", id);
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

	public List findByExample(DeceaseTreatmentLink instance) {
		log.debug("finding DeceaseTreatmentLink instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("pojo_classes.DeceaseTreatmentLink")
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