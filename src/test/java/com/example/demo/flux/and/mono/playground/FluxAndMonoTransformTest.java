package com.example.demo.flux.and.mono.playground;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoTransformTest {

	List<String> names = Arrays.asList("Ivan", "Paula", "Iker", "Karla", "Yuri", "Ismael", "Isabella");

	@Test
	public void flux_Transform_Using_Map_Test() {
		log("flux_Transform_Using_Map_Test start ----------------------------------");

		Flux<String> stringFlux = Flux.fromIterable(names)
				.filter(s -> s.startsWith("K") || s.startsWith("Y"))
				.map(s -> s.toUpperCase())
				.log();

		StepVerifier.create(stringFlux)
				.expectNext("KARLA", "YURI")
				.verifyComplete();

		log("flux_Transform_Using_Map_Test end ----------------------------------");
	}

	@Test
	public void flux_Transform_Using_Map_With_Length_Test() {
		log("flux_Transform_Using_Map_With_Lenght_Test start ----------------------------------");

		Flux<Integer> stringFlux = Flux.fromIterable(names)
				.map(s -> s.length())
				.log();

		StepVerifier.create(stringFlux)
				.expectNext(4, 5, 4, 5, 4, 6, 8)
				.verifyComplete();

		log("flux_Transform_Using_Map_With_Length_Test end ----------------------------------");
	}

	@Test
	public void flux_Transform_Using_Map_With_Length_Repeat_Test() {
		log("flux_Transform_Using_Map_With_Length_Repeat_Test start ----------------------------------");

		Flux<Integer> stringFlux = Flux.fromIterable(names)
				.map(s -> s.length())
				.repeat(1)
				.log();

		StepVerifier.create(stringFlux)
				.expectNext(4, 5, 4, 5, 4, 6, 8, 4, 5, 4, 5, 4, 6, 8)
				.verifyComplete();

		log("flux_Transform_Using_Map_With_Length_Repeat_Test end ----------------------------------");
	}

	@Test
	public void flux_Transform_Using_Map_With_Length_Repeat_Filter_Test() {
		log("flux_Transform_Using_Map_With_Length_Repeat_Filter_Test start ----------------------------------");

		Flux<String> stringFlux = Flux.fromIterable(names)
				.filter(s -> s.length() <= 5)
				.map(s -> s.toUpperCase())
				.filter(s -> s.length() > 4)
				.repeat(1)
				.log();

		StepVerifier.create(stringFlux)
				.expectNext("PAULA")
				.expectNext("KARLA")
				.expectNext("PAULA", "KARLA")
				.verifyComplete();

		log("flux_Transform_Using_Map_With_Length_Repeat_Filter_Test end ----------------------------------");
	}

	private void log(String string) {
		System.out.println(string);
	}

}
