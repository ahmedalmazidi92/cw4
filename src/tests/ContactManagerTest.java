
import interfaces.Contact;
import interfaces.ContactManager;
import interfaces.Meeting;
import interfaces.PastMeeting;

import org.junit.*;

import static org.junit.Assert.*;
import sourcecode.ContactImpl;
import sourcecode.ContactManagerImpl;
import sourcecode.FutureMeetingImpl;
import sourcecode.MeetingImpl;
import sourcecode.PastMeetingImpl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import interfaces.FutureMeeting;

public class ContactManagerTest {
	private ContactManager test1;
	private Calendar date1;
	private Calendar date2;
	private Calendar date3;
	private Set<Contact> contacts;

	@Before
	public void buildUp() {
		test1 = new ContactManagerImpl();
		date1 = new GregorianCalendar(2015, 03, 05);
		date2 = new GregorianCalendar(2014, 12, 31);
		date3 = new GregorianCalendar(2015, 02, 12);
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
	
	@Test
	public void testAddFutureMeeting() { //works
		ContactImpl.count = 0;
		contacts.add(new ContactImpl("Simon Pegg", "Paul"));	
		int expected = 1;
		int actual = test1.addFutureMeeting(contacts, date1);
		assertEquals(expected, actual);
	}
	
	@Test(expected = IllegalArgumentException.class) //works
	public void testInvalidDate() {
		date1 = new GregorianCalendar(2013, 1, 1);
		contacts.add(new ContactImpl("Simon Pegg", "Paul"));
		contacts.add(new ContactImpl("Nick Frost", ""));
		test1.addFutureMeeting(contacts, date1);
	}
	
	@Test(expected = IllegalArgumentException.class) //works
	public void testInvalidContact() {
		ContactImpl.count = 0;
		contacts.add(new ContactImpl ("Seemon Pegg", ""));
		test1.addFutureMeeting(contacts, date1);
	}
	
	@Test
	public void testGetFutureMeeting() { //works
		ContactImpl.count = 0;
		contacts.add(new ContactImpl("Simon Pegg", "Paul"));
		test1.addFutureMeeting(contacts, date1);
		MeetingImpl.count = 0;
		FutureMeeting expected = new FutureMeetingImpl(date1, contacts);
		FutureMeeting actual = test1.getFutureMeeting(1);
		assertEquals(expected, actual);
	}
	
	@Test(expected = IllegalArgumentException.class) //works
	public void testGetFutureMeetingException() {
		test1.addNewPastMeeting(contacts, date2, "");
		test1.getFutureMeeting(1);
	}
	
	@Test
	public void testAddAndGetNewPastMeeting() { //works
		ContactImpl.count = 0;
		contacts.add(new ContactImpl("Simon Pegg", "Paul"));
		test1.addNewPastMeeting(contacts, date2, "");
		MeetingImpl.count = 0;
		PastMeeting expected = new PastMeetingImpl(date2, contacts, "");
		PastMeeting actual = test1.getPastMeeting(1);
		assertEquals(expected, actual);
	}
	
	@Test(expected = IllegalArgumentException.class) //works
	public void testExceptionForGetPastMeeting() {
		test1.addFutureMeeting(contacts, date1);
		test1.getPastMeeting(1);
	}
	
	@Test(expected = NullPointerException.class) //works
	public void testNullExceptionForAddPastMeeting() {
		test1.addNewPastMeeting(null, date2, "");
	}
	
	@Test(expected = IllegalArgumentException.class) //works
	public void testEmptyContactsForAddNewPastMeeting() {
		test1.addNewPastMeeting(contacts, date2, "");
	}
	
	@Test(expected = IllegalArgumentException.class) //works
	public void testNonExistantContactsForAddNewPastMeeting() {
		contacts.add(new ContactImpl("Seemon Peg", ""));
		test1.addNewPastMeeting(contacts, date2, "");
	}
	
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
	
	@Test(expected = IllegalArgumentException.class) //works
	public void testGetContactwithIDsException() {
		Set<Contact> actual = test1.getContacts(1, 2, 4);
	}
	
	@Test
	public void testGetContactWithString() { //works
		ContactImpl.count = 0;
		contacts.add(new ContactImpl("Simon Pegg", "Paul"));
		Set<Contact> actual = test1.getContacts("Simon Pegg");
		assertEquals(contacts, actual);
	}
	
	@Test(expected = NullPointerException.class) //works
	public void testGetContactWithStringException() {
		String name = null;
		Set<Contact> actual = test1.getContacts(name);
	}
	
	@Test
	public void testGetMeeting() { //works
		contacts = test1.getContacts(1);
		test1.addFutureMeeting(contacts, date1);
		MeetingImpl.count = 0;
		Meeting expected = new MeetingImpl(date1, contacts);
		Meeting actual = test1.getMeeting(1);
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetFutureMeetingListWithContact() { //works
		contacts = test1.getContacts(1, 2, 3);
		test1.addFutureMeeting(contacts, date1);
		contacts = test1.getContacts(1, 2);
		test1.addFutureMeeting(contacts, date3);
		List<Meeting> expected = new ArrayList<Meeting>();
		expected.add(test1.getMeeting(1));
		expected.add(test1.getMeeting(2));
		ContactImpl.count = 0;
		Contact simon = new ContactImpl("Simon Pegg", "Paul");
		List<Meeting> actual = test1.getFutureMeetingList(simon);
		assertEquals(expected, actual);
	}
	
	@Test(expected = IllegalArgumentException.class) //works
	public void testGetFutureMeetingListException() {
		Contact seemon = new ContactImpl("Seemon Pug" , "");
		test1.getFutureMeetingList(seemon);
	}
	
	@Test
	public void testGetFutureMeetingListWithDate() { //works
		contacts = test1.getContacts(1, 2, 3);
		test1.addNewPastMeeting(contacts, date2, "");
		contacts = test1.getContacts(1, 2);
		test1.addNewPastMeeting(contacts, date2, "");
		List<Meeting> expected = new ArrayList<Meeting>();
		expected.add(test1.getMeeting(1));
		expected.add(test1.getMeeting(2));
		List<Meeting> actual = test1.getFutureMeetingList(date2);
		assertEquals(expected, actual);
		
	}
	
	@Test
	public void testGetPastMeetingList() { //works
		contacts = test1.getContacts(1, 2, 3);
		test1.addNewPastMeeting(contacts, date2, "");
		contacts = test1.getContacts(1, 2);
		test1.addNewPastMeeting(contacts, date2, "");
		List<PastMeeting> expected = new ArrayList<PastMeeting>();
		expected.add((PastMeeting) test1.getMeeting(1));
		expected.add((PastMeeting) test1.getMeeting(2));
		ContactImpl.count = 0;
		Contact simon = new ContactImpl("Simon Pegg", "Paul");
		List<PastMeeting> actual = test1.getPastMeetingList(simon);
		assertEquals(expected, actual);
	}
	
	@Test(expected = IllegalArgumentException.class) //works
	public void testGetPastMeetingListException() {
		Contact seemon = new ContactImpl("Seemon Pug" , "");
		test1.getPastMeetingList(seemon);
	}
	
	@Test
	public void testAddMeetingNotes() {
		contacts = test1.getContacts(1, 2, 3);
		test1.addNewPastMeeting(contacts, date2, "");
		String expected = "Scott Pilgrim vs The World";
		test1.addMeetingNotes(1, expected);
		String actual = test1.getPastMeeting(1).getNotes();
		assertEquals(expected, actual);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testMeetingNotExistForAddNotes() {
		test1.addMeetingNotes(1, "Movie Piracy");
	}
	
	
	
}
