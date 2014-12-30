package tests;
import interfaces.Contact;
import sourcecode.ContactImpl;
import org.junit.*;
import static org.junit.Assert.*;

public class ContactTest {
	private Contact test1;
	private Contact test2;
	private Contact test3;
	private Contact test4;
	
	@Before
	public void buildUp() {
		test1 = new ContactImpl("Simon Pegg");
		test2 = new ContactImpl("Nick Frost", "Shaun of the Dead");
		test3 = new ContactImpl("Edgar Wright", "Hot Fuzz");
		test4 = new ContactImpl("Scott Pilgrim");
	}
	
	@Test
	public void testName() {
		String expected = "Simon Pegg";
		String output = test1.getName();
		assertEquals(expected, output);
		
		expected = "Nick Frost";
		output = test2.getName();
		assertEquals(expected, output);
		
		expected = "Edgar Wright";
		output = test3.getName();
		assertEquals(expected, output);
		
		expected = "Scott Pilgrim";
		output = test4.getName();
		assertEquals(expected, output);
	}
}
