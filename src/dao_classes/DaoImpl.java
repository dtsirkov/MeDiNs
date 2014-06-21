package dao_classes;

// Generated Feb 11, 2014 10:41:09 PM by Hibernate Tools 4.0.0

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

import pojo_classes.EnumerationLabels;
import pojo_classes.EnumerationTypes;
import pojo_classes.Enumerations;
import pojo_classes.Persons;



/**
 * Home object for domain model classes in package pojo_classes.
 * @see package pojo_classes
 * @author Hibernate Tools
 */

public class DaoImpl implements DaoIntrfc, java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6238313952884288053L;

	private static final Log log = LogFactory.getLog(DaoImpl.class);

	private final Session session = (new SessionFactoryConfig()).getSession();

	public Session getSession() {
		return session;
	}

	public void persist(Object transientInstance) {
		log.info("persisting " +  getClassName(transientInstance) + " instance");
		try {
			//Session session = sessionFactory.getCurrentSession();
			Transaction trans = session.beginTransaction();
			session.persist(transientInstance);
			trans.commit();
		} catch (RuntimeException re) {
			log.error("Persist failed!", re);
			throw re;
		}
	}

	public void attachDirty(Object instance) {
		log.info("attaching dirty " + getClassName(instance) + " instance");
		try {
			//Session session = sessionFactory.getCurrentSession();
			Transaction trans = session.beginTransaction();
			session.saveOrUpdate(instance);
			trans.commit();
			log.info("attach successful");
		} catch (RuntimeException re) {
			log.error("Attach failed!", re);
			throw re;
		}
	}

	/*public void attachClean(Object instance) {
		log.info("attaching clean Object instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.info("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}*/

	@Override
	public void delete(Object persistentInstance) {
		log.info("deleting " + getClassName(persistentInstance) + " instance");
		try {
			//Session session = sessionFactory.getCurrentSession();
			Transaction trans = session.beginTransaction();
			session.delete(persistentInstance);
			trans.commit();
			log.info("delete successful");
		} catch (RuntimeException re) {
			log.error("Delete failed!", re);
			throw re;
		}
	}

	public Object merge(Object detachedInstance) {
		log.info("merging " + getClassName(detachedInstance) + " instance");
		try {
			//Session session = sessionFactory.getCurrentSession();
			Transaction trans = session.beginTransaction();
			Object result = session.merge(detachedInstance);
			trans.commit();
			log.info("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("Merge failed!", re);
			throw re;
		}
	}

	public Object findById(String pojoClass, Object id) {
		log.info("getting " + pojoClass + " instance with id: " + id);
		try {
			//Session session = sessionFactory.getCurrentSession();
			Transaction trans = session.beginTransaction();
			Object instance = session.get("pojo_classes." + pojoClass, (java.io.Serializable) id);
			trans.commit();
			if (instance == null) {
				log.info("get successful, no instance found");
			} else {
				log.info("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("Find by Id failed!", re);
			throw re;
		}
	}

	public List<Object> findByExample(Object instance) {
		log.info("finding " + getClassName(instance) + " instance by example");
		try {
			//Session session = sessionFactory.getCurrentSession();
			Transaction trans = session.beginTransaction();
			String pojoClass = getClassName(instance);
			@SuppressWarnings("unchecked")
			List<Object> results = session.createCriteria(pojoClass).add(Example.create(instance)).list();
			log.info("find by example successful, result size: " + results.size());
			trans.commit();
			return results;
		} catch (RuntimeException re) {
			log.error("Find by example failed!", re);
			throw re;
		}
	}

	public List<Object> findByExample(Object instance, Map<Object, List<Object>> hm) {
		log.info("finding " + getClassName(instance) + " instance by example");
		try {
			//Session session = sessionFactory.getCurrentSession();
			Transaction trans = session.beginTransaction();
			String pojoClass = getClassName(instance);
			Criteria criteria = session.createCriteria(pojoClass);
			Example example = Example.create(instance);
			criteria.add(example);
			for (Object propertyName : hm.keySet()) {
				List<Object> valueLs = hm.get(propertyName);
				System.out.println("Key = " + propertyName);
				criteria.add(Restrictions.in((String)propertyName, valueLs));
			}
			@SuppressWarnings("unchecked")
			List<Object> results = criteria.list();
			log.info("find by criteria successful, result size: " + results.size());
			trans.commit();
			return results;
		} catch (RuntimeException re) {
			log.error("Find by criteria failed!", re);
			throw re;
		}
	}

	public Map<Enumerations, String> getEnumeration(String type, String language){

		Map<Enumerations, String> hmEnumerationEnumerationLabel = new HashMap<Enumerations, String>();

		try{

			EnumerationTypes enumerationType = (EnumerationTypes)findById("EnumerationTypes", type);

			Map<Object, List<Object>> hmEnumeration = new HashMap<Object, List<Object>>();

			List<Object> enumerationTypeLs = new ArrayList<Object>();

			enumerationTypeLs.add(enumerationType);

			hmEnumeration.put("enumerationTypes", enumerationTypeLs);

			List<Object> enumerationLs = findByExample(new Enumerations(), hmEnumeration);

			EnumerationLabels enumerationLabel = new EnumerationLabels();

			enumerationLabel.setLanguage(language);

			Map<Object, List<Object>> hmEnumerationLabel = new HashMap<Object, List<Object>>();

			hmEnumerationLabel.put("enumerations", enumerationLs);

			List<Object> labelLs = findByExample(enumerationLabel, hmEnumerationLabel);

			for(Object enumeration : enumerationLs){
				String label = ((EnumerationLabels)labelLs.get(enumerationLs.indexOf(enumeration))).getLabel();
				hmEnumerationEnumerationLabel.put((Enumerations)enumeration, label);
			} 

		} catch (RuntimeException re) {
			log.error("Method getEnumarataion/2 of class DaoImpl failed!", re);	
		}

		return hmEnumerationEnumerationLabel;

	}

	public String getClassName(Object object){
		return object.getClass().getName();
	}

	public String toString(Object pojoObject) {
		StringBuilder stringBuilder = new StringBuilder();
		Transaction trans = session.beginTransaction();
		try {
			@SuppressWarnings("all")
			Class className = Class.forName(pojoObject.getClass().getName());		
			Method methodsArray[] = className.getDeclaredMethods();		
			Object propertyValue;
			String valueStr;
			for (int i = 0; i < methodsArray.length; i++)
				if (methodsArray[i].getName().startsWith("get")) {
					propertyValue = methodsArray[i].invoke(pojoObject);
					try{
						valueStr = ((Enumerations)propertyValue).getCode();
					} catch (Exception e){
						//valueStr = "unknown";
						valueStr = String.valueOf(propertyValue);
					}
					stringBuilder.append(methodsArray[i].getName().substring(3) + ": "
							+ valueStr + "\n");
				}
		} catch (Throwable e) {
			System.err.println(e);
		}
		trans.commit();
		return stringBuilder.toString();
	}

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException{

		Date birthDate = new Date();
		DaoImpl daoImpl = new DaoImpl();

		Enumerations jobTitle = (Enumerations)daoImpl.findById("Enumerations", "md");
		Enumerations title = (Enumerations)daoImpl.findById("Enumerations", "mrs");
		Enumerations role = (Enumerations)daoImpl.findById("Enumerations", "employe");
		Enumerations sex = (Enumerations)daoImpl.findById("Enumerations", "female");

		Random rand = new Random();
		Integer number = Math.abs(rand.nextInt());
		String numberStr = number.toString();

		Persons person = new Persons(numberStr, jobTitle, title, role, sex, "firstName", "lastName", birthDate);
		daoImpl.persist(person);

	}
}
