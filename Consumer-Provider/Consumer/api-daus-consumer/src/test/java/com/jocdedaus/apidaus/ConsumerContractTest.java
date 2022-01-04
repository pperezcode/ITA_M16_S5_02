package com.jocdedaus.apidaus;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.pactfoundation.consumer.dsl.LambdaDsl;

import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(PactConsumerTestExt.class)
@PactTestFor(providerName = "Provider", port = "8080")
public class ConsumerContractTest {
	
	public String token = "eyJhbGciOiJIUzUxMiJ9." + 
			"eyJqdGkiOiJzb2Z0dGVrSldUIiwic3ViIjoicGF0cmljaWEiLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwiaWF0IjoxNjM5NDEyMjA3LCJleHAiOjE2Mzk0MTI4MDd9." +
			"BTpN5Agzm8_X1wmZz48enni6t3h2Mh-Rtp0xP3nrG1JkT3599Rj7CBnY3i68PaJg1rsROsAGHek6uVoM-G6fIg";

	@Pact(consumer = "Consumer")
	public RequestResponsePact pactGetWinner(PactDslWithProvider builder) {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("content-type", "application/json");
		headers.put("Authorization", "Bearer " + token);
		
		return builder.given("Get winner").uponReceiving("A request to /players/ranking/winner")
				.path("/players/ranking/winner").method("GET").willRespondWith().status(200)
				.body(LambdaDsl.newJsonBody(o -> o.numberType("idjugador", 1).stringType("nom", "player1")
						.datetimeExpression("dataRegistre", "2021-12-08T19:29:05.4102012")
						.numberType("percentatgeExit", 0.0)).build())
				.headers(headers)
				.toPact();
	}

	@Test
	@PactTestFor(pactMethod = "pactGetWinner")
	public void testPactGetWinner(MockServer mockServer) throws IOException {
		HttpResponse httpResponse = Request.Get(mockServer.getUrl() + "/players/ranking/winner").execute()
				.returnResponse();
		assertThat(httpResponse.getStatusLine().getStatusCode()).isEqualTo(200);
	}
}
