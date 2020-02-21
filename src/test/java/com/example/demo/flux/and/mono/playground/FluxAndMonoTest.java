package com.example.demo.flux.and.mono.playground;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoTest {

	@Test
	public void fluxTest() {
		log("fluxTest start ----------------------------------");

		Flux<String> stringFlux = Flux.just("Spring", "Reactive", "Hello", "World")
				// .concatWith(Flux.error(new RuntimeException("Something happened")))
				.concatWith(Flux.just("After exception"))
				.concatWith(Flux.just("1", "2", "3"))
				.log();

		stringFlux
				.subscribe(System.out::println,
						(e) -> System.err.println("error: " + e.getMessage()),
						() -> System.out.println("completed"));

		log("fluxTest end ----------------------------------");
	}

	@Test
	public void fluxTest_without_error() {
		log("fluxTest_without_error start ----------------------------------");

		Flux<String> stringFlux = Flux.just("Spring", "Reactive", "Hello", "World")
				.log();

		StepVerifier.create(stringFlux)
				.expectNext("Spring")
				.expectNext("Reactive")
				.expectNext("Hello")
				.expectNext("World")
				.verifyComplete();

		log("fluxTest_without_error end ----------------------------------");
	}

	@Test
	public void fluxTest_with_error() {
		log("fluxTest_with_error start ----------------------------------");

		Flux<String> stringFlux = Flux.just("Spring", "Reactive", "Hello", "World")
				.concatWith(Flux.error(new RuntimeException("Something happened")))
				.log();

		StepVerifier.create(stringFlux)
				.expectNext("Spring")
				.expectNext("Reactive")
				.expectNext("Hello")
				.expectNext("World")
				.expectErrorMessage("Something happened")
				.verify();

		log("fluxTest_with_error end ----------------------------------");
	}

	@Test
	public void fluxTestCount_with_error() {
		log("fluxTestCount_with_error start ----------------------------------");

		Flux<String> stringFlux = Flux.just("Spring", "Reactive", "Hello", "World")
				.concatWith(Flux.error(new RuntimeException("Something happened")))
				.log();

		StepVerifier.create(stringFlux)
				.expectNextCount(4)
				.expectError(RuntimeException.class)
				.verify();

		log("fluxTestCount_with_error end ----------------------------------");
	}

	@Test
	public void fluxTest_with_error1() {
		log("fluxTest_with_error1 start ----------------------------------");

		Flux<String> stringFlux = Flux.just("Spring", "Reactive", "Hello", "World")
				.concatWith(Flux.error(new RuntimeException("Something happened")))
				.log();

		StepVerifier.create(stringFlux)
				.expectNext("Spring", "Reactive", "Hello", "World")
				.expectErrorMessage("Something happened")
				.verify();

		log("fluxTest_with_error1 end ----------------------------------");
	}

	@Test
	public void monoTest() {
		log("monoTest start ----------------------------------");

		Mono<String> stringMono = Mono.just("Spring")
				.log();

		StepVerifier.create(stringMono)
				.expectNext("Spring")
				.verifyComplete();

		log("monoTest end ----------------------------------");
	}

	@Test
	public void monoTest_with_error() {
		log("monoTest_with_error start ----------------------------------");

		Mono<Object> objectMono = Mono.error(new RuntimeException("Something wrong"))
				.log();

		StepVerifier.create(objectMono)
				.expectError()
				.verify();

		log("monoTest_with_error end ----------------------------------");
	}

	private void log(String string) {
		System.out.println(string);
	}

}
