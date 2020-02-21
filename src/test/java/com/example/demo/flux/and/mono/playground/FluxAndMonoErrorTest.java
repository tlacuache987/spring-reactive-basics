package com.example.demo.flux.and.mono.playground;

import java.time.Duration;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxAndMonoErrorTest {

	@Test
	public void flux_error_handling_test() {
		log("flux_error_handling_test start ----------------------------------");

		Flux<String> stringFlux = Flux.just("A", "B", "C")
				.concatWith(Flux.error(new RuntimeException("Something happened")))
				.concatWith(Flux.just("After exception"))
				.log();

		StepVerifier.create(stringFlux)
				.expectSubscription()
				.expectNext("A", "B", "C")
				.expectErrorMessage("Something happened")
				.verify();

		log("flux_error_handling_test end ----------------------------------");
	}

	@Test
	public void flux_error_handling_test_2() {
		log("flux_error_handling_test_2 start ----------------------------------");

		Flux<String> stringFlux = Flux.just("A", "B", "C")
				.concatWith(Flux.error(new RuntimeException("Something happened")))
				.concatWith(Flux.just("After exception"))
				.log();

		StepVerifier.create(stringFlux)
				.expectSubscription()
				.expectNext("A", "B", "C")
				.expectError(RuntimeException.class)
				.verify();

		log("flux_error_handling_test_2 end ----------------------------------");
	}

	@Test
	public void flux_error_handling_onErrorResume_test() {
		log("flux_error_handling_onErrorResume_test start ----------------------------------");

		Flux<String> stringFlux = Flux.just("A", "B", "C")
				.concatWith(Flux.error(new RuntimeException("Something happened")))
				.concatWith(Flux.just("After exception"))
				.onErrorResume(e -> { // this block gets executed on exception
					System.out.println("exception: " + e.getClass().getSimpleName());
					return Flux.just("default value", "another default value");
				})
				.log();

		StepVerifier.create(stringFlux)
				.expectSubscription()
				.expectNext("A", "B", "C")
				.expectNext("default value", "another default value")
				.verifyComplete();

		log("flux_error_handling_onErrorResume_test end ----------------------------------");
	}

	@Test
	public void flux_error_handling_onErrorReturn_test() {
		log("flux_error_handling_onErrorReturn_test start ----------------------------------");

		Flux<String> stringFlux = Flux.just("A", "B", "C")
				.concatWith(Flux.error(new RuntimeException("Something happened")))
				.concatWith(Flux.just("After exception"))
				.onErrorReturn("default value")
				.log();

		StepVerifier.create(stringFlux)
				.expectSubscription()
				.expectNext("A", "B", "C")
				.expectNext("default value")
				.verifyComplete();

		log("flux_error_handling_onErrorReturn_test end ----------------------------------");
	}

	@Test
	public void flux_error_handling_onErrorReturn_test_2() {
		log("flux_error_handling_onErrorReturn_test_2 start ----------------------------------");

		Flux<String> stringFlux = Flux.just("A", "B", "C")
				// .concatWith(Flux.error(new IllegalArgumentException("Something happened")))
				.concatWith(Flux.error(new RuntimeException("Something happened")))
				.concatWith(Flux.just("After exception"))
				.onErrorReturn(IllegalArgumentException.class, "default illegal")
				.onErrorReturn(RuntimeException.class, "default runtime")
				.log();

		StepVerifier.create(stringFlux)
				.expectSubscription()
				.expectNext("A", "B", "C")
				.expectNext("default runtime")
				.verifyComplete();

		log("flux_error_handling_onErrorReturn_test_2 end ----------------------------------");
	}

	@Test
	public void flux_error_handling_onErrorReturn_test_3() {
		log("flux_error_handling_onErrorReturn_test_3 start ----------------------------------");

		Flux<String> stringFlux = Flux.just("A", "B", "C")
				.concatWith(Flux.error(new IllegalArgumentException("Something happened")))
				.concatWith(Flux.error(new RuntimeException("Something happened")))
				.concatWith(Flux.just("After exception"))
				.onErrorReturn(e -> e instanceof IllegalArgumentException, "default illegal")
				.onErrorReturn(e -> e instanceof RuntimeException, "default runtime")
				.log();

		StepVerifier.create(stringFlux)
				.expectSubscription()
				.expectNext("A", "B", "C")
				.expectNext("default illegal")
				.verifyComplete();

		log("flux_error_handling_onErrorReturn_test_3 end ----------------------------------");
	}

	@Test
	public void flux_error_handling_onErrorMap_test_1() {
		log("flux_error_handling_onErrorMap_test_1 start ----------------------------------");

		Flux<String> stringFlux = Flux.just("A", "B", "C")
				.concatWith(Flux.error(new RuntimeException("Something happened")))
				.concatWith(Flux.just("After exception"))
				.onErrorMap(runtimeExc -> new IllegalArgumentException("Something wrong"))
				.log();

		StepVerifier.create(stringFlux)
				.expectSubscription()
				.expectNext("A", "B", "C")
				.expectError(IllegalArgumentException.class)
				.verify();

		log("flux_error_handling_onErrorMap_test_1 end ----------------------------------");
	}

	@Test
	public void flux_error_handling_onErrorMap_test_2() {
		log("flux_error_handling_onErrorMap_test_2 start ----------------------------------");

		Flux<String> stringFlux = Flux.just("A", "B", "C")
				.concatWith(Flux.error(new RuntimeException("Something happened")))
				.concatWith(Flux.just("After exception"))
				.onErrorMap(runtimeExc -> new IllegalArgumentException("Something wrong"))
				.onErrorReturn(e -> e instanceof IllegalArgumentException, "default illegal")
				.log();

		StepVerifier.create(stringFlux)
				.expectSubscription()
				.expectNext("A", "B", "C")
				.expectNext("default illegal")
				.verifyComplete();

		log("flux_error_handling_onErrorMap_test_2 end ----------------------------------");
	}

	@Test
	public void flux_error_handling_onErrorMap_with_retry_test_3() {
		log("flux_error_handling_onErrorMap_with_retry_test_3 start ----------------------------------");

		Flux<String> stringFlux = Flux.just("A", "B", "C")
				.concatWith(Flux.error(new RuntimeException("Something happened")))
				.concatWith(Flux.just("After exception"))
				.onErrorMap(runtimeExc -> new IllegalArgumentException("Something wrong"))
				.retry(2)
				.log();

		StepVerifier.create(stringFlux)
				.expectSubscription()
				.expectNext("A", "B", "C")
				.expectNext("A", "B", "C")
				.expectNext("A", "B", "C")
				.expectError(IllegalArgumentException.class)
				.verify();

		log("flux_error_handling_onErrorMap_with_retry_test_3 end ----------------------------------");
	}

	@Test
	public void flux_error_handling_onErrorMap_with_retry_backoff_test_4() {
		log("flux_error_handling_onErrorMap_with_retry_backoff_test_4 start ----------------------------------");

		Flux<String> stringFlux = Flux.just("A", "B", "C")
				.concatWith(Flux.error(new RuntimeException("Something happened")))
				.concatWith(Flux.just("After exception"))
				.onErrorMap(runtimeExc -> new IllegalArgumentException("Something wrong"))
				.retryBackoff(2, Duration.ofSeconds(5))
				.log();

		StepVerifier.create(stringFlux)
				.expectSubscription()
				.expectNext("A", "B", "C")
				.expectNext("A", "B", "C")
				.expectNext("A", "B", "C")
				.expectError(IllegalStateException.class)
				.verify();

		log("flux_error_handling_onErrorMap_with_retry_backoff_test_4 end ----------------------------------");
	}

	private void log(String string) {
		System.out.println(string);
	}

}
