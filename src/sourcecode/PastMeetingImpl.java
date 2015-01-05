package sourcecode;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

import interfaces.Contact;
import interfaces.PastMeeting;

public class PastMeetingImpl extends MeetingImpl implements PastMeeting, Serializable {
	String notes;

	public PastMeetingImpl(Calendar date, Set<Contact> contacts, String notes) {
		super(date, contacts);
		this.notes = notes;
		
	}

	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	@Override
	public String toString() {
		return ("Past Meeting [ID: " + getId() + ", Date: " + (getDate().get(Calendar.DATE)) + (getDate().get(Calendar.MONTH)) + (getDate().get(Calendar.YEAR)) 
				+ ", Contacts: " + getContacts());
	}
}
