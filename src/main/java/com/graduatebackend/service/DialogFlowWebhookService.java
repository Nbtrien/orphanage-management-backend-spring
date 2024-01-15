package com.graduatebackend.service;

import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.ui.Model;

public interface DialogFlowWebhookService {
	/**
	 * get response for Contact Intent
	 *
	 * @param parameters
	 * @return
	 */
	String getContactIntent(JSONObject parameters);

	/**
	 * get response for Event Intent
	 *
	 * @param model
	 * @return
	 */
	String getEventsIntent(Model model);

	/**
	 * get response for Donation Intent
	 *
	 * @param model
	 * @return
	 */
	String getDonationProgramsIntent(Model model);

	/**
	 * get response for Donation Way Intent
	 *
	 * @param model
	 * @return
	 */
	String getDonationWayIntent(Model model);

	/**
	 * get response for Family Donation Intent
	 *
	 * @param model
	 * @return
	 */
	String getFamilyDonationIntent(Model model);

	/**
	 * get response for Donation tracking Intent
	 *
	 * @param model
	 * @return
	 */
	String getDonationTrackingIntent(Model model);

	/**
	 * get response for Adoption Way Intent
	 *
	 * @param model
	 * @return
	 */
	String getAdoptionWayIntent(Model model);

	/**
	 * get response by intent name
	 *
	 * @param intentName
	 * @return
	 */
	String getIntentByName(String intentName);
}
