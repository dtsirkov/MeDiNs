package database.pojo;

// Generated Apr 4, 2015 7:32:07 PM by Hibernate Tools 4.0.0

import java.util.Date;

/**
 * TouristVisit generated by hbm2java
 */
public class TouristVisit implements java.io.Serializable {

	private Integer id;
	private Persons persons;
	private Date from;
	private Date to;
	private String resort;
	private String hotel;
	private String room;
	private String phoneNumber;

	public TouristVisit() {
	}

	public TouristVisit(Persons persons, Date from, Date to, String resort,
			String hotel, String room) {
		this.persons = persons;
		this.from = from;
		this.to = to;
		this.resort = resort;
		this.hotel = hotel;
		this.room = room;
	}

	public TouristVisit(Persons persons, Date from, Date to, String resort,
			String hotel, String room, String phoneNumber) {
		this.persons = persons;
		this.from = from;
		this.to = to;
		this.resort = resort;
		this.hotel = hotel;
		this.room = room;
		this.phoneNumber = phoneNumber;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Persons getPersons() {
		return this.persons;
	}

	public void setPersons(Persons persons) {
		this.persons = persons;
	}

	public Date getFrom() {
		return this.from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return this.to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	public String getResort() {
		return this.resort;
	}

	public void setResort(String resort) {
		this.resort = resort;
	}

	public String getHotel() {
		return this.hotel;
	}

	public void setHotel(String hotel) {
		this.hotel = hotel;
	}

	public String getRoom() {
		return this.room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}