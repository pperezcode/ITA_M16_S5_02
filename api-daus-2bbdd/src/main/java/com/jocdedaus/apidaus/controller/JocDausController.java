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

import com.jocdedaus.apidaus.model.document.Partida;
import com.jocdedaus.apidaus.model.entity.Jugador;
import com.jocdedaus.apidaus.model.service.JocDausService;

@RestController
@RequestMapping("/players")
public class JocDausController {

	@Autowired
	JocDausService jdService;
	
	@GetMapping("/hello")
	public ResponseEntity<?> sayHello() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body("Validació correcta");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@PostMapping
	public ResponseEntity<?> crearJugador(@RequestBody Jugador jugador)	{		
		try {	
			// Verificar que jugador s'introdueix amb el paràmetre 'nom'
			if (jugador.getNom() == null) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Es necessita el paràmetre 'nom' per a crear l'usuari!");
			}
			// El nom de l'usuari ha de ser únic i si està buit serà ANÒNIM (poden haver molts ANÒNIM)
			if (jdService.existeixNomJugador(jugador)) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("Aquest nom de jugador ja existeix!");
			}
			if (jugador.getNom().equals("")) {
				jugador.setNom("ANÒNIM");
			}
			
			// Crear el jugador i guardar-lo a la base de dades
			jugador = jdService.crearJugador(jugador);
			return ResponseEntity.status(HttpStatus.OK).body(jugador);
		
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

		}
	}
	
	@PostMapping("/{idJugador}/games")
	public ResponseEntity<?> jugarPartida(@PathVariable("idJugador") Integer idJugador) {
		try {
			// Validar que el jugador existeix
			if (!jdService.existsJugadorById(idJugador)) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aquest jugador no existeix!");
			}
			
			// Crear la partida i jugar
			Partida partida = new Partida();
			jdService.jugarPartidaJugadorById(idJugador, partida);

			return ResponseEntity.status(HttpStatus.OK).body(partida);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}			
	}
	
	@PutMapping
	public ResponseEntity<?> actualitzarNomJugador(@RequestBody Jugador jugador) {
		try {
			// Validar que el jugador existeix
			if (jdService.existeixNomJugador(jugador)) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Aquest nom de jugador ja existeix!");
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
	public ResponseEntity<?> eliminarPartidesJugador(@PathVariable("idJugador") Integer idJugador) {
		
		try {
			// Validar que el jugador existeix
			if(!jdService.existsJugadorById(idJugador)) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aquest jugador no existeix!");
			}
			
			// Eliminar partides del jugador
			jdService.eliminarPartidesJugadorId(idJugador);
			
			
			return ResponseEntity.status(HttpStatus.OK).body("S'han eliminat totes les partides del jugador!");
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
		
	@GetMapping
	public ResponseEntity<?> getJugadorsAmbPercentatgeExit() {
		try {
			// Retornar la llista de jugadors amb % èxit
			return ResponseEntity.status(HttpStatus.OK).body(jdService.llistaJugadorsExit());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@GetMapping("/{idJugador}/games")
	public ResponseEntity<?> getTiradesbyJugador(@PathVariable("idJugador") Integer idJugador) {
		try {
			// Validar que el jugador existeix
			if(!jdService.existsJugadorById(idJugador)) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Aquest jugador no existeix!");
			}
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("exitmigjugador", jdService.getPercentatgeExit(idJugador));
            map.put("partides", jdService.partidesJugadorById(idJugador));

			// Retornar la llista de partides per jugador
            return ResponseEntity.status(HttpStatus.OK).body(map);
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@GetMapping("/ranking")
	public ResponseEntity<?> rankingPlayers() {
		try {
			// Retornar llista de jugadors ordenats per % èxit
			return ResponseEntity.status(HttpStatus.OK).body(jdService.llistaRankingJugadors());
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@GetMapping("/ranking/loser")
	public ResponseEntity<?> loser() {
		try {
			// Retornar jugador amb el pitjor % d'èxit
			return ResponseEntity.status(HttpStatus.OK).body(jdService.loser());
		
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@GetMapping("/ranking/winner")
	public ResponseEntity<?> winner() {
		try {
			// Retornar jugador amb el pitjor % d'èxit
			return ResponseEntity.status(HttpStatus.OK).body(jdService.winner());
		
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
}
