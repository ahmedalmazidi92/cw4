package sourcecode;
import java.util.Calendar;
import java.util.Set;

import interfaces.Contact;
import interfaces.PastMeeting;

public class PastMeetingImpl extends MeetingImpl implements PastMeeting {
	String notes;

	public PastMeetingImpl(Calendar date, Set<Contact> contacts) {
		super(date, contacts);
		this.notes = "";
		
	}

	public String getNotes() {
		return notes;
	}
	
	public void addNotes(String notes) {
		this.notes = notes;
	}

}
