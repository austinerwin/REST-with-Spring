package com.cooksys.friendlr.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cooksys.friendlr.dto.PersonSansIdDto;
import com.cooksys.friendlr.dto.PersonWithIdDto;
import com.cooksys.friendlr.mapper.PersonMapper;
import com.cooksys.friendlr.pojo.Person;
import com.cooksys.friendlr.service.FriendlrService;

@RestController
@RequestMapping("friendlr")
public class FriendlrController {

	private FriendlrService service;
	private PersonMapper mapper;

	public FriendlrController(FriendlrService service, PersonMapper mapper) {
		this.service = service;	
		this.mapper = mapper;
	}
	
	@GetMapping("person")
	public List<PersonWithIdDto> getAll(@RequestParam(required = false, value = "firstname") String firstName, @RequestParam(required = false, value = "lastname") String lastName) {
		
		if(firstName != null || lastName != null)
			return mapList(service.getByNamez(firstName, lastName));
		
		return mapList(service.getAllPersons());
	}
	
	private List<PersonWithIdDto> mapList(List<Person> list) {
		return list.stream().map(person -> mapper.toPersonWithId(person)).collect(Collectors.toList());
	}

	@GetMapping("person/{id}")
	public PersonSansIdDto get(@PathVariable Integer id) {
		return mapper.toPersonSansId(service.getPerson(id));
	}
	
	@PostMapping("person")
	public Person post(@RequestBody PersonSansIdDto dto, HttpServletResponse response) {
		response.setStatus(HttpServletResponse.SC_CREATED);
		return service.createPerson(mapper.toPerson(dto));
	}
	
	@PostMapping("person/{personId}/friends/{friendId}")
	public PersonWithIdDto post(@PathVariable Integer personId, @PathVariable Integer friendId, HttpServletResponse response) {
		return mapper.toPersonWithId(service.addFriend(personId, friendId));
	}

	@GetMapping("person/{personId}/friends")
	public List<PersonWithIdDto> getFriends(@PathVariable Integer personId) {
		return service.getFriends(personId).stream().map(person -> mapper.toPersonWithId(person)).collect(Collectors.toList());
	}
}
