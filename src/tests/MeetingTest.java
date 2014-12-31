
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
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
	private Set<Contact> contacts1 = new HashSet<Contact>();
	private Set<Contact> contacts2 = new HashSet<Contact>();
	private Set<Contact> contacts3 = new HashSet<Contact>();
	private Set<Contact> contacts4 = new HashSet<Contact>();
	private Calendar date1;
	private Calendar date2;
	private Calendar date3;
	private Calendar date4;
	private Contact simon = new ContactImpl("Simon Pegg");
	private Contact nick = new ContactImpl("Nick Frost");
	private Contact edgar = new ContactImpl("Edgar Wright", "Antman");
	private Contact marvel = new ContactImpl("Marvel Execs", "Thoughts about changing director");
	
	@Before
	public void buildUp() {
		contacts1.add(simon);
		contacts1.add(nick);
		contacts2.add(edgar);
		contacts2.add(marvel);
		contacts3.add(simon);
		contacts3.add(edgar);
		contacts4.add(nick);
		contacts4.add(marvel);
		date1 = new GregorianCalendar(2014, 11, 5);
		date2 = new GregorianCalendar(2014, 12, 25);
		date3 = new GregorianCalendar(2014, 12, 31);
		date4 = new GregorianCalendar(2015, 1, 1);
		test1 = new MeetingImpl(date1, contacts1);
		test2 = new MeetingImpl(date2, contacts2);
		test3 = new MeetingImpl(date3, contacts3);
		test4 = new MeetingImpl(date4, contacts4);
	}
	
	@After
	public void cleanUp() {
		MeetingImpl.count = 0;
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
