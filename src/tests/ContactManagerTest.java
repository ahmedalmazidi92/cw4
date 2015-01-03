
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

	@Before
	public void buildUp() {
		test1 = new ContactManagerImpl();
		date1 = new GregorianCalendar(2015, 03, 05);
		contacts = new HashSet<Contact>();
		test1.addNewContact("Simon Pegg", "Paul");
		test1.addNewContact("Nick Frost", "");
		test1.addNewContact("Edgar Wright", "Antman");
	}
	
	@After
	public void cleanUp() {
		MeetingImpl.count = 0;
		ContactImpl.count = 0;
	}
	
	//@Test
	//public void testAddFutureMeeting() { 
		//test1.addNewContact("Simon Pegg", "Still recovering from Paul");	
		//int expected = 1;
		//int actual = test1.addFutureMeeting(contacts, date1);
		//assertEquals(expected, actual);
	//}
	
	//@Test(expected = IllegalArgumentException.class) 
	//public void testInvalidDate() {
		//date1 = new GregorianCalendar(2013, 1, 1);
		//test1.addFutureMeeting(contacts, date1);
	//}
	
	//@Test(expected = IllegalArgumentException.class)
	//public void testInvalidContact() {
		
	//}
	
	@Test
	public void testAddContacts() { //works
		String name = "Simon";
		String notes = "Test";
		test1.addNewContact(name, notes);
		assertFalse(((ContactManagerImpl)test1).isEmpty());
	}
	
	@Test(expected = NullPointerException.class) //works
	public void testForNullPointerException() {
		test1.addNewContact(null, null);
	}
	
	@Test
	public void testGetContactswithIDs() { //works
		ContactImpl.count = 0;
		contacts.add(new ContactImpl("Simon Pegg", "Paul"));
		contacts.add(new ContactImpl("Nick Frost", ""));
		contacts.add(new ContactImpl("Edgar Wright", "Antman"));
		Set<Contact> actual = test1.getContacts(1, 2, 3);
		assertEquals(contacts, actual);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetContactwithIDsException() {
		Set<Contact> actual = test1.getContacts(1, 2, 4);
	}
	
	@Test
	public void testGetContactWithString() {
		ContactImpl.count = 0;
		contacts.add(new ContactImpl("Simon Pegg", "Paul"));
		Set<Contact> actual = test1.getContact("Simon Pegg");
		assertEquals(contacts, actual);
	}

}
