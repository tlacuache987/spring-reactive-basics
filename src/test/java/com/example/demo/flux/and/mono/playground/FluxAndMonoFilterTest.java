package com.example.demo.flux.and.mono.playground;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoFilterTest {

	List<String> names = Arrays.asList("Ivan", "Paula", "Iker", "Karla", "Yuri", "Ismael", "Isabella");

	@Test
	public void fluxFilterTest() {
		log("fluxFilterTest start ----------------------------------");

		Flux<String> stringFlux = Flux.fromIterable(names)
				.log()
				.filter(s -> s.startsWith("K") || s.startsWith("Y"));

		StepVerifier.create(stringFlux)
				.expectNext("Karla", "Yuri")
				.verifyComplete();

		stringFlux
				.subscribe((i) -> System.out.println("element: " + i),
						(e) -> System.err.println("error: " + e.getMessage()),
						() -> System.out.println("completed !"));

		log("fluxFilterTest end ----------------------------------");
	}

	@Test
	public void monoFilterTest() {
		log("monoFilterTest start ----------------------------------");

		Mono<String> stringFlux = Mono.justOrEmpty("Ivan")
				.log()
				.filter(s -> s.startsWith("K") || s.startsWith("Y"));

		StepVerifier.create(stringFlux)
				.expectNextCount(0)
				.verifyComplete();

		stringFlux
				.subscribe((i) -> System.out.println("element: " + i),
						(e) -> System.err.println("error: " + e.getMessage()),
						() -> System.out.println("completed !"));

		log("monoFilterTest end ----------------------------------");
	}

	private void log(String string) {
		System.out.println(string);
	}

}
