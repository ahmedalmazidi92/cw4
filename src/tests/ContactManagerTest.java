package tests;
import interfaces.Contact;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

import interfaces.FutureMeeting;

public class ContactManagerTest {
	private FutureMeeting test1;
	private Calendar date1;
	private Set<Contact> contacts1;
	private 

	public ContactManagerTest() {
		date1 = new GregorianCalendar(2015, 03, 05);
		contacts1 = new HashSet<Contact>();
		test1 = new FutureMeetingImpl() 
	}

}
