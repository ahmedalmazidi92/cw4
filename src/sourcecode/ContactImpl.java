package sourcecode;
import java.io.Serializable;

import interfaces.Contact;
/**
 * Implementation of the interface Contact
 * @author Ahmed
 * 
 * A contact is a person we are making business with or may
 * do in the future.
 * 
 * The static variable count is used to represent the number
 * of objects of type ContactImpl that have been created. It
 * is also used to assign a unique ID to the contact.
 *
 */
public class ContactImpl implements Contact, Serializable{
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
	
	/**
	 * Returns the ID of the contact.
	 * 
	 * @return the ID of the contact.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Returns the name of the contact.
	 * 
	 * @return the name of the contact.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns our notes about the contact, if any.
	 * 
	 * If we have not written anything about the contact,
	 * an empty string is returned
	 * 
	 * @return a string with notes about the contact, maybe empty
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * Add notes about the contact.
	 * 
	 * @param note the notes to be added
	 */
	public void addNotes(String note) {
		this.notes = note;
		
	}
	
	/**
	 * Sets the ID of the contact
	 * 
	 * @param id the ID to be set for the contact
	 */
	public void setId(int id) {
		this.id = id;
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
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return ("(ID: " + getId() + ", Name: " + getName() + ", Notes: " + getNotes() + ")");
	}

}
