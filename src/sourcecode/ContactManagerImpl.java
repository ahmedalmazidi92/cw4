package sourcecode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import interfaces.Contact;
import interfaces.ContactManager;
import interfaces.FutureMeeting;
import interfaces.Meeting;
import interfaces.PastMeeting;

public class ContactManagerImpl implements ContactManager {
	private Set<Contact> currentContacts;
	private List<Meeting> meetings;
	private Calendar currentDate;
	
	public ContactManagerImpl() {
		this.currentContacts = new HashSet<Contact>();
		this.meetings = new ArrayList<Meeting>();
		this.currentDate = new GregorianCalendar();
	}
	

	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		Predicate<Contact> matchContact = (c) -> c.equals(contacts);
		currentContacts.stream().forEach((c) -> {
			if(!matchContact.test(c)) {
				throw new IllegalArgumentException("Unknown contacts");
			}
		});
		
		if  (date.before(currentDate)) {
			throw new IllegalArgumentException("Please use an appropriate date");
		} else {
			FutureMeeting result = new FutureMeetingImpl(date, contacts);
			return result.getId();
		}
	}

	@Override
	public PastMeeting getPastMeeting(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FutureMeeting getFutureMeeting(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Meeting getMeeting(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Meeting> getFutureMeetingList(Contact contact) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		
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
