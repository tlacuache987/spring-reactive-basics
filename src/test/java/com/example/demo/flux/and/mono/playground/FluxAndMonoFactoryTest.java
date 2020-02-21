package com.example.demo.flux.and.mono.playground;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndMonoFactoryTest {

	List<String> names = Arrays.asList("Ivan", "Paula", "Iker", "Karla", "Yuri", "Ismael", "Isabella");

	@Test
	public void fluxUsingIterableTest() {
		log("fluxUsingIterableTest start ----------------------------------");

		Flux<String> stringFlux = Flux.fromIterable(names)
				.log();

		StepVerifier.create(stringFlux)
				.expectNext("Ivan", "Paula", "Iker", "Karla", "Yuri", "Ismael", "Isabella")
				.verifyComplete();

		stringFlux
				.subscribe((i) -> System.out.println("element: " + i),
						(e) -> System.err.println("error: " + e.getMessage()),
						() -> System.out.println("completed !"));

		log("fluxUsingIterableTest end ----------------------------------");
	}

	@Test
	public void fluxUsingArrayTest() {
		log("fluxUsingArrayTest start ----------------------------------");

		Flux<String> stringFlux = Flux.fromArray(names.toArray(new String[0]))
				.log();

		StepVerifier.create(stringFlux)
				.expectNext("Ivan", "Paula", "Iker", "Karla", "Yuri", "Ismael", "Isabella")
				.verifyComplete();

		stringFlux
				.subscribe((i) -> System.out.println("element: " + i),
						(e) -> System.err.println("error: " + e.getMessage()),
						() -> System.out.println("completed !"));

		log("fluxUsingArrayTest end ----------------------------------");
	}

	@Test
	public void fluxUsingStreamTest() {
		log("fluxUsingStreamTest start ----------------------------------");

		@SuppressWarnings("unused")
		Stream<String> stream = Stream.of(names.toArray(new String[0]));

		Flux<String> stringFlux = Flux.fromStream(() -> names.stream()) // Flux.fromStream(names.stream())
				.log();

		StepVerifier.create(stringFlux)
				.expectNext("Ivan", "Paula", "Iker", "Karla", "Yuri", "Ismael", "Isabella")
				.verifyComplete();

		// Cannot subscribe to this flux as its Strea, has been consumed. Use Supplier
		// of Stream
		stringFlux
				.subscribe((i) -> System.out.println("element: " + i),
						(e) -> System.err.println("error: " + e.getMessage()),
						() -> System.out.println("completed !"));

		System.out.println();

		log("fluxUsingStreamTest end ----------------------------------");
	}

	@Test
	public void fluxUsingRangeTest() {
		log("fluxUsingRangeTest start ----------------------------------");

		Flux<Integer> stringFlux = Flux.range(1, 5)
				.log();

		StepVerifier.create(stringFlux)
				.expectNext(1, 2, 3, 4, 5)
				.verifyComplete();

		stringFlux
				.subscribe((i) -> System.out.println("element: " + i),
						(e) -> System.err.println("error: " + e.getMessage()),
						() -> System.out.println("completed !"));

		log("fluxUsingRangeTest end ----------------------------------");
	}

	@Test
	public void monoUsingJustOrEmptyTest() {
		log("monoUsingJustOrEmptyTest start ----------------------------------");

		Mono<String> mono = Mono.justOrEmpty(null); // Mono.empty();

		StepVerifier.create(mono.log())
				.verifyComplete();

		mono
				.subscribe((i) -> System.out.println("element: " + i),
						(e) -> System.err.println("error: " + e.getMessage()),
						() -> System.out.println("completed !"));

		log("monoUsingJustOrEmptyTest end ----------------------------------");
	}

	@Test
	public void monoUsingSupplierTest() {
		log("monoUsingSupplierTest start ----------------------------------");

		Supplier<String> supplier = () -> "Ivan";

		Mono<String> mono = Mono.fromSupplier(supplier)
				.log();

		StepVerifier.create(mono.log())
				.expectNext("Ivan")
				.verifyComplete();

		mono
				.subscribe((i) -> System.out.println("element: " + i),
						(e) -> System.err.println("error: " + e.getMessage()),
						() -> System.out.println("completed !"));

		log("monoUsingSupplierTest end ----------------------------------");
	}

	private void log(String string) {
		System.out.println(string);
	}

}
