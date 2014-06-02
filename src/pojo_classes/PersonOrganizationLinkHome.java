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
 * Home object for domain model class PersonOrganizationLink.
 * @see pojo_classes.PersonOrganizationLink
 * @author Hibernate Tools
 */
public class PersonOrganizationLinkHome {

	private static final Log log = LogFactory
			.getLog(PersonOrganizationLinkHome.class);

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

	public void persist(PersonOrganizationLink transientInstance) {
		log.debug("persisting PersonOrganizationLink instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(PersonOrganizationLink instance) {
		log.debug("attaching dirty PersonOrganizationLink instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(PersonOrganizationLink instance) {
		log.debug("attaching clean PersonOrganizationLink instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(PersonOrganizationLink persistentInstance) {
		log.debug("deleting PersonOrganizationLink instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public PersonOrganizationLink merge(PersonOrganizationLink detachedInstance) {
		log.debug("merging PersonOrganizationLink instance");
		try {
			PersonOrganizationLink result = (PersonOrganizationLink) sessionFactory
					.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public PersonOrganizationLink findById(java.lang.Integer id) {
		log.debug("getting PersonOrganizationLink instance with id: " + id);
		try {
			PersonOrganizationLink instance = (PersonOrganizationLink) sessionFactory
					.getCurrentSession().get(
							"pojo_classes.PersonOrganizationLink", id);
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

	public List findByExample(PersonOrganizationLink instance) {
		log.debug("finding PersonOrganizationLink instance by example");
		try {
			List results = sessionFactory.getCurrentSession()
					.createCriteria("pojo_classes.PersonOrganizationLink")
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
