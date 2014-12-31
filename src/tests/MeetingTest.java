package tests;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Set;

import interfaces.Contact;
import interfaces.Meeting;
import sourcecode.ContactImpl;
import sourcecode.MeetingImpl;
import static org.junit.Assert.*;

import org.junit.*;

public class MeetingTest {
	private Meeting test1;
	private Meeting test2; 
	private Meeting test3;
	private Meeting test4;
	private Set<Contact> contacts;
	private Calendar date;
	
	@Before
	public void buildUp() {
		contacts.add(new ContactImpl("Simon Pegg"));
		contacts.add(new ContactImpl("Nick Frost"));
	}
	
	@Test
	public void testID() {
		int expected = 1;
		int actual = test1.getId();
		assertEquals(expected, actual);
		
		expected = 2;
		actual = test2.getId();
		assertEquals(expected, actual);
		
		expected = 3;
		actual = test3.getId();
		assertEquals(expected, actual);
		
		expected = 4;
		actual = test4.getId();
		assertEquals(expected, actual);
	}

}
