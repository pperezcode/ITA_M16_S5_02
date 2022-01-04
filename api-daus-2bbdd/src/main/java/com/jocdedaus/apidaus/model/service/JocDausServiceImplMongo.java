package com.jocdedaus.apidaus.model.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jocdedaus.apidaus.model.entity.Jugador;
import com.jocdedaus.apidaus.model.entity.Partida;
import com.jocdedaus.apidaus.model.repository.JugadorMongoRepository;
import com.jocdedaus.apidaus.model.repository.PartidaMongoRepository;

@Service("mongodb")
public class JocDausServiceImplMongo implements JocDausService {

	@Autowired
	JugadorMongoRepository jugadorMongoRepo;
	
	@Autowired
	PartidaMongoRepository partidaMongoRepo;
	
	@Override
	public void jugarPartidaJugadorById(Integer idJugador, Partida partida) {
		Optional<Jugador> jugadorOpt = jugadorMongoRepo.findById(idJugador);
		Jugador jugador;
		if (jugadorOpt.isPresent()) {
			jugador = jugadorOpt.get();
			
			// Assignar un jugador a una partida
			partida.setJugador(jugador);
						
			// Guardar la partida
			partidaMongoRepo.save(partida);
			
			// Obtenir % èxit i guardar-lo al jugador
			jugador.setPercentatgeExit(getPercentatgeExit(idJugador));
			
			// Guardar jugador a la base de dades
			jugadorMongoRepo.save(jugador);
			
		} else {
			System.err.println("No existeix el jugador!");
			jugador = null;
		}

	}
	
	@Override
	public void eliminarPartidesJugadorId(Integer idJugador) {
		Optional<Jugador> jugadorOpt = jugadorMongoRepo.findById(idJugador);
		Jugador jugador;
		int numPartides = 0;

		// Buscar jugador a la base de dades
		if (jugadorOpt.isPresent()) {
			jugador = jugadorOpt.get();
			
			// Eliminar les partides del jugador
			numPartides = partidaMongoRepo.deletePartidaByJugadorIdJugador(idJugador);

			// Actualitzar percentatgeExit a 0
			jugador.setPercentatgeExit(0);
			
			// Guardar jugador a la base de dades
			jugadorMongoRepo.save(jugador);		
			System.err.println("S'han eliminat " + numPartides + " partides");
		} else {
			System.err.println("No existeix el jugador!");
		}
	}
	
	@Override
	public boolean existeixNomJugador(Jugador jugador) {
		List<Jugador> jugadors = jugadorMongoRepo.findAll();
		boolean existeixNom = false;
		for (Jugador j : jugadors) {
			if (jugador.getNom() == j.getNom()) {
				existeixNom = true;
			} else {
				existeixNom = false;
			}
		}
		return existeixNom;
	}
	
	@Override
	public boolean existsJugadorByNom(String nom) {
		List<Jugador> j = jugadorMongoRepo.findByNom(nom.toUpperCase());
		if (j.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public boolean existsJugadorById(Integer idJugador) {
		return jugadorMongoRepo.existsById(idJugador);
	}
	
	@Override
	public double getPercentatgeExit(Integer idJugador) {
		// Comptar partides guanyades per jugador
		double partidesGuanyades = partidaMongoRepo.getByJugadorIdJugadorAndVictoria(idJugador, true).size();
		// Comptar partides totals
		double partidesTotals = partidesJugadorById(idJugador).size();
		
		return (partidesGuanyades/partidesTotals) * 100;
	}
	
	@Override
	public Jugador crearJugador(Jugador jugador) {
		jugador.setNom(jugador.getNom().toUpperCase());
		jugador.setDataRegistre(LocalDateTime.now());
		return jugadorMongoRepo.save(jugador);
	}
	
	@Override
	public Jugador updateJugador(Jugador jugador) {
		// Buscar el jugador sense modificar (jugadorAntic) a la base de dades
		Optional<Jugador> jugadorOpt = jugadorMongoRepo.findById(jugador.getIdJugador());
		Jugador jugadorAntic;
		if (jugadorOpt.isPresent()) {
			jugadorAntic = jugadorOpt.get();
			
		// Recuperar la dataRegistre del jugadorAntic i passar-la al jugadorNou
		LocalDateTime dataRegistre = jugadorAntic.getDataRegistre();
		jugador.setDataRegistre(dataRegistre);
		
		// Recuperar el percentatgeExit del jugador Antic i passar-li al jugadorNou
		double percentatgeExit = jugadorAntic.getPercentatgeExit();
		jugador.setPercentatgeExit(percentatgeExit);
			
		// Setejar nom a UpperCase
		jugador.setNom(jugador.getNom().toUpperCase());
		
		// Guardar el jugadorNou a la base de dades
		jugadorMongoRepo.save(jugador);
			
		// Retornar el jugador amb les dades modificades i guardat a la base de dades
		return jugador;
				
		} else {
			System.err.println("No existeix el jugador!");
			jugador = null;
			return jugador;
		}
		
	}
	
	@Override
	public Jugador loser() {
		return jugadorMongoRepo.findFirst1ByOrderByPercentatgeExitAsc();
	}
	
	@Override
	public Jugador winner() {
		return jugadorMongoRepo.findFirst1ByOrderByPercentatgeExitDesc();
	}
	
	@Override
	public List<Jugador> llistaJugadorsExit() {
		return jugadorMongoRepo.findAll();
	}
	
	@Override
	public List<Jugador> llistaRankingJugadors() {
		// Obtenir la llista de tots els jugadors ordenats per % èxit
		return jugadorMongoRepo.findAllByOrderByPercentatgeExitDesc();
	}
	
	@Override
	public List<Partida> partidesJugadorById(Integer idJugador) {
		return partidaMongoRepo.findPartidaByJugadorIdJugador(idJugador);
	}
	
}
