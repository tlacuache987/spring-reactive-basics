package com.example.demo.controller;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@WebFluxTest
public class FluxAndMonoControllerTest {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void getFlux_Test_Approach_1() {
		log("getFlux_Test_Approach_1 start -----------------------------");

		Flux<Integer> responseFlux = webTestClient
				.get()
				.uri("/flux/blocks")
				.accept(MediaType.APPLICATION_JSON_UTF8)
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
				.verifyComplete();

		log("getFlux_Test_Approach_1 end -----------------------------");
	}

	@Test
	public void getFlux_Test_Approach_2() {
		log("getFlux_Test_Approach_2 start -----------------------------");

		webTestClient
				.get()
				.uri("/flux/blocks")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBodyList(Integer.class)
				.hasSize(4);

		log("getFlux_Test_Approach_2 end -----------------------------");
	}

	@Test
	public void getFlux_Test_Approach_3() {
		log("getFlux_Test_Approach_3 start -----------------------------");

		List<Integer> expectedListResult = Arrays.asList(1, 2, 3, 4);

		EntityExchangeResult<List<Integer>> entityExchangeResponse = webTestClient
				.get()
				.uri("/flux/blocks")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBodyList(Integer.class)
				.returnResult();

		Assert.assertEquals(expectedListResult, entityExchangeResponse.getResponseBody());

		log("getFlux_Test_Approach_3 end -----------------------------");
	}

	@Test
	public void getFlux_Test_Approach_4() {
		log("getFlux_Test_Approach_4 start -----------------------------");

		List<Integer> expectedListResult = Arrays.asList(1, 2, 3, 4);

		EntityExchangeResult<List<Integer>> entityExchangeResponse = webTestClient
				.get()
				.uri("/flux/blocks")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBodyList(Integer.class)
				.consumeWith(response -> Assert.assertEquals(expectedListResult, response.getResponseBody()))
				.returnResult();

		Assert.assertEquals(expectedListResult, entityExchangeResponse.getResponseBody());

		log("getFlux_Test_Approach_4 end -----------------------------");
	}

	@Test
	public void fluxInfiniteStream_Test_Approach_1() {
		log("fluxInfiniteStream_Test_Approach_1 start -----------------------------");

		Flux<Long> responseFlux = webTestClient
				.get()
				.uri("/flux/infinite/stream")
				.accept(MediaType.APPLICATION_STREAM_JSON)
				.exchange()
				.expectStatus().isOk()
				.returnResult(Long.class)
				.getResponseBody();

		StepVerifier.create(responseFlux)
				.expectSubscription()
				.expectNext(0L)
				.expectNext(1L)
				.expectNext(2L)
				.thenCancel()
				.verify();

		log("fluxInfiniteStream_Test_Approach_1 end -----------------------------");
	}

	@Test
	public void mono_Test_Approach_1() {
		log("mono_Test_Approach_1 start -----------------------------");

		Integer expectedValue = 1;

		EntityExchangeResult<Integer> entityExchangeResponse = webTestClient
				.get()
				.uri("/mono")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.exchange()
				.expectStatus().isOk()
				.expectBody(Integer.class)
				.consumeWith(response -> Assert.assertEquals(expectedValue, response.getResponseBody()))
				.returnResult();

		Assert.assertEquals(expectedValue, entityExchangeResponse.getResponseBody());

		log("mono_Test_Approach_1 end -----------------------------");
	}

	@Test
	public void mono_Test_Approach_2() {
		log("mono_Test_Approach_2 start -----------------------------");

		Flux<Integer> responseFlux = webTestClient
				.get()
				.uri("/mono")
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.exchange()
				.expectStatus().isOk()
				.returnResult(Integer.class)
				.getResponseBody();

		StepVerifier.create(responseFlux)
				.expectSubscription()
				.expectNext(1)
				.verifyComplete();

		log("mono_Test_Approach_2 end -----------------------------");
	}

	public static void log(String message) {
		System.out.println(message);
	}
}
