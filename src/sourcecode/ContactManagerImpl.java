package sourcecode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import interfaces.Contact;
import interfaces.ContactManager;
import interfaces.FutureMeeting;
import interfaces.Meeting;
import interfaces.PastMeeting;
/**
 * An implementation of the interface ContactManager
 * @author Ahmed
 * 
 * A class to manager your contacts and meetings.
 */
public class ContactManagerImpl implements ContactManager, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5459765702170662576L;
	private Set<Contact> currentContacts;
	private List<Meeting> allMeetings;
	private Calendar currentDate;
	
	/**
	 * The constructor method checks to see if there is a file with name
	 * 'contacts.txt'. If so it will import the set of both contacts and meetings.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public ContactManagerImpl() {
		this.currentContacts = new HashSet<Contact>();
		this.allMeetings = new ArrayList<Meeting>();
		this.currentDate = new GregorianCalendar(2015, 01, 05);
		File newFile = new File("contacts.txt");
		if(newFile.exists()){
			try {
				FileInputStream fis = new FileInputStream(newFile);
				ObjectInputStream input = new ObjectInputStream(fis);
				this.allMeetings = (ArrayList<Meeting>) input.readObject();
				System.out.println("Meetings successfully added");
				this.currentContacts = (HashSet<Contact>) input.readObject();
				System.out.println("Contacts successfully added");
				input.close();
				fis.close();
			} catch (IOException e){
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	

	/**
	 * Add a new meeting to be held in the future
	 * 
	 * @param contacts a list of contacts that will participate in the meeting
	 * @param date the date on which the meeting will take place
	 * @return the ID for the meeting
	 * @throws IllegalArgumentException if the meeting is set for a time in the past,
	 * 		or if any contact is unknown / non-existent
	 */
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		isContactReal(contacts);
		if  (date.before(currentDate)) {
			throw new IllegalArgumentException("Please use an appropriate date");
		} else {
			FutureMeeting result = new FutureMeetingImpl(date, contacts);
			allMeetings.add(result);
			return result.getId();
		}
	}

	/**
	 * Returns the PAST meeting with the requested ID, or null if there is none
	 * 
	 * @param id the ID for the meeting
	 * @return the meeting with the requested ID, or null if there is none
	 * @throws IllegalArgumentException if there is a meeting with that ID happening
	 * in the future
	 */
	public PastMeeting getPastMeeting(int id) {
		Optional<Meeting> result = isIDReal(id);
		if(!result.isPresent()) {
			return null;
		}else if(result.get() instanceof FutureMeeting) {
			throw new IllegalArgumentException("ID " + id + " corresponds to a Future Meeting");
		}else {
			return (PastMeeting) result.get();
		}
	}

	/**
	 * Returns the FUTURE meting with the requested ID, or null if there is none
	 * 
	 * @param id the ID for the meeting
	 * @return the meeting with the requested ID, or null if there is none
	 * @throws IllegalArgumentException if there is a meeting with that ID happening in the past
	 */
	public FutureMeeting getFutureMeeting(int id) {
		Optional<Meeting> result = isIDReal(id);
		if(!result.isPresent()) {
			return null;
		}else if(result.get()instanceof PastMeeting) {
			throw new IllegalArgumentException("ID " + id + " corresponds to a Past Meeting");
		}else{
			return (FutureMeeting) result.get();
		}
	}

	/**
	 * Returns the meeting with the requested ID, or null if there none
	 * 
	 * @param id the ID for the meeting
	 * @return the meeting with the requested ID, or null if there is none
	 * @throws IllegalArgumentException if there is a meeting with that ID happening in the past
	 */
	public Meeting getMeeting(int id) {
		Optional<Meeting> result = isIDReal(id);
		if(!result.isPresent()){
			return null;
		}else {
			return (Meeting) result.get();
		}
	}
	/**
	 * Checks to see if the set of contacts provided do exist in the set of all contacts
	 * 
	 * @param contacts the set of contacts to be checked
	 */
	private void isContactReal(Set<Contact> contacts) {
		Predicate<Contact> matchContact = (c) -> currentContacts.contains(c);
		if(!contacts.stream().allMatch(matchContact)) {
				throw new IllegalArgumentException("Unknown contacts");
		}
	}
	/**
	 * Returns the meeting the provided ID corresponds to, or empty Optional is there are none
	 * 
	 * @param id the ID to be checked on
	 * @return Optional containing the the meeting that corresponds to the ID, or an empty Optional
	 * if there are none
	 */
	private Optional<Meeting> isIDReal(int id){
		return allMeetings.stream().filter((m) -> m.getId() == id).findAny();
	}

	/**
	 * Returns the list of future meetings scheduled with this contact.
	 * 
	 * If there are none, the returned list will be empty. Otherwise, the list will be
	 * chronologically sorted and will not contain any duplicates.
	 * 
	 * @param contact one of the user's contacts
	 * @return the list of future meeting(s) schedules with this contact (maybe empty)
	 * @throws IllegalArgumentException if the contact does not exist
	 */
	public List<Meeting> getFutureMeetingList(Contact contact) { 
		Set<Contact> contacts = new HashSet<Contact>();
		contacts.add(contact);
		isContactReal(contacts);
		List<Meeting> listOfMeetings = new ArrayList<Meeting>();
		allMeetings.stream().forEach((m) -> {
			if(m.getContacts().contains(contact) && m instanceof FutureMeeting){
				listOfMeetings.add(m);
			}
		});
		Collections.sort(listOfMeetings, new MeetingImpl(currentDate, contacts));
		MeetingImpl.count --;
		return listOfMeetings;
	}

	/**
	 * Returns the list of meetings that are scheduled for, or that took place on the
	 * specified date
	 * 
	 * If there are none, the returned list will be empty. Otherwise, the list will be
	 * chronologically sorted and will not contain any duplicates.
	 * 
	 * @param date the date
	 * @return the list of meetings
	 */
	public List<Meeting> getFutureMeetingList(Calendar date) {
		List<Meeting> listOfMeetings = new ArrayList<Meeting>();
		allMeetings.stream().forEach((m) -> {
			if(m.getDate() == date) {
				listOfMeetings.add(m);
			}
		});
		Collections.sort(listOfMeetings, new MeetingImpl(currentDate, getContacts(1)));
		MeetingImpl.count --;
		return listOfMeetings;
	}

	/**
	 * Returns the list of meetings that are scheduled for, or that took place on, the
	 * specified date
	 * 
	 * If there are none, the returned list will be empty. Otherwise, the list will be
	 * chronologically sorted and will not contain any duplicates.
	 * 
	 * @param contact one of the user's contacts
	 * @return the list of future meeting(s) scheduled with this contact (maybe empty)
	 * @throws IllegalArgumentException if the contact does not exist
	 */
	public List<PastMeeting> getPastMeetingList(Contact contact) {
		Set<Contact> contacts = new HashSet<Contact>();
		contacts.add(contact);
		isContactReal(contacts);
		List<PastMeeting> listOfMeetings = new ArrayList<PastMeeting>();
		allMeetings.stream().forEach((m) -> {
			if(m.getContacts().contains(contact) && m instanceof PastMeeting){
				listOfMeetings.add((PastMeeting) m);
			}
		});
		Collections.sort(listOfMeetings, new MeetingImpl(currentDate, getContacts(1)));
		MeetingImpl.count --;
		return listOfMeetings;
	}

	/**
	 * Create a new record for meeting that took place in the past.
	 * 
	 * @param contacts a list of participants
	 * @param date the date on which the meeting took place
	 * @param text messages to be added about the meeting.
	 * @throws IllegalArgumentException if the list of contacts is empty, or any of 
	 * 		the contacts does not exist
	 * @throws NullPointerException if any of the arguments is null
	 */
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date,
			String text) {
		if(contacts.isEmpty()){
			throw new IllegalArgumentException("The list of contacts provided is empty");
		}
		isContactReal(contacts);
		if (contacts == null || date == null || text == null){
			throw new NullPointerException("Null parameters are not accepted");
		}else {
			PastMeeting result = new PastMeetingImpl(date, contacts, text);
			allMeetings.add(result);
		}
		
	}

	/**
	 * Add notes to a meeting.
	 * 
	 * This method is used when a future meeting takes place, and is then converted to a past
	 * meeting (with notes)
	 * 
	 * It can be also used to add notes to a past meeting at a later date
	 * 
	 * @param id the ID of the meeting
	 * @param text messages to be added about the meeting.
	 * @throws IllegalArgumentException if the meeting does not exist
	 * @throws IllegalStateException if the meeting is set for a date in the future
	 * @throws NullPointerException if the notes are null;
	 */
	public void addMeetingNotes(int id, String text) {
		Optional<Meeting> result = isIDReal(id);
		if(text == null) {
			throw new NullPointerException("Null String is not an appropriate parameter");
		}else if (!result.isPresent()) {
			throw new IllegalArgumentException("Meeting with ID " + id + " does not exist");
		}else if(result.get() instanceof PastMeeting){
			((PastMeetingImpl) result.get()).setNotes(text);
		}else if (result.get().getDate().after(currentDate)) {
			throw new IllegalStateException("Meeting with ID " + id + " has not occured yet");
		}else {
			addNewPastMeeting(result.get().getContacts(), result.get().getDate(), text);
			((MeetingImpl) getPastMeeting(MeetingImpl.count)).setId(MeetingImpl.count - 1);
			MeetingImpl.count--;
			allMeetings.remove(result.get());
		}
		
	}

	/**
	 * Create a new contact with the specified name and notes.
	 * 
	 * @param name the name of the contact
	 * @param notes notes to be added about the contact
	 * @throws NullPointerException if the name or the notes are null
	 */
	public void addNewContact(String name, String notes) {
		if (name == null || notes == null) {
			throw new NullPointerException("Name and/or Notes are null");
		}else {
			Contact newContact = new ContactImpl(name, notes);
			currentContacts.add(newContact);
		}
		
	}
	

	/**
	 * Returns a list with the contacts that correspond to the IDs.
	 * 
	 * @param ids an arbitrary number of contact IDs
	 * @return a list containing the contacts that correspond to the IDs
	 * @throws IllegalArgumentException if any of the IDs does not correspond to a real contact.
	 */
	public Set<Contact> getContacts(int... ids) {
		Set<Contact> result = new HashSet<Contact>();
		for (int id : ids){
			for (Contact contact : currentContacts) {
				if (currentContacts.stream().anyMatch((c) -> c.getId() == id)) {
					result.add(contact);
				}else {
					throw new IllegalArgumentException("ID: " + id + " does not match any contact");
				}
			}
		}
		return result;
	}

	/**
	 * Returns a list with the contacts whose name contains that string.
	 * 
	 * @param name the string to search for
	 * @return a list with the contacts whose name contains that string
	 * @throws NullPointerException if the parameter is null
	 */
	public Set<Contact> getContacts(String name) {
		if(name == null) {
			throw new NullPointerException("Null is not accepted as a name");
		}else {
			Set<Contact> result = new HashSet<Contact>();
			Predicate<Contact> matchString = (c) -> c.getName().equals(name);
			currentContacts.stream().forEach((c) ->{
				if(matchString.test(c)) {
					result.add(c);
				}
			});
			return result;
		}
	}

	/**
	 * Save all data to disk
	 * 
	 * This method must be executed when the program is closed
	 * and when/if the user requests it.
	 */
	public void flush() {
		File newFile = new File("contacts.txt");
		if(newFile.exists()){
			newFile.delete();
		}else {
			try {
				FileOutputStream fos = new FileOutputStream(newFile);
				ObjectOutputStream output = new ObjectOutputStream(fos);
				output.writeObject(currentContacts);
				System.out.println("Contacts successfully written");
				output.writeObject(allMeetings);
				System.out.println("Meeting successfully written");
				output.close();
				fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
