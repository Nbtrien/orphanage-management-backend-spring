package com.graduatebackend.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.graduatebackend.dto.articles.GetDonationProgramResponseDto;
import com.graduatebackend.dto.articles.GetFamilyPostResponseDto;
import com.graduatebackend.entity.DialogflowIntent;
import com.graduatebackend.entity.DonationPurpose;
import com.graduatebackend.entity.Event;
import com.graduatebackend.entity.Family;
import com.graduatebackend.entity.WebsiteContact;
import com.graduatebackend.mapper.DonationPurposeMapper;
import com.graduatebackend.mapper.FamilyMapper;
import com.graduatebackend.repository.DialogflowIntentRepository;
import com.graduatebackend.repository.DonationPurposeRepository;
import com.graduatebackend.repository.EventRepository;
import com.graduatebackend.repository.FamilyRepository;
import com.graduatebackend.repository.WebsiteContactRepository;
import com.graduatebackend.service.DialogFlowWebhookService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DialogFlowWebhookServiceImpl implements DialogFlowWebhookService {
	private final WebsiteContactRepository contactRepository;
	private final EventRepository eventRepository;
	private final TemplateEngine templateEngine;
	private final DonationPurposeRepository donationPurposeRepository;
	private final FamilyRepository familyRepository;
	private final DialogflowIntentRepository intentRepository;

	@Override
	public String getContactIntent(JSONObject parameters) {
		try {
			JSONArray jsonArray = parameters.getJSONArray("contact");
			String message = "";
			if (jsonArray.length() > 0) {
				for (int i = 0; i < jsonArray.length(); i++) {
					switch (jsonArray.getString(i)) {
					case "address":
						message += "Địa chỉ của trại trẻ là " + contactRepository.getAddress() + " ";
						break;
					case "email":
						message += "Địa chỉ email của trại trẻ là " + contactRepository.getEmail() + " ";
						break;
					case "phone_number":
						message += "Số điện thoại của trại trẻ là " + contactRepository.getPhoneNumber() + " ";
						break;
					}
				}
			} else {
				Optional<WebsiteContact> websiteContact = contactRepository.findOne();
				message = websiteContact
						.map(contact -> "Bạn có thể liên hệ với chúng tôi qua số điện thoại " + contact.getPhoneNumber()
								+ " hoặc đến địa chỉ " + contact.getAddress() + ". Email của chúng tôi là "
								+ contact.getMailAddress())
						.orElse("Xin lỗi, hiện tại không có thông tin liên hệ cho trại trẻ");

			}
			return message;
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String getEventsIntent(Model model) {
		PageRequest pageRequest = PageRequest.of(0, 4, Sort.Direction.DESC, "eventStartDate");
		Page<Event> events = eventRepository.findAllEventPost(pageRequest);
		model.addAttribute("events", events.getContent());
		Context context = new Context();
		context.setVariables(model.asMap());

		return templateEngine.process("event-template.html", context);
	}

	@Override
	public String getDonationProgramsIntent(Model model) {
		PageRequest pageRequest = PageRequest.of(0, 4);
		Page<DonationPurpose> donationPurposes = donationPurposeRepository.findAll(pageRequest, null, true);

		List<GetDonationProgramResponseDto> donationPrograms = donationPurposes.stream()
				.map(DonationPurposeMapper.INSTANCE::toGetDonationProgramResponseDto).collect(Collectors.toList());

		model.addAttribute("programs", donationPrograms);
		Context context = new Context();
		context.setVariables(model.asMap());

		return templateEngine.process("donation-programs.html", context);
	}

	@Override
	public String getDonationWayIntent(Model model) {
		Context context = new Context();
		return templateEngine.process("donation-way.html", context);
	}

	@Override
	public String getFamilyDonationIntent(Model model) {
		List<Family> families = familyRepository.getFamilyPosts();
		List<GetFamilyPostResponseDto> responseDtoList = families.stream().map(family -> {
			GetFamilyPostResponseDto familyPost = FamilyMapper.INSTANCE.toGetFamilyPostResponseDto(family);
			familyPost.setTitle(family.getPost().getTitle());
			familyPost.setSummary(family.getPost().getSummary());
			return familyPost;
		}).collect(Collectors.toList());
		model.addAttribute("families", responseDtoList);
		Context context = new Context();
		context.setVariables(model.asMap());

		return templateEngine.process("family-donation.html", context);
	}

	@Override
	public String getDonationTrackingIntent(Model model) {
		Context context = new Context();
		return templateEngine.process("donation-tracking.html", context);
	}

	@Override
	public String getAdoptionWayIntent(Model model) {
		Context context = new Context();
		return templateEngine.process("adoption-way-template.html", context);
	}

	@Override
	public String getIntentByName(String intentName) {
		Optional<DialogflowIntent> intent = intentRepository.findByDisplayNameAndIsDeleteIsFalse(intentName);
		return intent.map(DialogflowIntent::getResponse).orElse(null);
	}
}
