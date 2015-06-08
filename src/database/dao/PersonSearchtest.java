package database.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;

import database.pojo.Country;

public class PersonSearchtest {

	/**
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		Map<String, String> constr = new HashMap<String, String>();
		//constr.put("keystring", "%Nik%");
		constr.put("firstName", "John");		
		//String SQL=getPersonSearchSQL(constr);
		//System.out.println(SQL.toString());

		DaoImpl daoImpl=new DaoImpl("BG");
		Session  session=daoImpl.getSession();

		Country country=new Country();

		List<Object> ls = daoImpl.findByExample(country);
		System.out.println(ls);
		session.close();

	}

	@SuppressWarnings("unchecked")
	public static List<Object> getSearchedObjectsList(String searchedObjectClassName,Map<String, String> constr,Session session){		
		String searchSQL = "from "+searchedObjectClassName+" where ";
		Iterator<String> constrIterator = constr.keySet().iterator();
		while (constrIterator.hasNext()) {
			String key =constrIterator.next();			
			String concat = key + " like :"+key;
			searchSQL+= concat;
			if (constrIterator.hasNext()==true)
				searchSQL +=" and ";		
		}  
		Query query = session.createQuery(searchSQL);
		Iterator<String> constrIterator2 = constr.keySet().iterator();
		while (constrIterator2.hasNext()) {
			String key =constrIterator2.next();
			String value=constr.get(key);
			query.setParameter(key, value);
		}
		return query.list();		
	}

}
