package com.cooksys.friendlr.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cooksys.friendlr.dto.PersonWithIdDto;
import com.cooksys.friendlr.exception.PersonNotFoundException;
import com.cooksys.friendlr.pojo.Person;
import com.cooksys.friendlr.repository.PersonRepository;

@Service
public class FriendlrService {

	Logger log = LoggerFactory.getLogger(getClass());
	
	PersonRepository personRepo;
	
	public FriendlrService(PersonRepository personRepo) {
		this.personRepo = personRepo;
	}
	
	public List<Person> getByFirstName(String name) {
		return personRepo.getByFirstName(name);
	}
	
	public List<Person> getByNamez(String firstName, String lastName) {
		return personRepo.getByNamez(firstName, lastName);
	}
	
	public List<Person> getAllPersons() {
		return personRepo.getAll();
	}

	public Person getPerson(Integer personId) {
		return personRepo.get(personId);
	}

	public Person createPerson(Person person) {
		return personRepo.create(person);
	}

	public Person addFriend(Integer personId, Integer friendId) {
		Person addedAFriend = personRepo.get(personId);
		addedAFriend.getFriends().add(personRepo.get(friendId));
		personRepo.update(addedAFriend);
		return addedAFriend;
	}

	public List<Person> getFriends(Integer personId) {
		return personRepo.get(personId).getFriends();
	}

}
