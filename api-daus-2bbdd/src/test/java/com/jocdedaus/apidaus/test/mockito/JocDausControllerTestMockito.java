package com.jocdedaus.apidaus.test.mockito;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jocdedaus.apidaus.controller.JocDausController;
import com.jocdedaus.apidaus.model.entity.Jugador;
import com.jocdedaus.apidaus.model.entity.Partida;
import com.jocdedaus.apidaus.model.service.JocDausService;

@WebMvcTest(JocDausController.class)
class JocDausControllerTestMockito {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;
		
	@MockBean
	JocDausService jdService;
	
	Jugador jugador1 = null;
	Partida partida1 = null;
	Partida partida2 = null;
	
	@BeforeEach
	public void init() {
		// Iniciem unes dades de prova
		jugador1 = new Jugador("playerTest1");
		
		jugador1.setPercentatgeExit(50);
		
		partida1 = new Partida();
		partida1.setDau1(2);
		partida1.setDau2(5);
		partida1.setResultat(7);
		partida1.setVictoria(true);
		partida1.setJugador(jugador1);
		
		partida2 = new Partida();
		partida2.setDau1(3);
		partida2.setDau2(6);
		partida2.setResultat(9);
		partida2.setVictoria(false);
		partida2.setJugador(jugador1);
		
		jdService.crearJugador(jugador1);
		
		// Iniciem Mocks
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	@DisplayName("POST: crearJugadorMockitoTest")
	public void crearJugadorMockitoTest() throws Exception {		
		// Crear jugador (jugadorCreat)
		Jugador jugadorCreat = new Jugador();
		jugadorCreat.setIdJugador(1);
		jugadorCreat.setNom("playerTest1");
		
		// Serialitzar el jugador
		ObjectMapper mapper = new ObjectMapper();
		String userJson = mapper.writeValueAsString(jugadorCreat);

		// Invocar el WebService "/players" 
		this.mockMvc.perform(
				MockMvcRequestBuilders.post("/players")
				.content(userJson.getBytes())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@DisplayName("PUT: actualitzarNomJugadorMockitoTest")
	public void actualitzarNomJugadorMockitoTest() throws Exception {
		// Crear jugador
		Jugador jugadorModificat = new Jugador("playertestModificat");
		jugadorModificat.setIdJugador(1);
		
		// Serialitzar el jugador
		ObjectMapper mapper = new ObjectMapper();
		String userJson = mapper.writeValueAsString(jugadorModificat);
		
		// Invocar el WebService 
		this.mockMvc.perform(
				MockMvcRequestBuilders.put("/players")
				.contentType(MediaType.APPLICATION_JSON)
				.content(userJson.getBytes()))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@DisplayName("GET: jugadorsAmbPercentatgeExitMockitoTest")
	public void getJugadorsAmbPercentatgeExitMockitoTest() throws Exception {
	    this.mockMvc.perform(MockMvcRequestBuilders.get("/players").accept(MediaType.APPLICATION_JSON))
	    .andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@DisplayName("GET: rankingPlayersMockitoTest")
	public void rankingPlayersMockitoTest() throws Exception {
	    this.mockMvc.perform(MockMvcRequestBuilders.get("/players/ranking").accept(MediaType.APPLICATION_JSON))
	    .andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@DisplayName("GET: loserMockitoTest")
	public void loserMockitoTest() throws Exception {
	    this.mockMvc.perform(MockMvcRequestBuilders.get("/players/ranking/loser").accept(MediaType.APPLICATION_JSON))
	    .andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@DisplayName("GET: winnerMockitoTest")
	public void winnerMockitoTest() throws Exception {
	    this.mockMvc.perform(MockMvcRequestBuilders.get("/players/ranking/winner").accept(MediaType.APPLICATION_JSON))
	    .andExpect(MockMvcResultMatchers.status().isOk());
	}
}
