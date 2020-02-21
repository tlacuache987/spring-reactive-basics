package com.example.demo.handler;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class SampleHandlerFunction {

	public Mono<ServerResponse> flux(ServerRequest request) {

		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(Flux.just(1, 2, 3, 4, 5).log(), Integer.class);
	}

	public Mono<ServerResponse> mono(ServerRequest request) {

		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just("Spring reactive").log(), String.class);
	}
}
