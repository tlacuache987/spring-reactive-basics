package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringReactiveMongodbApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringReactiveMongodbApplication.class, args);
	}

	/*
	@Configuration
	@Profile("mongo")
	static class MongoConfig {
		@Bean
		public MongoClient mongoclient() throws IOException {
			EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean();
			mongo.setBindIp("localhost");
			return mongo.getObject();
		}
	
		@Bean
		public MongoTemplate mongoTemplate(MongoClient mongoClient) {
			MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, "embedded_db");
			return mongoTemplate;
		}
	}
	*/

}
