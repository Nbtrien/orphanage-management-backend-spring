package com.graduatebackend.service.impl;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.google.cloud.dialogflow.v2.AgentName;
import com.google.cloud.dialogflow.v2.DetectIntentRequest;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.Intent;
import com.google.cloud.dialogflow.v2.IntentsClient;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.QueryResult;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.TextInput;
import com.graduatebackend.entity.DialogflowIntent;
import com.graduatebackend.entity.DonationPurpose;
import com.graduatebackend.entity.DonationPurposePost;
import com.graduatebackend.entity.Event;
import com.graduatebackend.entity.Family;
import com.graduatebackend.entity.FamilyPost;
import com.graduatebackend.exception.ResourceNotFoundException;
import com.graduatebackend.repository.DialogflowIntentRepository;
import com.graduatebackend.repository.DonationPurposeRepository;
import com.graduatebackend.repository.EventRepository;
import com.graduatebackend.repository.FamilyRepository;
import com.graduatebackend.service.DialogflowService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class DialogflowServiceImpl implements DialogflowService {
	private final SessionsClient sessionsClient;
	private final IntentsClient intentsClient;
	@Value("${dialogflow.credentials.project-id}")
	private String projectId;
	@Value("${dialogflow.credentials.language-code}")
	private String languageCode;
	private final EventRepository eventRepository;
	private final TemplateEngine templateEngine;
	private final DialogflowIntentRepository intentRepository;
	private final DonationPurposeRepository donationPurposeRepository;
	private final FamilyRepository familyRepository;

	public String detectIntent(String userInput) {
		String sessionId = UUID.randomUUID().toString();
		SessionName session = SessionName.of(projectId, sessionId);

		QueryInput queryInput = QueryInput.newBuilder()
				.setText(TextInput.newBuilder().setText(userInput).setLanguageCode(languageCode)).build();

		DetectIntentRequest detectIntentRequest = DetectIntentRequest.newBuilder().setSession(session.toString())
				.setQueryInput(queryInput).build();

		DetectIntentResponse response = sessionsClient.detectIntent(detectIntentRequest);
		QueryResult queryResult = response.getQueryResult();

		return queryResult.getFulfillmentText();
	}

	@Override
	public void createEventIntent(Model model, Integer eventId) {
		Event event = eventRepository.findByEventIdAndIsDeleteIsFalse(eventId)
				.orElseThrow(() -> new ResourceNotFoundException("Event not found."));
		createEventIntent(model, event);
	}

	@Override
	public void createDonationProgramIntent(Model model, Integer id) {
		DonationPurpose donationPurpose = donationPurposeRepository.findByDonationPurposeIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Donation Program not found"));
		createDonationProgramIntent(model, donationPurpose.getPost());
	}

	@Override
	public void createFamilyDonationIntent(Model model, Integer id) {
		Family family = familyRepository.findFamilyByFamilyIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Family not found."));
		createFamilyDonationIntent(model, family.getPost());
	}

	@Override
	@Async
	public CompletableFuture<Void> createEventIntent(Model model, Event event) {
		if (event.getIntent() == null) {
			log.info("Event Detail#" + event.getEventId());
			Intent intent = Intent.newBuilder().setDisplayName("Event Detail#" + event.getEventId())
					.addTrainingPhrases(Intent.TrainingPhrase.newBuilder()
							.addParts(Intent.TrainingPhrase.Part.newBuilder()
									.setText("Sự kiện tình nguyện " + event.getTitle())))
					.setWebhookState(Intent.WebhookState.WEBHOOK_STATE_ENABLED).build();
			AgentName parent = AgentName.of(projectId);
			Intent intentSaved = intentsClient.createIntent(parent, intent);

			model.addAttribute("event", event);
			org.thymeleaf.context.Context context = new Context();
			context.setVariables(model.asMap());

			String response = templateEngine.process("event-detail-template.html", context);

			DialogflowIntent dialogflowIntent = DialogflowIntent.builder().displayName(intentSaved.getDisplayName())
					.name(intentSaved.getName()).response(response).build();
			intentRepository.save(dialogflowIntent);

			event.setIntent(dialogflowIntent);
			eventRepository.save(event);
		}
		return CompletableFuture.completedFuture(null);
	}

	@Override
	@Async
	public CompletableFuture<Void> createDonationProgramIntent(Model model, DonationPurposePost post) {
		if (post.getDonationPurpose().getIntent() == null) {
			Intent intent = Intent.newBuilder()
					.setDisplayName("Donation Program Detail#" + post.getDonationPurpose().getDonationPurposeId())
					.addTrainingPhrases(Intent.TrainingPhrase.newBuilder()
							.addParts(Intent.TrainingPhrase.Part.newBuilder()
									.setText("Chiến dịch tài trợ " + post.getDonationPurpose().getPurpose())))
					.setWebhookState(Intent.WebhookState.WEBHOOK_STATE_ENABLED).build();
			AgentName parent = AgentName.of(projectId);
			Intent intentSaved = intentsClient.createIntent(parent, intent);

			model.addAttribute("post", post);
			org.thymeleaf.context.Context context = new Context();
			context.setVariables(model.asMap());

			String response = templateEngine.process("donation-program-detail-template.html", context);

			DialogflowIntent dialogflowIntent = DialogflowIntent.builder().displayName(intentSaved.getDisplayName())
					.name(intentSaved.getName()).response(response).build();
			intentRepository.save(dialogflowIntent);

			post.getDonationPurpose().setIntent(dialogflowIntent);
			donationPurposeRepository.save(post.getDonationPurpose());
		} else {
			DialogflowIntent intent = post.getDonationPurpose().getIntent();
			model.addAttribute("post", post);
			org.thymeleaf.context.Context context = new Context();
			context.setVariables(model.asMap());

			String response = templateEngine.process("donation-program-detail-template.html", context);
			intent.setResponse(response);
			intentRepository.save(intent);
		}
		return CompletableFuture.completedFuture(null);
	}

	@Override
	@Async
	public CompletableFuture<Void> createFamilyDonationIntent(Model model, FamilyPost post) {
		if (post.getFamily().getIntent() == null) {
			Intent intent = Intent.newBuilder()
					.setDisplayName("Family Donation Detail#" + post.getFamily().getFamilyId())
					.addTrainingPhrases(Intent.TrainingPhrase.newBuilder()
							.addParts(Intent.TrainingPhrase.Part.newBuilder()
									.setText("Gia đình " + post.getFamily().getFamilyName())))
					.setWebhookState(Intent.WebhookState.WEBHOOK_STATE_ENABLED).build();
			AgentName parent = AgentName.of(projectId);
			Intent intentSaved = intentsClient.createIntent(parent, intent);

			model.addAttribute("post", post);
			org.thymeleaf.context.Context context = new Context();
			context.setVariables(model.asMap());

			String response = templateEngine.process("family-donation-detail-template.html", context);

			DialogflowIntent dialogflowIntent = DialogflowIntent.builder().displayName(intentSaved.getDisplayName())
					.name(intentSaved.getName()).response(response).build();
			intentRepository.save(dialogflowIntent);

			post.getFamily().setIntent(dialogflowIntent);
			familyRepository.save(post.getFamily());
		} else {
			DialogflowIntent intent = post.getFamily().getIntent();
			model.addAttribute("post", post);
			org.thymeleaf.context.Context context = new Context();
			context.setVariables(model.asMap());

			String response = templateEngine.process("family-donation-detail-template.html", context);
			intent.setResponse(response);
			intentRepository.save(intent);
		}
		return CompletableFuture.completedFuture(null);
	}
}
