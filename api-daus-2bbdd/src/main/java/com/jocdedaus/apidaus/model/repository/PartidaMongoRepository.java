package com.jocdedaus.apidaus.model.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.jocdedaus.apidaus.model.entity.Partida;

@Repository
public interface PartidaMongoRepository extends MongoRepository<Partida, Integer> {

	int deletePartidaByJugadorIdJugador(Integer idJugador);
	
	List<Partida> findPartidaByJugadorIdJugador(Integer idJugador);
	
	List<Partida> getByJugadorIdJugadorAndVictoria(Integer idJugador, boolean victoria);
	
}