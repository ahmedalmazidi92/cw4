package tests;
import java.util.Calendar;
import java.util.Set;
import interfaces.Meeting;
import static org.junit.Assert.*;

import org.junit.Test;

public class MeetingTest {
	private Meeting test1 = new MeetingImpl();
	private Meeting test2 = new MeetingImpl();
	private Meeting test3 = new MeetingImpl();
	
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
	}

}
