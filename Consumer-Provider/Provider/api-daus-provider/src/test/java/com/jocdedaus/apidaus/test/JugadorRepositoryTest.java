package com.jocdedaus.apidaus.test;

import static org.junit.jupiter.api.Assertions.*;

//import java.util.ArrayList;
//import java.util.List;

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

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS) // Necessari per a que funcioni el BeforeAll
class JugadorRepositoryTest {

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
	@DisplayName("REPOSITORI JUGADOR: existsById()")
	void existsByIdTest() {
						
		assertTrue(jugadorRepo.existsById(1));
		assertFalse(jugadorRepo.existsById(9999999));
	}
	
	@Test
	@DisplayName("REPOSITORI JUGADOR: getByID")
	@Transactional
	void getByIdTest() {
		// Crear un jugador amb nom "playerTest", igual que el que es crea en iniciar el test a @BeforeAll
		Jugador jugadorCreat1 = new Jugador("playerTest1");

		// Buscar el jugador amb id 1 al repositori
		Jugador jugadorEsperat1 = jugadorRepo.getById(1);

		// Comprovar que el nom dels jugadors és igual
		assertEquals(jugadorEsperat1.getNom(), jugadorCreat1.getNom());
	}
	
//	@Test
//	@DisplayName("REPOSITORI JUGADOR: findAll()")
//	void findAllTest() {
//		// Fem una llista dels jugadors com hauran de sortir a la bbdd
//		List<Jugador> jugadors = new ArrayList<>();
//		
//		Jugador jugador1 = new Jugador("playerTest1");
//		jugador1.setIdJugador(1);
//		jugador1.setPercentatgeExit(50);
//		jugadors.add(jugador1);
//
//		Jugador jugador2 = new Jugador("playerTest2");
//		jugador2.setIdJugador(2);
//		jugador2.setPercentatgeExit(88);
//		jugadors.add(jugador2);
//
//		Jugador jugador3 = new Jugador("playerTest3");
//		jugador3.setIdJugador(3);
//		jugador3.setPercentatgeExit(11);
//		jugadors.add(jugador3);
//
//		// Comparem que el mètode findAll ens retorna la mateixa llista que hem creat
//		assertEquals(jugadors.toString(), jugadorRepo.findAll().toString());		
//	}
	
	@Test
	@DisplayName("REPOSITORI JUGADOR: existsByNom()")
	void existsByNomTest() {
						
		assertTrue(jugadorRepo.existsByNom("PLAYERTEST1"));
		assertFalse(jugadorRepo.existsByNom("NOM DE PROVA"));
	}
	
	@Test
	@DisplayName("REPOSITORI JUGADOR: findFirst1ByOrderByPercentatgeExitDesc()")
	@Transactional
	void findFirst1ByOrderByPercentatgeExitDescTest() {		
		// Crear jugador2 per comprovar que obtindré el resultat esperat
		Jugador jugador2 = new Jugador("playerTest2");
		jugador2.setIdJugador(2);
		jugador2.setPercentatgeExit(88);
		
		// Comprovar que trobaré el que té percentatge d'èxit més gran (jugador2)
		assertEquals(jugador2.getNom(), jugadorRepo.findFirst1ByOrderByPercentatgeExitDesc().getNom());
	}
	
//	@Test
//	@DisplayName("REPOSITORI JUGADOR: findFirst1ByOrderByPercentatgeExitAsc()")
//	@Transactional
//	void findFirst1ByOrderByPercentatgeExitAscTest() {		
//		// Crear jugador2 per comprovar que obtindré el resultat esperat
//		Jugador jugador3 = new Jugador("playerTest3");
//		jugador3.setIdJugador(3);
//		jugador3.setPercentatgeExit(11);
//		
//		// Comprovar que trobaré el que té percentatge d'èxit més gran (jugador2)
//		assertEquals(jugador3.getNom(), jugadorRepo.findFirst1ByOrderByPercentatgeExitAsc().getNom());
//	}
	
//	@Test
//	@DisplayName("REPOSITORI JUGADOR: findAllByOrderByPercentatgeExitDesc()")
//	@Transactional
//	void findAllByOrderByPercentatgeExitDescTest() {		
//		// Fem una llista dels jugadors com hauran de sortir(jugador2, jugador1, jugador3)
//		List<Jugador> jugadors = new ArrayList<>();
//		
//		Jugador jugador2 = new Jugador("playerTest2");
//		jugador2.setIdJugador(2);
//		jugador2.setPercentatgeExit(88);
//		jugadors.add(jugador2);
//
//		Jugador jugador1 = new Jugador("playerTest1");
//		jugador1.setIdJugador(1);
//		jugador1.setPercentatgeExit(50);
//		jugadors.add(jugador1);
//		
//		Jugador jugador3 = new Jugador("playerTest3");
//		jugador3.setIdJugador(3);
//		jugador3.setPercentatgeExit(11);
//		jugadors.add(jugador3);
//		
//		// Comparem que el mètode findAll ens retorna la mateixa llista que hem creat
//		assertEquals(jugadors.toString(), jugadorRepo.findAllByOrderByPercentatgeExitDesc().toString());	
//	}
}
