
import interfaces.Contact;
import interfaces.ContactManager;
import interfaces.Meeting;

import org.junit.*;

import static org.junit.Assert.*;
import sourcecode.ContactImpl;
import sourcecode.ContactManagerImpl;
import sourcecode.FutureMeetingImpl;
import sourcecode.MeetingImpl;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import interfaces.FutureMeeting;

public class ContactManagerTest {
	private ContactManager test1;
	private Calendar date1;
	private Set<Contact> contacts;
	private Contact simon;
	private Contact nick;
	private Contact edgar;
	private Contact scott;

	@Before
	public void buildUp() {
		test1 = new ContactManagerImpl();
		simon = new ContactImpl("Simon Pegg", "Recovering from Paul");
		nick = new ContactImpl("Nick Frost", "Also recovering from Paul");
		edgar = new ContactImpl("Edgar Wright", "Is glad to not have worked on Paul");
		scott = new ContactImpl("Scott Pilgrim");
		date1 = new GregorianCalendar(2015, 03, 05);
		contacts = new HashSet<Contact>();
		contacts.add(simon);
		contacts.add(nick);
		contacts.add(edgar);
		contacts.add(scott);
	}
	
	@After
	public void cleanUp() {
		MeetingImpl.count = 0;
		ContactImpl.count = 0;
	}
	
	//@Test
	//public void testAddFutureMeeting() { //works
		//int expected = 1;
		//int actual = test1.addFutureMeeting(contacts, date1);
		//assertEquals(expected, actual);
	//}
	
	//@Test(expected = IllegalArgumentException.class) //works
	//public void testInvalidDate() {
		//date1 = new GregorianCalendar(2013, 1, 1);
		//test1.addFutureMeeting(contacts, date1);
	//}
	
	//@Test(expected = IllegalArgumentException.class)
	//public void testInvalidContact() {
		
	//}
	
	@Test
	public void testAddContacts() {
		String name = "Simon";
		String notes = "Test";
		test1.addNewContact(name, notes);
		assertFalse(((ContactManagerImpl)test1).isEmpty());
	}

}
