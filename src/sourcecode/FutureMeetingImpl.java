package sourcecode;

import interfaces.Contact;
import interfaces.FutureMeeting;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;

public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting, Serializable {

	public FutureMeetingImpl(Calendar date, Set<Contact> contacts) {
		super(date, contacts);
	}
	
	@Override
	public String toString() {
		return ("Future Meeting [ID: " + getId() + ", Date: " + (getDate().get(Calendar.DATE)) + (getDate().get(Calendar.MONTH)) + (getDate().get(Calendar.YEAR)) 
				+ ", Contacts: " + getContacts());
	}
}
