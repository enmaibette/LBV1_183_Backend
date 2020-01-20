package com.lbv3.backend_.dao;

import com.lbv3.backend_.model.Person;
import com.lbv3.backend_.model.Produkt;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;


/**
 * ProductRepository
 * @author Lara Akgün
 * @author Enma Ronquillo
 * @version 20.01.2020
 */
public interface ProductRepository extends CrudRepository<Produkt, Integer> {

    ArrayList<Produkt> findAll();
}
