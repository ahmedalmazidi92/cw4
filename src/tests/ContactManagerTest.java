
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
	}
	
	@After
	public void cleanUp() {
		MeetingImpl.count = 0;
		ContactImpl.count = 0;
	}
	
	@Test
	public void testAddFutureMeeting() { 
		Contact simon = new ContactImpl("Simon Pegg", "Still recovering from Paul");
		contacts.add(simon);
		test1.addNewContact(simon); //New method to be implemented		
		int expected = 1;
		int actual = test1.addFutureMeeting(contacts, date1);
		assertEquals(expected, actual);
	}
	
	@Test(expected = IllegalArgumentException.class) 
	public void testInvalidDate() {
		date1 = new GregorianCalendar(2013, 1, 1);
		test1.addFutureMeeting(contacts, date1);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInvalidContact() {
		
	}
	
	@Test
	public void testAddContacts() { 
		String name = "Simon";
		String notes = "Test";
		test1.addNewContact(name, notes);
		assertFalse(((ContactManagerImpl)test1).isEmpty());
	}
	
	@Test(expected = NullPointerException.class) //works
	public void testForNullPointerException() {
		test1.addNewContact(null, null);
	}

}
