package com.example.demo.flux.and.mono.playground;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import lombok.SneakyThrows;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

public class FluxAndMonoTransformFlatMapTest {

	List<String> names = Arrays.asList("Ivan", "Paula", "Iker", "Karla", "Yuri", "Ismael", "Isabella");

	@Test
	public void flux_Transform_Using_FlatMap_Test() {
		log("flux_Transform_Using_FlatMap_Test start ----------------------------------");

		Flux<String> stringFlux = Flux.fromArray(new String[] { "A", "B", "C", "D", "E", "F" })
				.flatMap(s -> {
					return Flux.fromIterable(converToList(s));
				})
				.log();

		System.out.println("flux definition");

		StepVerifier.create(stringFlux)
				.expectNext("A")
				.expectNext("new value")
				.expectNext("B")
				.expectNext("new value")
				.expectNext("C")
				.expectNext("new value")
				.expectNext("D")
				.expectNext("new value")
				.expectNext("E")
				.expectNext("new value")
				.expectNext("F", "new value")
				.verifyComplete();

		System.out.println("flux completed");

		log("flux_Transform_Using_FlatMap_Test end ----------------------------------");
	}

	@SneakyThrows
	private List<String> converToList(String s) {
		Thread.sleep(randomTime());
		return Arrays.asList(s, "new value");
	}

	private long randomTime() {
		Random r = new Random();
		int low = 1000;
		int high = 1000;
		return low == high ? low : r.nextInt(high - low) + low;
	}

	@Test
	public void flux_Transform_Using_FlatMap_Parallel_Test() {
		log("flux_Transform_Using_FlatMap_Parallel_Test start ----------------------------------");

		Flux<String> stringFlux = Flux.fromArray(new String[] { "A", "B", "C", "D", "E" })
				.window(2) // Flux<Flux<String>> = (A,B) , (C,D), (E)
				.flatMap(fs -> fs.map(this::converToList).subscribeOn(Schedulers.parallel())) // Flux<List<String>>
				.flatMap(s -> Flux.fromIterable(s)) // Flux<String>
				.log();

		System.out.println("flux definition");

		StepVerifier.create(stringFlux)
				.expectNextCount(10)
				.verifyComplete();

		System.out.println("flux completed");

		log("flux_Transform_Using_FlatMap_Parallel_Test end ----------------------------------");
	}

	@Test
	public void flux_Transform_Using_FlatMap_Parallel_maintain_Order_Test() {
		log("flux_Transform_Using_FlatMap_Parallel_maintain_Order_Test start ----------------------------------");

		Flux<String> stringFlux = Flux.fromArray(new String[] { "A", "B", "C", "D", "E" })
				.window(2) // Flux<Flux<String>> = (A,B) , (C,D), (E)
				//.concatMap(fs -> fs.map(this::converToList).subscribeOn(Schedulers.parallel())) // Flux<List<String>>
				.flatMapSequential(fs -> fs.map(this::converToList).subscribeOn(Schedulers.parallel())) // Flux<List<String>>
				.flatMap(s -> Flux.fromIterable(s)) // Flux<String>
				.log();

		System.out.println("flux definition");

		StepVerifier.create(stringFlux)
				.expectNextCount(10)
				.verifyComplete();

		System.out.println("flux completed");

		log("flux_Transform_Using_FlatMap_Parallel_maintain_Order_Test end ----------------------------------");
	}

	private void log(String string) {
		System.out.println(string);
	}

}
