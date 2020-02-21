package com.example.demo.handler;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.demo.router.RouterFunctionConfig;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
// 1st load approach
// @SpringBootTest
// @AutoConfigureWebTestClient

// 2nd load approach
// @WebFluxTest
// @Import({ RouterFunctionConfig.class, SampleHandlerFunctionTestConfig.class
// })

// 3rd load approach
@WebFluxTest
@Import({ RouterFunctionConfig.class, Config.class })
public class SampleHandlerFunctionTest {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void functional_Flux_Test_Approach_1() {
		log("functional_Flux_Test_Approach_1 start -----------------------------");

		Flux<Integer> responseFlux = webTestClient
				.get()
				.uri("/functional/flux")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.returnResult(Integer.class)
				.getResponseBody();

		StepVerifier.create(responseFlux)
				.expectSubscription()
				.expectNext(1)
				.expectNext(2)
				.expectNext(3)
				.expectNext(4)
				.expectNext(5)
				.verifyComplete();

		log("functional_Flux_Test_Approach_1 end -----------------------------");
	}

	@Test
	public void functional_Mono_Test_Approach_1() {
		log("functional_Mono_Test_Approach_1 start -----------------------------");

		String expectedValue = "Spring reactive";

		EntityExchangeResult<String> entityExchangeResponse = webTestClient
				.get()
				.uri("/functional/mono")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.expectBody(String.class)
				.consumeWith(response -> Assert.assertEquals(expectedValue, response.getResponseBody()))
				.returnResult();

		Assert.assertEquals(expectedValue, entityExchangeResponse.getResponseBody());

		log("functional_Mono_Test_Approach_1 end -----------------------------");
	}

	@Test
	public void functional_Mono_Test_Approach_2() {
		log("functional_Mono_Test_Approach_2 start -----------------------------");

		String expectedValue = "Spring reactive";

		Flux<String> fluxMonoString = webTestClient
				.get()
				.uri("/functional/mono")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus().isOk()
				.returnResult(String.class)
				.getResponseBody();

		StepVerifier.create(fluxMonoString)
				.expectSubscription()
				.expectNext(expectedValue)
				.verifyComplete();

		log("functional_Mono_Test_Approach_2 end -----------------------------");
	}

	public static void log(String message) {
		System.out.println(message);
	}
}

@Configuration
@ComponentScan(basePackageClasses = SampleHandlerFunction.class)
class Config {
}
