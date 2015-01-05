package sourcecode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
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

public class ContactManagerImpl implements ContactManager {
	private Set<Contact> currentContacts;
	private Set<Meeting> allMeetings;
	private Calendar currentDate;
	
	public ContactManagerImpl() {
		this.currentContacts = new HashSet<Contact>();
		this.allMeetings = new HashSet<Meeting>();
		this.currentDate = new GregorianCalendar(2015, 01, 05);
	}
	

	@Override
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

	@Override
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

	@Override
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

	@Override
	public Meeting getMeeting(int id) {
		Optional<Meeting> result = isIDReal(id);
		if(!result.isPresent()){
			return null;
		}else {
			return (Meeting) result.get();
		}
	}
	
	private void isContactReal(Set<Contact> contacts) {
		Predicate<Contact> matchContact = (c) -> currentContacts.contains(c);
		if(!contacts.stream().allMatch(matchContact)) {
				throw new IllegalArgumentException("Unknown contacts");
		}
	}
	
	private Optional<Meeting> isIDReal(int id){
		return allMeetings.stream().filter((m) -> m.getId() == id).findAny();
	}

	@Override
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
		return listOfMeetings;
	}

	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {
		List<Meeting> listOfMeetings = new ArrayList<Meeting>();
		allMeetings.stream().forEach((m) -> {
			if(m.getDate() == date) {
				listOfMeetings.add(m);
			}
		});
		return listOfMeetings;
	}

	@Override
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
		return listOfMeetings;
	}

	@Override
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

	@Override
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

	@Override
	public void addNewContact(String name, String notes) {
		if (name == null || notes == null) {
			throw new NullPointerException("Name and/or Notes are null");
		}else {
			Contact newContact = new ContactImpl(name, notes);
			currentContacts.add(newContact);
		}
		
	}
	
	public boolean isEmpty() {
		return currentContacts.isEmpty();
	}

	@Override
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

	@Override
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

	@Override
	public void flush() {
		File newFile = new File("src/contacts.txt");
		if(newFile.exists()){
			try {
				FileOutputStream fos = new FileOutputStream(newFile, false);
				ObjectOutputStream output = new ObjectOutputStream(fos);
				output.writeObject()
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
