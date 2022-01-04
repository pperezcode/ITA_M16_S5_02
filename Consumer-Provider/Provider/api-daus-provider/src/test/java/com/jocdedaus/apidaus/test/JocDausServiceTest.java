package com.jocdedaus.apidaus.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.jocdedaus.apidaus.model.entity.Jugador;
import com.jocdedaus.apidaus.model.entity.Partida;
import com.jocdedaus.apidaus.model.repository.JugadorRepository;
import com.jocdedaus.apidaus.model.repository.PartidaRepository;
import com.jocdedaus.apidaus.model.service.JocDausService;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS) // Necessari per a que funcioni el BeforeAll
class JocDausServiceTest {
	
	@Autowired
	private JocDausService jdService;
	
	@Autowired
	private JugadorRepository jugadorRepo;
	
	@Autowired
	private PartidaRepository partidaRepo;
	
	@BeforeAll
	public void setup() {
		// Crear test data
		Jugador jugador1 = new Jugador("playerTest1");
		Jugador jugador2 = new Jugador("playerTest2");
		Jugador jugador3 = new Jugador("playerTest3");
		
		jugador1.setPercentatgeExit(50);
		jugador2.setPercentatgeExit(88);
		jugador3.setPercentatgeExit(11);
		
		jugadorRepo.save(jugador1);
		jugadorRepo.save(jugador2);
		jugadorRepo.save(jugador3);
		
		Partida partida1 = new Partida();
		partida1.setDau1(2);
		partida1.setDau2(5);
		partida1.setResultat(7);
		partida1.setVictoria(true);
		partida1.setJugador(jugador1);
		partidaRepo.save(partida1);
		
		Partida partida2 = new Partida();
		partida2.setDau1(3);
		partida2.setDau2(6);
		partida2.setResultat(9);
		partida2.setVictoria(false);
		partida2.setJugador(jugador1);
		partidaRepo.save(partida2);
	}
	
	@Test
	@DisplayName("SERVICE: crearJugador")
	void crearJugadorTest() {
		
		Jugador jugadorExp = new Jugador("playerTestCreat");
		
		Jugador jugadorCreat = jdService.crearJugador(new Jugador("playerTestCreat"));
		
		assertEquals(jugadorExp.getNom(), jugadorCreat.getNom());
	}
	
	@Test
	@DisplayName("SERVICE: updateJugador")
	@Transactional
	void updateJugadorTest() {
				
		// Crear jugadorModificat
		Jugador jugadorModificat = new Jugador("PLAYERTEST4");
		
		// Obtenir el jugador4
		Jugador jugador4 = jugadorRepo.getById(4);
		jugador4.setNom("PLAYERTEST4");	// La bbdd guarda els noms en majúscules
		jugador4.setPercentatgeExit(22);
		jdService.updateJugador(jugador4);
		
		assertEquals(jugadorModificat.getNom(), jugador4.getNom());
	}

	@Test
	@DisplayName("SERVICE: existeixNomJugador")
	void existeixNomJugadorTest() {
		
		// Crear un nou jugador i li posem el mateix nom que el jugador1
		Jugador jugadorNouNomIgual = new Jugador("playerTest1");
		
		// Comprovar que existeix el nom del jugador
		assertTrue(jdService.existeixNomJugador(jugadorNouNomIgual));
		
		// Crear un nou jugador amb un nom diferent
		Jugador jugadorNouNomDiferent = new Jugador("playerTest1NouNom");

		// Comprovar que no existeix el nom del jugador
		assertFalse(jdService.existeixNomJugador(jugadorNouNomDiferent));
	}
	
	@Test
	@DisplayName("SERVICE: existsJugadorById")
	void existsJugadorByIdTest() {
		// Comprovar que existeix el jugador amb id = 1
		assertTrue(jdService.existsJugadorById(1));
		
		// Comprovar que no existeix el jugador amb id = 9999
		assertFalse(jdService.existsJugadorById(9999));			
	}
	
	@Test
	@DisplayName("SERVICE: getPercentatgeExit")
	void getPercentatgeExitTest() {
		// Comprovar que el mètode getPercentatgeExit ens dona el resultat esperat
		// percentatge èxit jugador 1 = 50
		double percentatgeEsperat = 50;
		assertEquals(jdService.getPercentatgeExit(1), percentatgeEsperat);
	}
	
