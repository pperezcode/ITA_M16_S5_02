package com.jocdedaus.apidaus.model.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.jocdedaus.apidaus.model.entity.Jugador;
import com.jocdedaus.apidaus.model.entity.Partida;

@Component
public interface JocDausService {

	void jugarPartidaJugadorById(Integer idJugador, Partida partida);

	int eliminarPartidesJugadorId(Integer idJugador);

	boolean existsJugadorByNom(String nom);

	boolean existsJugadorById(Integer idJugador);

	double getPercentatgeExit(Integer idJugador);
	
	
	Jugador crearJugador(Jugador jugador);

	Jugador updateJugador(Jugador jugador);

	Jugador loser();

	Jugador winner();

	List<Jugador> llistaJugadorsExit();

	List<Jugador> llistaRankingJugadors();

	List<Partida> partidesJugadorById(Integer idJugador);

}
