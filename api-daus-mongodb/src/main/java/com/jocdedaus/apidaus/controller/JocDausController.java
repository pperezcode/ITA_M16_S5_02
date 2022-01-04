package com.jocdedaus.apidaus.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jocdedaus.apidaus.model.entity.Jugador;
import com.jocdedaus.apidaus.model.entity.Partida;
import com.jocdedaus.apidaus.model.service.JocDausService;
import com.jocdedaus.apidaus.model.service.SequenceGeneratorService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/players")
public class JocDausController {

	@Autowired
	JocDausService jdService;
	
	@Autowired
	SequenceGeneratorService sequenceGeneratorService;
	
	@PostMapping
	@ApiOperation(value = "Crear un jugador", notes = "Crea un jugador amb nom únic, opcional (si no indica nom, es dirà 'ANÒNIM')")
	public ResponseEntity<?> crearJugador(@RequestBody Jugador jugador)	{		
		try {	
			// Verificar que jugador s'introdueix amb el paràmetre 'nom'
			if (jugador.getNom() == null) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Es necessita el paràmetre 'nom' per a crear l'usuari!");
			}
			// El nom de l'usuari ha de ser únic i si està buit serà ANÒNIM (poden haver molts ANÒNIM)
			if (jdService.existsJugadorByNom(jugador.getNom().toUpperCase())) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Aquest nom de jugador ja existeix!");
			}
			if (jugador.getNom().equals("")) {
				jugador.setNom("ANÒNIM");
			}
			// Crear el jugador i guardar-lo a la base de dades
			jugador.setIdJugador(sequenceGeneratorService.generateSequence(Jugador.SEQUENCE_NAME));
			jugador = jdService.crearJugador(jugador);
			return ResponseEntity.status(HttpStatus.OK).body(jugador);
		
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

		}
	}
	
	@PostMapping("/{idJugador}/games")
	@ApiOperation(value = "Realitzar partida", notes = "Un jugador específic ('idJugador') realitza una tirada de daus")
	public ResponseEntity<?> jugarPartida(@PathVariable("idJugador") Integer idJugador) {
		try {
			// Validar que el jugador existeix
			if (!jdService.existsJugadorById(idJugador)) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aquest jugador no existeix!");
			}
			
			// Crear la partida i jugar
			Partida partida = new Partida();
			partida.setIdPartida(sequenceGeneratorService.generateSequence(Partida.SEQUENCE_NAME));
			jdService.jugarPartidaJugadorById(idJugador, partida);

			return ResponseEntity.status(HttpStatus.OK).body(partida);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}			
	}
	
	@PutMapping
	@ApiOperation(value = "Modificar nom jugador", notes = "Modificar nom jugador a partir de 'id' i 'nom'")
	public ResponseEntity<?> actualitzarNomJugador(@RequestBody Jugador jugador) {
		try {
			// Validar que el jugador existeix
			if(!jdService.existsJugadorById(jugador.getIdJugador())) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aquest jugador no existeix!");
			}
			// Validar que el nom no existeix a la base de dades
			if (jdService.existsJugadorByNom(jugador.getNom().toUpperCase())) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Aquest nom de jugador ja existeix!");
			}
			if (jugador.getNom().equals("")) {
				jugador.setNom("ANÒNIM");
			} 
			
			// Actualitzar dades jugador
			jugador = jdService.updateJugador(jugador);
			
			return ResponseEntity.status(HttpStatus.OK).body(jugador);
			
		}catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{idJugador}/games")
	@Transactional
	@ApiOperation(value = "Eliminar partides jugador", notes = "Eliminar totes les partides d'un jugador ('idJugador') i actualitzar % d'exit a 0")
	public ResponseEntity<?> eliminarPartidesJugador(@PathVariable("idJugador") Integer idJugador) {
		
		try {
			// Validar que el jugador existeix
			if(!jdService.existsJugadorById(idJugador)) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aquest jugador no existeix!");
			}
			
			// Eliminar partides del jugador
			int numPartides = jdService.eliminarPartidesJugadorId(idJugador);
			
			
			return ResponseEntity.status(HttpStatus.OK).body("S'han eliminat " + numPartides + " partides!");
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
		
	@GetMapping
	@ApiOperation(value = "Llistat de jugadors", notes = "Retorna el llistat de tots els jugadors del sistema amb el seu percentatge mig d’èxits")
	public ResponseEntity<?> getJugadorsAmbPercentatgeExit() {
		try {
			// Retornar la llista de jugadors amb % èxit
			return ResponseEntity.status(HttpStatus.OK).body(jdService.llistaJugadorsExit());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@GetMapping("/{idJugador}/games")
	@ApiOperation(value = "Llistat de partides per jugador", notes = "Retorna el llistat de partides del jugador ('idJugador')")
	public ResponseEntity<?> getTiradesbyJugador(@PathVariable("idJugador") Integer idJugador) {
		try {
			// Validar que el jugador existeix
			if(!jdService.existsJugadorById(idJugador)) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aquest jugador no existeix!");
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			if (jdService.partidesJugadorById(idJugador).size() == 0) {
				map.put("partides", "No hi ha partides per mostrar ni per calcular èxit mig");
			} else {
				map.put("exitmigjugador", jdService.getPercentatgeExit(idJugador));
	            map.put("partides", jdService.partidesJugadorById(idJugador));
			}
			
			// Retornar la llista de partides per jugador
            return ResponseEntity.status(HttpStatus.OK).body(map);
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@GetMapping("/ranking")
	@ApiOperation(value = "Ranking mig de jugadors", notes = "Retorna el ranking mig de tots els jugadors del sistema (percentatge mig d’èxits)")
	public ResponseEntity<?> rankingPlayers() {
		try {
			// Retornar llista de jugadors ordenats per % èxit
			return ResponseEntity.status(HttpStatus.OK).body(jdService.llistaRankingJugadors());
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@GetMapping("/ranking/loser")
	@ApiOperation(value = "Jugador perdedor", notes = "Retorna el jugador amb pitjor percentatge d’èxit")
	public ResponseEntity<?> loser() {
		try {
			// Retornar jugador amb el pitjor % d'èxit
			return ResponseEntity.status(HttpStatus.OK).body(jdService.loser());
		
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@GetMapping("/ranking/winner")
	@ApiOperation(value = "Jugador guanyador", notes = "Retorna el jugador amb millor percentatge d’èxit")
	public ResponseEntity<?> winner() {
		try {
			// Retornar jugador amb el pitjor % d'èxit
			return ResponseEntity.status(HttpStatus.OK).body(jdService.winner());
		
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
}