	@Test
	@DisplayName("SERVICE: loser")
	void loserTest() {
		// Comprovar que el mètode loser ens dóna el resultat esperat
		// percentatge èxit més baix: jugador3 = 11
		Jugador jugadorEsperat = new Jugador("PLAYERTEST3");
		assertEquals(jdService.loser().getNom(), jugadorEsperat.getNom());
	}
	
	@Test
	@DisplayName("SERVICE: winner")
	void winnerTest() {
		// Comprovar que el mètode loser ens dona el resultat esperat
		// percentatge èxit més baix: jugador2 = 88
		Jugador jugadorEsperat = new Jugador("PLAYERTEST2");
		assertEquals(jdService.winner().getNom(), jugadorEsperat.getNom());
	}
	
//	@Test
//	@DisplayName("SERVICE: llistaJugadorsExit")
//	void llistaJugadorsExitTest() {
//		// Crear la llista que esperem obtenir
//		List<Jugador> jugadors = new ArrayList<>();
//		
//		Jugador jugador1 = new Jugador("PLAYERTEST1");
//		jugador1.setIdJugador(1);
//		jugador1.setPercentatgeExit(50);
//		jugadors.add(jugador1);
//
//		Jugador jugador2 = new Jugador("PLAYERTEST2");		
//		jugador2.setIdJugador(2);
//		jugador2.setPercentatgeExit(88);
//		jugadors.add(jugador2);
//
//		Jugador jugador3 = new Jugador("PLAYERTEST3");
//		jugador3.setIdJugador(3);
//		jugador3.setPercentatgeExit(11);
//		jugadors.add(jugador3);
//
//		assertEquals(jdService.llistaJugadorsExit().toString(), jugadors.toString());
//	}
	
//	@Test
//	@DisplayName("SERVICE: llistaRankingJugadors")
//	void llistaRankingJugadorsTest() {
//		// Crear la llista que esperem obtenir
//		List<Jugador> jugadors = new ArrayList<>();
//		
//		Jugador jugador2 = new Jugador("PLAYERTEST2");		
//		jugador2.setIdJugador(2);
//		jugador2.setPercentatgeExit(88);
//		jugadors.add(jugador2);
//
//		Jugador jugador1 = new Jugador("PLAYERTEST1");
//		jugador1.setIdJugador(1);
//		jugador1.setPercentatgeExit(50);
//		jugadors.add(jugador1);
//
//		Jugador jugador3 = new Jugador("PLAYERTEST3");
//		jugador3.setIdJugador(3);
//		jugador3.setPercentatgeExit(11);
//		jugadors.add(jugador3);
//
//		// Comprovar que obtenim el resultat esperat
//		assertEquals(jdService.llistaRankingJugadors().toString(), jugadors.toString());
//	}
	
	@Test
	@DisplayName("SERVICE: partidesJugadorById")
	void partidesJugadorByIdTest() {
		// Crear un jugador amb les mateixes dades del jugador 1
		Jugador jugador1 = new Jugador("playerTest1");
		jugador1.setIdJugador(1);
		jugador1.setPercentatgeExit(50);
	
		// Crear la llista de partides del jugador1 que esperem obtenir
		List<Partida> partidesJugador1 = new ArrayList<>();
		
		Partida partida1 = new Partida();
		partida1.setIdPartida(1);
		partida1.setDau1(2);
		partida1.setDau2(5);
		partida1.setResultat(7);
		partida1.setVictoria(true);
		partida1.setJugador(jugador1);
		partidesJugador1.add(partida1);
		
		Partida partida2 = new Partida();
		partida2.setIdPartida(2);
		partida2.setDau1(3);
		partida2.setDau2(6);
		partida2.setResultat(9);
		partida2.setVictoria(false);
		partida2.setJugador(jugador1);
		partidesJugador1.add(partida2);

		// Comprovar que obtenim el resultat esperat
		assertEquals(jdService.partidesJugadorById(1).toString(), partidesJugador1.toString());
	}
}
