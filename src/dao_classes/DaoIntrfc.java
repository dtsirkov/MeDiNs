package dao_classes;

import java.util.List;
import java.util.Map;

import pojo_classes.Enumerations;

public interface DaoIntrfc {

	public void persist(Object transientInstance);

	public void persist(Object[] objectArray);

	public void attachDirty(Object instance);

	public void delete(Object persistentInstance);

	public Object merge(Object detachedInstance);

	public Object findById(String pojoClass, Object id);

	public List<Object> findByExample(Object instance);

	public List<Object> findByExample(Object instance, Map<Object, List<Object>> hm);

	public Map<Enumerations, String> getEnumeration(String type);

}
