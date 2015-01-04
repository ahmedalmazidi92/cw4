package sourcecode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import interfaces.Contact;
import interfaces.ContactManager;
import interfaces.FutureMeeting;
import interfaces.Meeting;
import interfaces.PastMeeting;

public class ContactManagerImpl implements ContactManager {
	private Set<Contact> currentContacts;
	private List<Meeting> allMeetings;
	private Calendar currentDate;
	
	public ContactManagerImpl() {
		this.currentContacts = new HashSet<Contact>();
		this.allMeetings = new ArrayList<Meeting>();
		this.currentDate = new GregorianCalendar();
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
		Predicate<Meeting> matchID = (m) -> m.getId() == id;
		Optional<Meeting> result = allMeetings.stream().filter(matchID).findAny();
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
		Optional<Meeting> result = allMeetings.stream().filter((m) -> m.getId() == id).findAny();
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
		Optional<Meeting> result = allMeetings.stream().filter((m) -> m.getId() ==id).findAny();
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

	@Override
	public List<Meeting> getFutureMeetingList(Contact contact) {
		Set<Contact> contacts = new HashSet<Contact>();
		contacts.add(contact);
		isContactReal(contacts);
		List<Meeting> listOfMeetings = new ArrayList<Meeting>();
		
	}

	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PastMeeting> getPastMeetingList(Contact contact) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addNewContact(String name, String notes) {
		if (name == null || notes == null) {
			throw new NullPointerException("Name and/or Notes are null");
		}else {
			ContactImpl.count = currentContacts.size();
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
		// TODO Auto-generated method stub
		
	}

}
