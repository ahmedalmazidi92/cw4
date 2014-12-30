
import interfaces.Contact;
import org.junit.*;
import sourcecode.ContactImpl;
import static org.junit.Assert.*;

public class ContactTest {
	private Contact test1 = new ContactImpl("Simon Pegg");
	private Contact test2 = new ContactImpl("Nick Frost", "Shaun of the Dead");
	private Contact test3 = new ContactImpl("Edgar Wright", "Hot Fuzz");
	private Contact test4 = new ContactImpl("Scott Pilgrim");
	
	@After
	public void cleanUp() {
		ContactImpl.count = 0;
	}
	
	
	@Test
	public void testName() { //works
		String expected = "Simon Pegg";
		String actual = test1.getName();
		assertEquals(expected, actual);
		
		expected = "Nick Frost";
		actual = test2.getName();
		assertEquals(expected, actual);
		
		expected = "Edgar Wright";
		actual = test3.getName();
		assertEquals(expected, actual);
		
		expected = "Scott Pilgrim";
		actual = test4.getName();
		assertEquals(expected, actual);
	}
	
	@Test
	public void testID() { //works
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
	
	//@Test
	//public void testNotes() {
		//String actual = test1.getNotes();
		//assertNull(actual);
		
		//String expected = "Shaun of the Dead";
		//actual = test2.getNotes();
		//assertEquals(expected, actual);
		
		//expected = "Hot Fuzz";
		//actual = test3.getNotes();
		//assertEquals(expected, actual);
		
		//actual = test4.getNotes();
		//assertNull(actual);
	//}
}
