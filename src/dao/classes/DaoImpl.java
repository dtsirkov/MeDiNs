package dao.classes;

// Generated Feb 11, 2014 10:41:09 PM by Hibernate Tools 4.0.0

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

import pojo.classes.EnumerationLabels;
import pojo.classes.EnumerationTypes;
import pojo.classes.Enumerations;
import pojo.classes.Persons;



/**
 * Home object for domain model classes in package pojo.classes.
 * @see package pojo.classes
 * @author Hibernate Tools
 */

public class DaoImpl implements DaoIntrfc, java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6238313952884288053L;

	private static final Log log = LogFactory.getLog(DaoImpl.class);

	private final Session session = (new SessionFactoryConfig()).getSession();

	private String language;
	private Transaction transaction;

	public Session getSession() {
		return session;
	}

	public DaoImpl(){}

	public DaoImpl(String language){
		setLanguage(language);
	}

	public String getLanguage() {
		return language;
	}

	public Transaction getTransaction() {
		if (transaction != null && transaction.isActive()){
			return transaction;
		}
		transaction = session.beginTransaction();
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void persist(Object transientInstance) {
		log.info("persisting " +  getClassName(transientInstance) + " instance");
		try {
			session.persist(transientInstance);
		} catch (RuntimeException re) {
			log.error("Persist failed!", re);
			throw re;
		}
	}

	public void saveOrUpdate(Object instance) {
		log.info("attaching dirty " + getClassName(instance) + " instance");
		try {
			session.saveOrUpdate(instance);
			log.info("attach successful");
		} catch (RuntimeException re) {
			log.error("Attach failed!", re);
			throw re;
		}
	}

	public void delete(Object persistentInstance) {
		log.info("deleting " + getClassName(persistentInstance) + " instance");
		try {
			session.delete(persistentInstance);
			log.info("delete successful");
		} catch (RuntimeException re) {
			log.error("Delete failed!", re);
			throw re;
		}
	}

	public Object merge(Object detachedInstance) {
		log.info("merging " + getClassName(detachedInstance) + " instance");
		try {
			Object result = session.merge(detachedInstance);
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
			Object instance = session.get("pojo.classes." + pojoClass, (java.io.Serializable) id);
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
			String pojoClass = getClassName(instance);
			@SuppressWarnings("unchecked")
			List<Object> results = session.createCriteria(pojoClass).add(Example.create(instance)).list();
			log.info("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("Find by example failed!", re);
			throw re;
		}
	}

	public List<Object> findByExample(Object instance, Map<Object, List<Object>> hm) {
		log.info("finding " + getClassName(instance) + " instance by example");
		try {
			String pojoClass = getClassName(instance);
			Criteria criteria = session.createCriteria(pojoClass);
			Example example = Example.create(instance);
			criteria.add(example);
			for (Object propertyName : hm.keySet()) {
				List<Object> valueLs = hm.get(propertyName);
				criteria.add(Restrictions.in((String)propertyName, valueLs));
			}
			@SuppressWarnings("unchecked")
			List<Object> results = criteria.list();
			log.info("find by criteria successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("Find by criteria failed!", re);
			throw re;
		}
	}

	public void evict(Object object){
		log.info("Evicting " + object);
		try {
			session.evict(object);
		} catch (RuntimeException re) {
			log.error("Evict failed!", re);
			throw re;
		}
	}

	public Map<Enumerations, String> getEnumeration(String type){

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

	public List<?> searchByConstaint(String dBTableName, Map<String, String> constraint){	
		@SuppressWarnings("rawtypes")
		List result = new ArrayList();
		try{

			if(!constraint.isEmpty()){

				String key, value, concat;
				String searchQuery = "from " + dBTableName + " where ";
				Iterator<String> queryIterator = constraint.keySet().iterator();
				Iterator<String> constrIterator = constraint.keySet().iterator();

				while (queryIterator.hasNext()) {
					key = queryIterator.next();			
					concat = key + " like :" + key;
					searchQuery += concat;
					if (queryIterator.hasNext())
						searchQuery += " and ";		
				}  
				Query query = session.createQuery(searchQuery);
				while (constrIterator.hasNext()) {
					key = constrIterator.next();
					value = constraint.get(key);
					query.setParameter(key, value);
				}

				result =  query.list();	
			}

		} catch (RuntimeException re) {
			log.error("Method searchByConstaint/2 of class DaoImpl failed!", re);	
		}

		return result;
	}

	public String toString(Object pojoObject) {
		StringBuilder stringBuilder = new StringBuilder();
		try {
			//@SuppressWarnings("all")
			Class<?> className = Class.forName(pojoObject.getClass().getName());		
			Method methodsArray[] = className.getDeclaredMethods();		
			Object propertyValue;
			String valueStr;
			for (int i = 0; i < methodsArray.length; i++)
				if (methodsArray[i].getName().startsWith("get")) {
					propertyValue = methodsArray[i].invoke(pojoObject);
					try{
						valueStr = ((Enumerations)propertyValue).getCode();
					} catch (Exception e){
						valueStr = String.valueOf(propertyValue);
					}
					stringBuilder.append(methodsArray[i].getName().substring(3) + ": "
							+ valueStr + "\n");
				}
		} catch (Throwable e) {
			System.err.println(e);
		}
		return stringBuilder.toString();
	}

	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException{

		Date birthDate = new Date();
		DaoImpl daoImpl = new DaoImpl();

		Enumerations jobTitle = (Enumerations) daoImpl.findById("Enumerations", "md");

		Random rand = new Random();
		Integer number = Math.abs(rand.nextInt());
		String numberStr = number.toString();

	}


}
