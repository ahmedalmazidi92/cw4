package sourcecode;
import interfaces.Contact;

public class ContactImpl implements Contact{
	private int id;
	private String name;
	private String notes;
	private static int count = 0;

	public ContactImpl(String name, String notes) {
		this.name = name;
		this.notes = notes;
		count++;
		this.id = count;
		System.out.println(name);
		System.out.println(count);
	}
	
	public ContactImpl(String name) {
		this.name = name;
		this.notes = "";
		count++;
		this.id = count;
		System.out.println(name);
		System.out.println(count);
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getNotes() {
		return notes;
	}

	public void addNotes(String note) {
		this.notes = note;
		
	}

}
