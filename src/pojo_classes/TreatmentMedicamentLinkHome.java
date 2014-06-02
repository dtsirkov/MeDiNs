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
 * Home object for domain model class TreatmentMedicamentLink.
 * @see pojo_classes.TreatmentMedicamentLink
 * @author Hibernate Tools
 */
public class TreatmentMedicamentLinkHome {

	private static final Log log = LogFactory
			.getLog(TreatmentMedicamentLinkHome.class);

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

	public void persist(TreatmentMedicamentLink transientInstance) {
		log.debug("persisting TreatmentMedicamentLink instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TreatmentMedicamentLink instance) {
		log.debug("attaching dirty TreatmentMedicamentLink instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TreatmentMedicamentLink instance) {
		log.debug("attaching clean TreatmentMedicamentLink instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TreatmentMedicamentLink persistentInstance) {
		log.debug("deleting TreatmentMedicamentLink instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TreatmentMedicamentLink merge(
			TreatmentMedicamentLink detachedInstance) {
		log.debug("merging TreatmentMedicamentLink instance");
		try {
			TreatmentMedicamentLink result = (TreatmentMedicamentLink) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TreatmentMedicamentLink findById(java.lang.Integer id) {
		log.debug("getting TreatmentMedicamentLink instance with id: " + id);
		try {
			TreatmentMedicamentLink instance = (TreatmentMedicamentLink) sessionFactory
					.getCurrentSession().get(
							"pojo_classes.TreatmentMedicamentLink", id);
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

	public List findByExample(TreatmentMedicamentLink instance) {
		log.debug("finding TreatmentMedicamentLink instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("pojo_classes.TreatmentMedicamentLink")
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
