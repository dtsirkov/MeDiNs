package dao.classes;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import pojo.classes.Contacts;
import pojo.classes.Enumerations;
import pojo.classes.PersonContactLink;
import pojo.classes.Persons;

public class DaoTest {
	public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException{

		//CREATE CONFIGURATION AND SESSION
		System.out.println();
		System.out.println("CREATE CONFIGURATION AND SESSION");

		DaoImpl daoImpl = new DaoImpl("EN");

		//CREATE PERSON
		System.out.println();
		System.out.println("CREATE PERSON");

		Enumerations jobTitle = (Enumerations)daoImpl.findById("Enumerations", "md");
		Enumerations title = (Enumerations)daoImpl.findById("Enumerations", "mrs");
		Enumerations role = (Enumerations)daoImpl.findById("Enumerations", "employe");
		Enumerations sex = (Enumerations)daoImpl.findById("Enumerations", "female");

		Random rand = new Random();
		Integer number = Math.abs(rand.nextInt());
		String numberStr = number.toString();

		Persons person = new Persons(numberStr, jobTitle, title, role, sex, "firstName", "lastName", new Date());
		daoImpl.persist(person);

		//SEARCH PERSON TEST
		System.out.println();
		System.out.println("CREATE PERSON");
		Persons searchPerson = new Persons ();
		searchPerson.setFirstName("Petko");

		List<Object> personLs = daoImpl.findByExample(searchPerson);
		for(Object pers : personLs){
			System.out.println((Persons) pers);
		}

		Object findByIdPerson = daoImpl.findById("Persons", "5910142331");
		System.out.println(daoImpl.toString(findByIdPerson));

		Object merge = daoImpl.findById("Persons", "108237289");
		System.out.println(daoImpl.toString(merge));
		((Persons) merge).setFirstName("John");
		Object merged = daoImpl.merge(merge);
		System.out.println(daoImpl.toString(merged));


		//GET ENUMERATION CODE VALUE AND LABEL
		System.out.println();
		System.out.println("GET ENUMERATION CODE VALUE AND LABEL");

		Map<Enumerations, String> jobTitleEnumLs = daoImpl.getEnumeration("job title");

		for (Map.Entry<Enumerations, String> entry : jobTitleEnumLs.entrySet()) {
			Enumerations enumeration = entry.getKey();
			String label = entry.getValue();
			System.out.println(enumeration + " = " + label);
		} 

		//SEARCH BY CONTACT
		System.out.println();
		System.out.println("SEARCH BY CONTACT");
		Contacts contact = (Contacts)daoImpl.findById("Contacts", 2);

		Map<Object, List<Object>> hmContact = new HashMap<Object, List<Object>>();

		List<Object> contactsLs = new ArrayList<Object>();

		contactsLs.add(contact);

		hmContact.put("contacts", contactsLs);

		List<Object> linkLs = daoImpl.findByExample(new PersonContactLink(), hmContact);

		for(Object link : linkLs){
			System.out.println(((PersonContactLink) link).getPersons().getFirstName());

		}
		
		Persons foundPerson = (Persons)daoImpl.findById("Persons", "411515");
		Map<Object, List<Object>> hmPerson = new HashMap<Object, List<Object>>();
		List<Object> personsLs = new ArrayList<Object>();
		personsLs.add(foundPerson);
		hmPerson.put("persons", personsLs);
		List<Object> personAddressLinks = daoImpl.findByExample(new PersonContactLink(), hmPerson);
		for(Object personAddressLink : personAddressLinks){
			System.out.println(((PersonContactLink) personAddressLink).getContacts().getAddress());
		}
		

		/*	
	((Persons) merge).setFirstName("Petko");
	daoImpl.attachDirty(merge);
		 */

	}
}
