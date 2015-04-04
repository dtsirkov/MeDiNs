package dao.classes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.SessionImplementor;

import pojo.classes.Country;
import pojo.classes.Enumerations;
import pojo.classes.Persons;

import beans.ComboxBean;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

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
		
		List<Object> ls=daoImpl.findByExample(country);

		session.close();
		
	}
	
	public static List getSearchedObjectsList(String searchedObjectClassName,Map<String, String> constr,Session session){		
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
