package com.jocdedaus.apidaus.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jocdedaus.apidaus.model.entity.Jugador;
import com.jocdedaus.apidaus.model.entity.Partida;
import com.jocdedaus.apidaus.model.service.JocDausService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS) // Necessari per a que funcioni el BeforeAll
class JocDausControllerTest {

	private MockMvc mockMvc;
	
	@Autowired
	JocDausService jdService;
	
	@Autowired
	private WebApplicationContext context;
		
	@BeforeEach
	public void setupMockMvc() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();           
	}
	
	@Test
	@DisplayName("POST: crearJugador")
	public void crearJugadorTest() throws Exception {
		
		// Crear jugador
		Jugador jugador = new Jugador();
		jugador.setNom("");
		
		// Serialitzar el jugador
		ObjectMapper mapper = new ObjectMapper();
		String userJson = mapper.writeValueAsString(jugador);

		// Invocar el WebService 
		this.mockMvc.perform(
				MockMvcRequestBuilders.post("/players")
				.content(userJson.getBytes())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@DisplayName("POST: jugarPartida")
	public void jugarPartidaTest() throws Exception {
		// Crear jugador
		jdService.crearJugador(new Jugador("playertest1"));
		
		// Crear partida 
		Partida partida = new Partida();
				
		// Serialitzar el jugador
		ObjectMapper mapper = new ObjectMapper();
		String userJson = mapper.writeValueAsString(partida);
		
		// Invocar el WebService 
		this.mockMvc.perform(
				MockMvcRequestBuilders.post("/players/{idJugador}/games", 1)
				.contentType(MediaType.APPLICATION_JSON)
				.content(userJson.getBytes()))
			.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	@DisplayName("GET: jugadorsAmbPercentatgeExit")
	public void getJugadorsAmbPercentatgeExitTest() throws Exception {
	    this.mockMvc.perform(MockMvcRequestBuilders.get("/players").accept(MediaType.APPLICATION_JSON))
	    .andExpect(MockMvcResultMatchers.status().isOk());
	}
	
}