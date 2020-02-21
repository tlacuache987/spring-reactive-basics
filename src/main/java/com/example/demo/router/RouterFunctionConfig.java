package com.example.demo.router;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RequestPredicate;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.demo.handler.SampleHandlerFunction;

import reactor.core.publisher.Mono;

@Configuration
public class RouterFunctionConfig {

	@Bean
	public RouterFunction<ServerResponse> router(SampleHandlerFunction handler) {
		return RouterFunctions
				.route(RequestPredicates.GET("/functional/flux")
						.and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
						new HandlerFunction<ServerResponse>() {

							@Override
							public Mono<ServerResponse> handle(ServerRequest request) {
								return handler.flux(request);
							}
						})
				.andRoute(method(RequestMethod.GET, MediaType.APPLICATION_JSON, "/functional/mono"),
						handler::mono);
	}

	public RequestPredicate method(RequestMethod method, MediaType mediaType, String path) {
		switch (method) {
		case GET:
			return RequestPredicates.GET(path).and(RequestPredicates.accept(mediaType));
		default:
			break;
		}
		return null;
	}

}
