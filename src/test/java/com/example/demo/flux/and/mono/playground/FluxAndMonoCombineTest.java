package com.example.demo.flux.and.mono.playground;

import java.time.Duration;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoCombineTest {

	@Test
	public void flux_combine_using_merge() {
		log("flux_combine_using_merge start ----------------------------------");

		Flux<String> flux1 = Flux.just("A", "B", "C", "D", "E");

		Flux<String> flux2 = Flux.just("1", "2", "3", "4");

		Flux<String> mergeFlux = Flux.merge(flux1, flux2).log();

		StepVerifier.create(mergeFlux)
				.expectSubscription()
				.expectNext("A", "B", "C", "D", "E", "1", "2", "3", "4")
				.verifyComplete();

		log("flux_combine_using_merge end ----------------------------------");
	}

	@Test
	public void flux_combine_using_merge_with_delay() {
		log("flux_combine_using_merge_with_delay start ----------------------------------");

		Flux<String> flux1 = Flux.just("A", "B", "C")
				.delayElements(Duration.ofSeconds(1));

		Flux<String> flux2 = Flux.just("1", "2", "3")
				.delayElements(Duration.ofSeconds(1));

		Flux<String> mergeFlux = Flux.merge(flux1, flux2).log();

		StepVerifier.create(mergeFlux)
				.expectSubscription()
				.expectNextCount(6)
				// .expectNext("A", "B", "C", "D", "E", "1", "2", "3", "4")
				.verifyComplete();

		log("flux_combine_using_merge_with_delay end ----------------------------------");
	}

	@Test
	public void flux_combine_using_concat_with_delay() {
		log("flux_combine_using_concat_with_delay start ----------------------------------");

		Flux<String> flux1 = Flux.just("A", "B", "C")
				.delayElements(Duration.ofSeconds(1));

		Flux<String> flux2 = Flux.just("1", "2", "3");

		Flux<String> mergeFlux = Flux.concat(flux1, flux2).log();

		StepVerifier.create(mergeFlux)
				.expectSubscription()
				.expectNext("A", "B", "C", "1", "2", "3")
				.verifyComplete();

		log("flux_combine_using_concat_with_delay end ----------------------------------");
	}

	@Test
	public void flux_combine_using_zip() {
		log("flux_combine_using_zip start ----------------------------------");

		Flux<String> flux1 = Flux.just("A", "B", "C", "D")
				.delayElements(Duration.ofSeconds(1));

		Flux<String> flux2 = Flux.just("1", "2", "3");

		Flux<String> mergeFlux = Flux.zip(flux1, flux2,
				(t1, t2) -> {
					return t1.concat(t2);
				}).log();

		StepVerifier.create(mergeFlux)
				.expectSubscription()
				.expectNext("A1", "B2", "C3")
				.verifyComplete();

		log("flux_combine_using_zip end ----------------------------------");
	}

	@Test
	public void flux_combine_using_zip_2() {
		log("flux_combine_using_zip_2 start ----------------------------------");

		Flux<String> flux1 = Flux.just("A", "B", "C", "D");

		Flux<String> flux2 = Flux.just("1", "2", "3");

		Flux<String> tuple2Flux = Flux.zip(flux1, flux2)
				.map(t -> t.getT1() + t.getT2())
				.log();

		StepVerifier.create(tuple2Flux)
				.expectSubscription()
				.expectNext("A1", "B2", "C3")
				.verifyComplete();

		log("flux_combine_using_zip_2 end ----------------------------------");
	}

	@Test
	public void flux_combine_using_zip_3_with_delay() {
		log("flux_combine_using_zip_3_with_delay start ----------------------------------");

		Flux<Long> delay = Flux.interval(Duration.ofSeconds(1));
		Flux<Long> delay2 = Flux.interval(Duration.ofMillis(500));

		Flux<String> flux1 = Flux.just("A", "B", "C", "D");

		Flux<String> flux2 = Flux.just("1", "2", "3");

		Flux<String> flux1WithDelay = flux1.zipWith(delay, (s, l) -> s);

		Flux<String> flux2WithDelay = flux2.zipWith(delay2, (s, l) -> s);

		Flux<String> tuple2Flux = Flux.zip(flux1WithDelay, flux2WithDelay)
				.map(t -> t.getT1() + t.getT2())
				.log();

		StepVerifier.create(tuple2Flux)
				.expectSubscription()
				.expectNext("A1", "B2", "C3")
				.verifyComplete();

		log("flux_combine_using_zip_3_with_delay end ----------------------------------");
	}

	private void log(String string) {
		System.out.println(string);
	}

}
