package sourcecode;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

import interfaces.Contact;
import interfaces.PastMeeting;
/**
 * An implementation of the interface PastMeeting
 * @author Ahmed Almazidi
 *
 * A meeting that was held in the past.
 * 
 * It includes your notes about what happened and what was agreed.
 */
public class PastMeetingImpl extends MeetingImpl implements PastMeeting, Serializable {
	String notes;

	public PastMeetingImpl(Calendar date, Set<Contact> contacts, String notes) {
		super(date, contacts);
		this.notes = notes;
		
	}

	/**
	 * Returns the notes from the meeting.
	 * 
	 * If there are no notes, the empty string is returned.
	 * 
	 * @return the notes from the meeting.
	 */
	public String getNotes() {
		return notes;
	}
	
	/**
	 * Sets the notes for the meeting. If there are any,
	 * it overwrites them.
	 *  
	 * @param notes the notes to be set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	@Override
	public String toString() {
		return ("Past Meeting [ID: " + getId() + ", Date: " + (getDate().get(Calendar.DATE)) + (getDate().get(Calendar.MONTH)) + (getDate().get(Calendar.YEAR)) 
				+ ", Contacts: " + getContacts());
	}
}
