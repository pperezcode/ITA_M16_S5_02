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

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS) // Necessari per a que funcioni el BeforeAll
class PartidaRepositoryTest {

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
	@DisplayName("REPOSITORI PARTIDA: getByJugadorIdJugador")
	void getByJugadorIdJugadorTest() {
		// Creem el jugador1 i la seva llista de partides
		Jugador jugador1 = new Jugador("playerTest1");
		jugador1.setIdJugador(1);
		jugador1.setPercentatgeExit(50);
		
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
		
		// Comparem que el mètode getByJugadorIdJugador ens retorna la mateixa llista que hem creat
		assertEquals(partidesJugador1.toString(), partidaRepo.getByJugadorIdJugador(1).toString());		
	}
	
	@Test
	@DisplayName("REPOSITORI PARTIDA: getByJugadorIdJugadorAndVictoria")
	void getByJugadorIdJugadorAndVictoriaTest() {
		// Creem el jugador1 i la seva llista de partides amb victoria = true
		Jugador jugador1 = new Jugador("playerTest1");
		jugador1.setIdJugador(1);
		jugador1.setPercentatgeExit(50);
		
		List<Partida> partidesJugador1VictoriaTrue = new ArrayList<>();

		Partida partida1 = new Partida();
		partida1.setIdPartida(1);
		partida1.setDau1(2);
		partida1.setDau2(5);
		partida1.setResultat(7);
		partida1.setVictoria(true);
		partida1.setJugador(jugador1);
		partidesJugador1VictoriaTrue.add(partida1);
		
		// Comparem que el mètode getByJugadorIdJugadorAndVictoria ens retorna la mateixa llista que hem creat
		assertEquals(partidesJugador1VictoriaTrue.toString(), partidaRepo.getByJugadorIdJugadorAndVictoria(1, true).toString());		
	
		// Creem el jugador1 la llista de partides amb victoria = false
		List<Partida> partidesJugador1VictoriaFalse = new ArrayList<>();

		Partida partida2 = new Partida();
		partida2.setIdPartida(2);
		partida2.setDau1(3);
		partida2.setDau2(6);
		partida2.setResultat(9);
		partida2.setVictoria(false);
		partida2.setJugador(jugador1);
		partidesJugador1VictoriaFalse.add(partida2);
		
		// Comparem que el mètode getByJugadorIdJugadorAndVictoria ens retorna la mateixa llista que hem creat
		assertEquals(partidesJugador1VictoriaFalse.toString(), partidaRepo.getByJugadorIdJugadorAndVictoria(1, false).toString());
	}
	
	@Test
	@DisplayName("REPOSITORI PARTIDA: deleteByJugador")
	@Transactional
	void deleteByJugadorTest() {
		// Creem el jugador1 i una llista de partides buida
		Jugador jugador1 = new Jugador("playerTest1");
		jugador1.setIdJugador(1);
		
		List<Partida> partidesJugador1Buida = new ArrayList<>();
		
		// Comparem que la llista del repositori té contingut abans del delete
		assertNotEquals(partidesJugador1Buida.toString(), partidaRepo.getByJugadorIdJugador(1).toString());
		
		// Comparem que la llista del repositori està buida després del delete
		partidaRepo.deleteByJugador(jugador1);
		assertEquals(partidesJugador1Buida.toString(), partidaRepo.getByJugadorIdJugador(1).toString());
	}
}
