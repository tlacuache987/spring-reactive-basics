package com.example.demo;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Base64.Encoder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringReactiveMongodbApplication {

	public static void mains(String[] args) {
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

	private static String generateSafeToken() {
		SecureRandom random = new SecureRandom();
		byte bytes[] = new byte[20];
		random.nextBytes(bytes);
		Encoder encoder = Base64.getUrlEncoder().withoutPadding();
		String token = encoder.encodeToString(bytes);
		return token;
	}

	public static String generateRandomString(int length) {
		String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
		String CHAR_UPPER = CHAR_LOWER.toUpperCase();
		String NUMBER = "0123456789";

		String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
		SecureRandom random = new SecureRandom();

		if (length < 1)
			throw new IllegalArgumentException();

		StringBuilder sb = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			// 0-62 (exclusive), random returns 0-61
			int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
			char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);

			sb.append(rndChar);
		}

		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println("---");

		for (int i = 0; i < 100; i++) {
			System.out.println(generateRandomString(9));
		}

	}

	public static void main3(String[] args) throws NoSuchAlgorithmException, NoSuchProviderException {
		SecureRandom secureRandom = SecureRandom.getInstanceStrong();

		byte[] arr = new byte[16];

		secureRandom.nextBytes(arr);
		System.out.println(new String(arr));

		System.out.println("------");

		secureRandom.nextBytes(arr);
		System.out.println(new String(arr));

		System.out.println("------");

		secureRandom.nextBytes(arr);
		System.out.println(new String(arr));

		System.out.println();

		System.out.println(generateSafeToken());
	}

	private static SecureRandom secureRandom;

	private static final int SEED_RESET = 100;

	private static int uses;

	private static synchronized SecureRandom getSecureRandomInstance()
			throws NoSuchProviderException, NoSuchAlgorithmException {
		if (secureRandom == null || uses >= SEED_RESET) {
			uses = 0;
			secureRandom = SecureRandom.getInstanceStrong();
			secureRandom.nextBytes(new byte[64]);
		}
		uses += 1;
		return secureRandom;
	}

	/**
	 * Creates a cryptographically secure string of {@code length} random digits
	 * 
	 * @param length
	 * @return A cryptographically secure string of {@code length} random digits
	 */
	public static String getRandomDigitsString(int length) throws NoSuchProviderException, NoSuchAlgorithmException {
		return getSecureRandomInstance().toString();
	}
}
