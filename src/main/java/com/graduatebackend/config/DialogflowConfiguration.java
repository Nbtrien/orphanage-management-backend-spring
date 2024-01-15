package com.graduatebackend.config;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.api.client.json.gson.GsonFactory;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.IntentsClient;
import com.google.cloud.dialogflow.v2.IntentsSettings;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;

@Configuration
public class DialogflowConfiguration {

	@Bean
	public SessionsSettings sessionsSettings() throws IOException {
		GoogleCredentials credentials = googleCredentials();
		return SessionsSettings.newBuilder().setCredentialsProvider(FixedCredentialsProvider.create(credentials))
				.build();
	}

	@Bean
	public SessionsClient sessionsClient(SessionsSettings sessionsSettings) throws IOException {
		return SessionsClient.create(sessionsSettings);
	}

	@Bean
	public IntentsSettings intentsSettings() throws IOException {
		GoogleCredentials credentials = googleCredentials();
		return IntentsSettings.newBuilder().setCredentialsProvider(FixedCredentialsProvider.create(credentials))
				.build();
	}

	@Bean
	public IntentsClient intentsClient(IntentsSettings intentsSettings) throws IOException {
		return IntentsClient.create(intentsSettings);
	}

	@Bean
	public GoogleCredentials googleCredentials() throws IOException {
		try (InputStream is = getClass().getResourceAsStream("/orphanage-bot-lmst-2bb29c88892a.json");) {
			return GoogleCredentials.fromStream(is);
		}
	}

	@Bean
	public GsonFactory gsonFactory() {
		return GsonFactory.getDefaultInstance();
	}
}