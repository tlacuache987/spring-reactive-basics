package com.example.demo.controller;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class FluxAndMonoController {

	@GetMapping("/flux/blocks") // response blocks
	public Flux<Integer> getFlux() {
		return Flux.just(1, 2, 3, 4)
				.delayElements(Duration.ofSeconds(1))
				.log();
	}

	@GetMapping(value = "/flux/stream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE) // response blocks
	public Flux<Integer> getFluxStrem() {
		return Flux.just(1, 2, 3, 4)
				.delayElements(Duration.ofSeconds(3))
				.log();
	}

	@GetMapping(value = "/flux/infinite/stream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE) // response blocks
	public Flux<Long> getFluxInfiniteStrem() {
		return Flux.interval(Duration.ofSeconds(1))
				.log();
	}

	@GetMapping(value = "/mono") // response blocks
	public Mono<Integer> getmono() {
		return Mono.just(1)
				.log();
	}
}
