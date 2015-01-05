package sourcecode;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Set;

import interfaces.Contact;
import interfaces.Meeting;
/**
 * 
 * @author Ahmed Almazidi
 * An implementation of the interface Meeting
 * 
 * A Class to represent meetings.
 * Static variable count represents the number of objects of type
 * that have been created. It is also used to assign a unique ID
 * to the meetings
 * 
 */
public class MeetingImpl implements Meeting, Comparator<Meeting>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2885050652332298626L;
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
	/**
	 * Returns the ID of the meeting.
	 * 
	 * @return the ID of the meeting.
	 */
	public int getId() {
		return id;
	}
	/**
	 * Returns the date of the meeting.
	 * 
	 * @return the date of the meeting.
	 */
	public Calendar getDate() {
		return date;
	}
	/**
	 * Return the detials of people that attended the meeting.
	 * 
	 * The list contains a minimum of one contact and may contain an arbitrary
	 * number of them
	 * 
	 * @return the details of people that attended the meeting.
	 */
	public Set<Contact> getContacts() {
		return contacts;
	}
	/**
	 * Sets the ID for the meeting.
	 * 
	 * @param id the ID to be set for the meeting
	 */
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
	
	@Override
	public String toString() {
		return ("Meeting [ID: " + getId() + ", Date: " + (getDate().get(Calendar.DATE)) + (getDate().get(Calendar.MONTH)) + (getDate().get(Calendar.YEAR)) 
				+ ", Contacts: " + getContacts());
	}
}
