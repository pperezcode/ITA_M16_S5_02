package com.jocdedaus.apidaus.model.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jocdedaus.apidaus.model.entity.Jugador;

@Repository
public interface JugadorRepository extends MongoRepository<Jugador, Integer> {

	List<Jugador> findByNom(String name);
	
	Jugador findFirst1ByOrderByPercentatgeExitDesc();
	
	Jugador findFirst1ByOrderByPercentatgeExitAsc();

	List<Jugador> findAllByOrderByPercentatgeExitDesc();
	
}
