package com.jocdedaus.apidaus.test.pact;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Consumer;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;


@Provider("Provider")
@Consumer("Consumer")
@PactBroker(host = "localhost", port = "8080")
@PactFolder("target/pacts")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContractTest {
	
	@BeforeEach
	void before(PactVerificationContext context) {
	    context.setTarget(new HttpTestTarget("localhost", port));
	}

	@LocalServerPort
	private int port;
	
	@BeforeAll
	static void enablePublishingPact() {
	    System.setProperty("pact.verifier.publishResults", "true");
	}
	
	@TestTemplate
	@ExtendWith(PactVerificationInvocationContextProvider.class)
	void pactVerificationTestTemplate(PactVerificationContext context) {
	    context.verifyInteraction();
	}
	
	@State("Get winner")
	public void pactGetWinner() {
	}
}
