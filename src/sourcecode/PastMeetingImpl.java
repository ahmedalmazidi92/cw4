package sourcecode;
import java.util.Calendar;
import java.util.Set;

import interfaces.Contact;
import interfaces.PastMeeting;

public class PastMeetingImpl extends MeetingImpl implements PastMeeting {
	String notes;

	public PastMeetingImpl(Calendar date, Set<Contact> contacts, String notes) {
		super(date, contacts);
		this.notes = notes;
		
	}

	public String getNotes() {
		return notes;
	}

}
