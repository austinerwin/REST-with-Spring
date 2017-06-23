package com.cooksys.friendlr.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.cooksys.friendlr.pojo.Person;

@Repository
public class PersonRepository {

	EntityManager em;
	
	public PersonRepository(EntityManager em) {
		this.em = em;
	}
	
	public Person get(Integer id) {
		return em.find(Person.class, id);
	}
	
	public List<Person> getByFirstName(String name) {
		return em
				.createQuery("from Person where firstName='" + name +"'", Person.class)
				.getResultList();
	}

	public List<Person> getByNamez(String first, String last) {
		
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Person> criteria = builder.createQuery(Person.class);
		Root<Person> root = criteria.from(Person.class);

		return em.createQuery(
				criteria.where(
						builder.or(
								builder.equal(root.get("firstName"), first), 
								builder.equal(root.get("lastName"), last)
								)
						)
				).getResultList();
	}
	
	@Transactional
	public Person create(Person person) {
		em.persist(person);
		return person;
	}

	public List<Person> getAll() {
		return em.createQuery("from Person", Person.class).getResultList();
	}

	@Transactional
	public void update(Person addedAFriend) {
		em.merge(addedAFriend);
	}
	
}
