package sourcecode;
import interfaces.Contact;

public class ContactImpl implements Contact{
	private int id;
	private String name;
	private String notes;
	public static int count = 0;

	public ContactImpl(String name, String notes) {
		this.name = name;
		this.notes = notes;
		count++;
		this.id = count;
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
	
	@Override 
	public boolean equals(Object contact){
		if (contact instanceof Contact) {
			Contact result =  (ContactImpl) contact;
			return (result.getName() == this.getName() && result.getNotes() == this.getNotes() && result.getId() == this.getId() ? true : false);
		}else{
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		String hashString = getName() + getNotes() + getId();
		int code = hashString.hashCode();
		return code;
	}

}
