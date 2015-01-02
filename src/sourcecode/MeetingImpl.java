package sourcecode;

import java.util.Calendar;
import java.util.Set;

import interfaces.Contact;
import interfaces.Meeting;

public class MeetingImpl implements Meeting {
	private int id;
	private Calendar date;
	private Set<Contact> contacts;
	public static int count = 0;
	
	public MeetingImpl(Calendar date, Set<Contact> contacts) {
		count++;
		this.date = date;
		this.contacts = contacts;
		this.id = count;
	}
	public int getId() {
		return id;
	}
	public Calendar getDate() {
		return date;
	}
	public Set<Contact> getContacts() {
		return contacts;
	}
	
	@Override
	public boolean equals(Object meeting) {
		if(meeting instanceof Meeting) {
			Meeting result = (MeetingImpl) meeting;
			return (result.getId() == this.getId() && result.getDate() == this.getDate() && result.getContacts() == this.getContacts() ? true : false);
		}else {
			return false;
		}
	}

	@Override
	public 
}
