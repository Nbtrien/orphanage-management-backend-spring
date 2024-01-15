package com.graduatebackend.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.ui.Model;

import com.graduatebackend.entity.DonationPurposePost;
import com.graduatebackend.entity.Event;
import com.graduatebackend.entity.FamilyPost;

public interface DialogflowService {
	/**
	 * detect intent
	 *
	 * @param userInput
	 * @return
	 */
	String detectIntent(String userInput);

	/**
	 * create intent for event with id
	 *
	 * @param model
	 * @param eventId
	 */
	void createEventIntent(Model model, Integer eventId);

	/**
	 * create intent for donation program with id
	 *
	 * @param model
	 * @param id
	 */
	void createDonationProgramIntent(Model model, Integer id);

	/**
	 * create intent for family with id
	 *
	 * @param model
	 * @param id
	 */
	void createFamilyDonationIntent(Model model, Integer id);

	/**
	 * create intent for event
	 *
	 * @param model
	 * @param event
	 * @return
	 */
	CompletableFuture<Void> createEventIntent(Model model, Event event);

	/**
	 * create intent for donation program
	 *
	 * @param model
	 * @param post
	 * @return
	 */
	CompletableFuture<Void> createDonationProgramIntent(Model model, DonationPurposePost post);

	/**
	 * create intent for family
	 *
	 * @param model
	 * @param post
	 * @return
	 */
	CompletableFuture<Void> createFamilyDonationIntent(Model model, FamilyPost post);
}
