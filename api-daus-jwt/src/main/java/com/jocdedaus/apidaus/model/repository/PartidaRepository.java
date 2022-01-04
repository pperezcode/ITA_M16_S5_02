package com.jocdedaus.apidaus.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jocdedaus.apidaus.model.entity.Jugador;
import com.jocdedaus.apidaus.model.entity.Partida;

@Repository
public interface PartidaRepository extends JpaRepository<Partida, Integer> {

	void deleteByJugador(Jugador jugador);
		
	List<Partida> getByJugadorIdJugador(Integer idJugador);
	
	List<Partida> getByJugadorIdJugadorAndVictoria(Integer idJugador, boolean victoria);
	
}
