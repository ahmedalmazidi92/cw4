package sourcecode;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Set;

import interfaces.Contact;
import interfaces.Meeting;

public class MeetingImpl implements Meeting, Comparator<Meeting>, Serializable {
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
	
	public void setId(int id) {
		this.id = id;
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
	public int hashCode() {
		int code;
		String hashString = "" + this.getId() + this.getContacts() + this.getDate();
		code = hashString.hashCode();
		return code;
	}
	@Override
	public int compare(Meeting o1, Meeting o2) {
		Calendar date1 = o1.getDate();
		Calendar date2 = o2.getDate();
		if(date1.before(date2)){
			return -1;
		}else if(date1.after(date2)){
			return 1;
		}
		
		return 0;
	}
}
