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
	private Set<Contact> contacts1;
	private Set<Contact> contacts2;
	private Set<Contact> contacts3;
	private Set<Contact> contacts4;
	private Calendar date1;
	private Calendar date2;
	private Calendar date3;
	private Calendar date4;
	
	@Before
	public void buildUp() {
		contacts1.add(new ContactImpl("Simon Pegg"));
		contacts1.add(new ContactImpl("Nick Frost"));
		
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
