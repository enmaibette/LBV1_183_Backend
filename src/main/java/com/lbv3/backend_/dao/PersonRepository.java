package com.lbv3.backend_.dao;

import com.lbv3.backend_.model.Person;
import org.springframework.data.repository.CrudRepository;


/**
 * PersonRepository
 * @author Lara Akgün
 * @author Enma Ronquillo
 * @version 08.11.2019
 */
public interface PersonRepository extends CrudRepository<Person, Integer> {

    Person findByUsername(String username);
}
