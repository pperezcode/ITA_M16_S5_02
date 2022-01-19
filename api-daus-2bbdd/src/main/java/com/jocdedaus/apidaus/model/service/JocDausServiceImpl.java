package com.jocdedaus.apidaus.model.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jocdedaus.apidaus.model.document.Partida;
import com.jocdedaus.apidaus.model.entity.Jugador;
import com.jocdedaus.apidaus.model.repository.JugadorRepository;
import com.jocdedaus.apidaus.model.repository.PartidaRepository;

@Service
public class JocDausServiceImpl implements JocDausService {

	@Autowired
	JugadorRepository jugadorRepo;
	
	@Autowired
	PartidaRepository partidaRepo;
	
//	@Override
//	public void jugarPartidaJugadorById(Integer idJugador, Partida partida) {
//		// Assignem un jugador a una partida
//		partida.setJugador(jugadorRepo.getById(idJugador));
//		
//		// Guardar la partida
//		partidaRepo.save(partida);
//		
//		// Calcular % èxit del jugador
//		double exit = getPercentatgeExit(idJugador);
//		
//		// Guardar nou percentatge èxit
//		Jugador jugador = jugadorRepo.getById(idJugador);
//		jugador.setPercentatgeExit(exit);
//		jugadorRepo.save(jugador);
//	}

	@Override
	public void jugarPartidaJugadorById(Integer idJugador, Partida partida) {
		Optional<Jugador> jugadorOpt = jugadorRepo.findById(idJugador);
		Jugador jugador;
		if (jugadorOpt.isPresent()) {
			jugador = jugadorOpt.get();
			
			// Assignar un jugador a una partida
			partida.setJugador(jugador);
						
			// Guardar la partida
			partidaRepo.save(partida);
			
			// Obtenir % èxit i guardar-lo al jugador
			jugador.setPercentatgeExit(getPercentatgeExit(idJugador));
			
			// Guardar jugador a la base de dades
			jugadorRepo.save(jugador);
			
		} else {
			System.err.println("No existeix el jugador!");
			jugador = null;
		}

	}
	
//	@Override
//	public void eliminarPartidesJugadorId(Integer idJugador) {
//		// Buscar jugador a la base de dades
//		Jugador jugador = jugadorRepo.getById(idJugador);
//
//		// Eliminar les partides del jugador
//		partidaRepo.deleteByJugador(jugador);
//
//		// Actualitzar percentatgeExit a 0
//		jugador.setPercentatgeExit(0);
//		
//		// Guardar jugador a la base de dades
//		jugadorRepo.save(jugador);		
//	}
	
	@Override
	public int eliminarPartidesJugadorId(Integer idJugador) {
		Optional<Jugador> jugadorOpt = jugadorRepo.findById(idJugador);
		Jugador jugador;
		int numPartides = 0;

		// Buscar jugador a la base de dades
		if (jugadorOpt.isPresent()) {
			jugador = jugadorOpt.get();
			
			// Eliminar les partides del jugador
			numPartides = partidaRepo.deletePartidaByJugadorIdJugador(idJugador);

			// Actualitzar percentatgeExit a 0
			jugador.setPercentatgeExit(0);
			
			// Guardar jugador a la base de dades
			jugadorRepo.save(jugador);		
			
		} else {
			System.err.println("No existeix el jugador!");
		}
		return numPartides;
	}
	
	@Override
	public boolean existeixNomJugador(Jugador jugador) {
		return jugadorRepo.existsByNom(jugador.getNom().toUpperCase());
	}
	
	@Override
	public boolean existsJugadorByNom(String nom) {
		List<Jugador> jugadors = jugadorRepo.findAll();
		boolean existeixNom = false;
		for (Jugador j : jugadors) {
			if (j.getNom() == nom) {
				existeixNom = true;
			} else {
				existeixNom = false;
			}
		}
		return existeixNom;
	}
	
	@Override
	public boolean existsJugadorById(Integer idJugador) {
		return jugadorRepo.existsById(idJugador);
	}
	
	@Override
	public double getPercentatgeExit(Integer idJugador) {
		// Comptar partides guanyades per jugador
		double partidesGuanyades = partidaRepo.getByJugadorIdJugadorAndVictoria(idJugador, true).size();
		// Comptar partides totals
		double partidesTotals = partidesJugadorById(idJugador).size();
		
		return (partidesGuanyades/partidesTotals) * 100;
	}
	
	@Override
	public Jugador crearJugador(Jugador jugador) {
		jugador.setDataRegistre(LocalDateTime.now());
		return jugadorRepo.save(jugador);
	}
	
	@Override
	public Jugador updateJugador(Jugador jugador) {
				
		// Buscar el jugador sense modificar (jugadorAntic) a la base de dades
		Jugador jugadorAntic = jugadorRepo.getById(jugador.getIdJugador());
		
		// Recuperar la dataRegistre del jugadorAntic i passar-la al jugadorNou
		LocalDateTime dataRegistre = jugadorAntic.getDataRegistre();
		jugador.setDataRegistre(dataRegistre);
		
		// Recuperar el percentatgeExit del jugador Antic i passar-li al jugadorNou
		double percentatgeExit = jugadorAntic.getPercentatgeExit();
		jugador.setPercentatgeExit(percentatgeExit);
		
		// Guardar el jugadorNou a la base de dades
		jugadorRepo.save(jugador);
		
		// Retornar el jugador amb les dades modificades i guardat a la base de dades
		return jugador;
	}
	
	@Override
	public Jugador loser() {
		return jugadorRepo.findFirst1ByOrderByPercentatgeExitAsc();
	}
	
	@Override
	public Jugador winner() {
		
		return jugadorRepo.findFirst1ByOrderByPercentatgeExitDesc();
	}
	
	@Override
	public List<Jugador> llistaJugadorsExit() {
		return jugadorRepo.findAll();
	}
	
	@Override
	public List<Jugador> llistaRankingJugadors() {
		// Obtenir la llista de tots els jugadors ordenats per % èxit
		return jugadorRepo.findAllByOrderByPercentatgeExitDesc();
	}
	
	@Override
	public List<Partida> partidesJugadorById(Integer idJugador) {
	//	return partidaRepo.getByJugadorIdJugador(idJugador);
		return partidaRepo.findPartidaByJugadorIdJugador(idJugador);
	}
	
}
