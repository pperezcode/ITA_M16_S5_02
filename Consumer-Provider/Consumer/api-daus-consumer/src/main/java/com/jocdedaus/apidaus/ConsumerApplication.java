package com.jocdedaus.apidaus;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ConsumerApplication {

	private final RestTemplate restTemplate;

    public ConsumerApplication(String baseUrl) {
        this.restTemplate = new RestTemplateBuilder().rootUri(baseUrl).build();
    }

    public Jugador getWinner() {
        return restTemplate.getForObject("/players/ranking/winner", Jugador.class);
    }
}
