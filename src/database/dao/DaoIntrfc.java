package database.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;

import database.pojo.Enumerations;




public interface DaoIntrfc {

	public Session getSession();

	public Transaction getTransaction();

	public void persist(Object transientInstance);

	public void saveOrUpdate(Object instance);

	public void delete(Object persistentInstance);

	public Object merge(Object detachedInstance);

	public Object findById(String pojoClass, Object id);

	public List<Object> findByExample(Object instance);

	public List<Object> findByExample(Object instance, Map<Object, List<Object>> hm);

	public void evict(Object object);

	public void clear();

	public Map<Enumerations, String> getEnumeration(String type);

	public List<?> searchByConstaint(String dBTableName, Map<String, String> constraint);

	public List<?> searchByConstraintsWithOp(String dBTableName, Map<String, ArrayList<Object>> constraint);

	public String toString(Object pojoObject);

}
