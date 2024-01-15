package com.graduatebackend.controller;

import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.graduatebackend.dto.dialogflow.FulfillmentResponseDto;
import com.graduatebackend.service.DialogFlowWebhookService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/dialogflow-webhook")
@CrossOrigin
@RequiredArgsConstructor
public class DialogFlowWebhookController {
	private final DialogFlowWebhookService webhookService;

	@PostMapping()
	public Object handleWebhook(HttpEntity<String> httpEntity, Model model) throws JSONException {
		String reqObject = httpEntity.getBody();
		JSONObject obj = new JSONObject(reqObject);

		JSONObject queryResult = obj.getJSONObject("queryResult");
		String intent = queryResult.getJSONObject("intent").getString("displayName");
		String fulfillmentText;
		if (queryResult.has("fulfillmentText")) {
			fulfillmentText = queryResult.getString("fulfillmentText");
		} else {
			fulfillmentText = "Xin lỗi, tôi không hiểu.";
		}
		JSONObject parameters = queryResult.getJSONObject("parameters");

		switch (intent) {
		case "Contact":
			fulfillmentText = webhookService.getContactIntent(parameters);
			break;
		case "Events":
			fulfillmentText = webhookService.getEventsIntent(model);
			break;
		case "Donation Programs":
			fulfillmentText = webhookService.getDonationProgramsIntent(model);
			break;
		case "Donation Way":
			fulfillmentText = webhookService.getDonationWayIntent(model);
			break;
		case "Family Donation":
			fulfillmentText = webhookService.getFamilyDonationIntent(model);
			break;
		case "Donation Tracking":
			fulfillmentText = webhookService.getDonationTrackingIntent(model);
			break;
		case "Adoption Way":
			fulfillmentText = webhookService.getAdoptionWayIntent(model);
			break;
		default:
			fulfillmentText = webhookService.getIntentByName(intent);
			if (fulfillmentText == null)
				fulfillmentText = "Xin lỗi, tôi không hiểu";
			break;
		}
		FulfillmentResponseDto response = new FulfillmentResponseDto();
		response.setFulfillmentText(fulfillmentText);
		return response;
	}
}
