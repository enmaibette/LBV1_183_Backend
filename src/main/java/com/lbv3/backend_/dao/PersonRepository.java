package com.lbv3.backend_.dao;

import com.lbv3.backend_.model.Personen;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<Personen, Integer> {

    Personen findByUsername(String username);

}
