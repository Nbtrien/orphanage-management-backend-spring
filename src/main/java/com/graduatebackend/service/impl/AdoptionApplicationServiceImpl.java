package com.graduatebackend.service.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.graduatebackend.constant.AdoptionCode;
import com.graduatebackend.constant.ChildrenCode;
import com.graduatebackend.dto.adoption.AccountAddAdoptionApplicationRequestDto;
import com.graduatebackend.dto.adoption.AccountAddNewAdoptionApplicationRequestDto;
import com.graduatebackend.dto.adoption.AddNewAdoptionApplicationRequestDto;
import com.graduatebackend.dto.adoption.GetAdoptionApplicationDetailResponseDto;
import com.graduatebackend.dto.adoption.GetAdoptionApplicationHistoryResponseDto;
import com.graduatebackend.dto.adoption.GetAdoptionApplicationResponseDto;
import com.graduatebackend.dto.adoption.GetApplicantDetailResponseDto;
import com.graduatebackend.dto.adoption.GetSpouseDetailResponseDto;
import com.graduatebackend.dto.children.GetChildrenResponseDto;
import com.graduatebackend.email.AttachmentInfo;
import com.graduatebackend.email.EmailService;
import com.graduatebackend.entity.Account;
import com.graduatebackend.entity.Address;
import com.graduatebackend.entity.AdoptionApplication;
import com.graduatebackend.entity.AdoptionApplicationDocument;
import com.graduatebackend.entity.Applicant;
import com.graduatebackend.entity.ApplicantSpouse;
import com.graduatebackend.entity.Children;
import com.graduatebackend.entity.Image;
import com.graduatebackend.entity.MaritalStatus;
import com.graduatebackend.exception.ResourceExistedException;
import com.graduatebackend.exception.ResourceNotFoundException;
import com.graduatebackend.mapper.AdoptionApplicationMapper;
import com.graduatebackend.mapper.ApplicantMapper;
import com.graduatebackend.mapper.ChildrenMapper;
import com.graduatebackend.repository.AccountRepository;
import com.graduatebackend.repository.AdoptionApplicationDocumentRepository;
import com.graduatebackend.repository.AdoptionApplicationRepository;
import com.graduatebackend.repository.ApplicantRepository;
import com.graduatebackend.repository.ChildrenRepository;
import com.graduatebackend.repository.DistrictRepository;
import com.graduatebackend.repository.ProvinceRepository;
import com.graduatebackend.repository.WardRepository;
import com.graduatebackend.service.AdoptionApplicationService;
import com.graduatebackend.service.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class AdoptionApplicationServiceImpl implements AdoptionApplicationService {
	private final AdoptionApplicationRepository adoptionApplicationRepository;
	private final ApplicantRepository applicantRepository;
	private final AdoptionApplicationDocumentRepository applicationDocumentRepository;
	private final ChildrenRepository childrenRepository;
	private final ProvinceRepository provinceRepository;
	private final DistrictRepository districtRepository;
	private final WardRepository wardRepository;
	private final FileService fileService;
	private final TemplateEngine templateEngine;
	private final EmailService emailService;
	private final AccountRepository accountRepository;

	@Override
	public Page<GetAdoptionApplicationResponseDto> fetch(PageRequest pageRequest, String applicationStatus) {
		Integer appStatus = applicationStatus == null ? null : Integer.valueOf(applicationStatus);
		Page<AdoptionApplication> adoptionApplications = adoptionApplicationRepository.findAll(pageRequest, appStatus);

		return adoptionApplications.map(application -> {
			GetAdoptionApplicationResponseDto responseDto = AdoptionApplicationMapper.INSTANCE
					.toGetAdoptionApplicationResponseDto(application);
			responseDto.setApplicationStatus(
					AdoptionCode.ApplicationStatus.of(application.getApplicationStatus()).getDisplay());
			return responseDto;
		});
	}

	@Override
	public List<GetAdoptionApplicationHistoryResponseDto> getAdoptionApplicationByAccount(Integer accountId) {
		Account account = accountRepository.findByAccountIdAndIsDeleteIsFalse(accountId)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found."));
		if (account.getApplicant().getAdoptionApplications().size() > 0) {
			List<GetAdoptionApplicationHistoryResponseDto> responseDtoList = account.getApplicant()
					.getAdoptionApplications().stream().map(application -> {
						GetAdoptionApplicationHistoryResponseDto responseDto = AdoptionApplicationMapper.INSTANCE
								.toGetAdoptionApplicationHistoryResponseDto(application);
						responseDto.setApplicationStatus(
								AdoptionCode.ApplicationStatus.of(application.getApplicationStatus()).getDisplay());
						if (application.getDocument().getApplicationPDFFilePath() != null)
							responseDto.setApplicationPDFFilePath(
									fileService.generateFileUrl(application.getDocument().getApplicationPDFFilePath()));
						if (application.getDocument().getChildrenPDFFilePath() != null)
							responseDto.setChildrenPDFFilePath(
									fileService.generateFileUrl(application.getDocument().getChildrenPDFFilePath()));
						return responseDto;
					}).collect(Collectors.toList());
			return responseDtoList;
		}
		return null;
	}

	@Override
	@Transactional
	public AdoptionApplication addNew(AddNewAdoptionApplicationRequestDto requestDto) {
		Optional<Applicant> applicantOptional = applicantRepository.findByMailAddressOrCitizenId(
				requestDto.getApplicant().getApplicantMailAddress(), requestDto.getApplicant().getCitizenIdNumber());
		if (applicantOptional.isPresent()) {
			throw new ResourceExistedException("Applicant is existed.");
		}
		AdoptionApplication application = AdoptionApplicationMapper.INSTANCE.toEntity(requestDto);

		LocalDate currentDate = LocalDate.now();
		LocalDate deadlineDate = currentDate.plusDays(30);
		application.setDateOfApplication(Date.valueOf(currentDate));
		application.setDeadlineDate(Date.valueOf(deadlineDate));

		// Set applicant and spouse full name
		Applicant applicant = application.getApplicant();
		applicant.setApplicantFullName(applicant.getApplicantLastName() + " " + applicant.getApplicantFirstName());
		Address address = Address.builder()
				.province(provinceRepository.getReferenceById(requestDto.getApplicant().getAddress().getProvinceId()))
				.district(districtRepository.getReferenceById(requestDto.getApplicant().getAddress().getDistrictId()))
				.ward(wardRepository.getReferenceById(requestDto.getApplicant().getAddress().getWardId()))
				.addressDetail(requestDto.getApplicant().getAddress().getAddressDetail()).build();

		applicant.setAddress(address);
		if (requestDto.getSpouse() != null) {
			ApplicantSpouse spouse = ApplicantMapper.INSTANCE.toApplicantSpouseEntity(requestDto.getSpouse());
			spouse.setSpouseFullName(spouse.getSpouseLastName() + " " + spouse.getSpouseFirstName());
			Address address1 = Address.builder()
					.province(provinceRepository.getReferenceById(requestDto.getSpouse().getAddress().getProvinceId()))
					.district(districtRepository.getReferenceById(requestDto.getSpouse().getAddress().getDistrictId()))
					.ward(wardRepository.getReferenceById(requestDto.getSpouse().getAddress().getWardId()))
					.addressDetail(requestDto.getSpouse().getAddress().getAddressDetail()).build();
			spouse.setAddress(address1);
			applicant.setSpouse(spouse);
		}
		application.setMaritalStatus(applicant.getMaritalStatus());
		AdoptionApplication applicationSaved = adoptionApplicationRepository.saveAndFlush(application);

		applicationSaved.setSpouse(applicationSaved.getApplicant().getSpouse());
		return adoptionApplicationRepository.saveAndFlush(applicationSaved);
	}

	@Transactional
	@Override
	public void accountAddNew(Model model, Integer accountId, AccountAddNewAdoptionApplicationRequestDto requestDto) {
		Account account = accountRepository.findByAccountIdAndIsDeleteIsFalse(accountId)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found."));
		Applicant applicant = account.getApplicant();
		applicant.setMaritalStatus(MaritalStatus.builder().maritalStatusId(requestDto.getMaritalStatusId()).build());
		applicant.setBackImage(Image.builder().imageFileName(requestDto.getBackImage().getImageFileName())
				.imageFilePath(requestDto.getBackImage().getImageFilePath()).build());
		applicant.setFrontImage(Image.builder().imageFileName(requestDto.getFrontImage().getImageFileName())
				.imageFilePath(requestDto.getFrontImage().getImageFilePath()).build());
		if (requestDto.getSpouse() != null) {
			ApplicantSpouse spouse = ApplicantMapper.INSTANCE.toApplicantSpouseEntity(requestDto.getSpouse());
			spouse.setSpouseFullName(spouse.getSpouseLastName() + " " + spouse.getSpouseFirstName());
			Address address1 = Address.builder()
					.province(provinceRepository.getReferenceById(requestDto.getSpouse().getAddress().getProvinceId()))
					.district(districtRepository.getReferenceById(requestDto.getSpouse().getAddress().getDistrictId()))
					.ward(wardRepository.getReferenceById(requestDto.getSpouse().getAddress().getWardId()))
					.addressDetail(requestDto.getSpouse().getAddress().getAddressDetail()).build();
			spouse.setAddress(address1);
			applicant.setSpouse(spouse);
		}

		Applicant applicantSaved = applicantRepository.save(applicant);

		LocalDate currentDate = LocalDate.now();
		LocalDate deadlineDate = currentDate.plusDays(30);

		AdoptionApplication application = AdoptionApplication.builder().applicant(applicant)
				.maritalStatus(applicantSaved.getMaritalStatus()).dateOfApplication(Date.valueOf(currentDate))
				.deadlineDate(Date.valueOf(deadlineDate)).spouse(applicantSaved.getSpouse())
				.reason(requestDto.getReason()).build();
		AdoptionApplication applicationSaved = adoptionApplicationRepository.saveAndFlush(application);
		sendEmailWhenAccountMakeApplication(model, applicationSaved.getAdoptionApplicationId());
	}

	@Override
	@Transactional
	public void accountAddApplication(Model model, Integer accountId,
			AccountAddAdoptionApplicationRequestDto requestDto) {
		Account account = accountRepository.findByAccountIdAndIsDeleteIsFalse(accountId)
				.orElseThrow(() -> new ResourceNotFoundException("Account not found."));
		Applicant applicant = account.getApplicant();
		if (requestDto.getMaritalStatusId() != null) {
			applicant
					.setMaritalStatus(MaritalStatus.builder().maritalStatusId(requestDto.getMaritalStatusId()).build());
			if (requestDto.getMaritalStatusId() != AdoptionCode.MaritalStatusCode.MARRIED.getCode()) {
				applicant.setSpouse(null);
			}
		}
		if (requestDto.getSpouse() != null) {
			ApplicantSpouse spouse = ApplicantMapper.INSTANCE.toApplicantSpouseEntity(requestDto.getSpouse());
			spouse.setSpouseFullName(spouse.getSpouseLastName() + " " + spouse.getSpouseFirstName());
			Address address1 = Address.builder()
					.province(provinceRepository.getReferenceById(requestDto.getSpouse().getAddress().getProvinceId()))
					.district(districtRepository.getReferenceById(requestDto.getSpouse().getAddress().getDistrictId()))
					.ward(wardRepository.getReferenceById(requestDto.getSpouse().getAddress().getWardId()))
					.addressDetail(requestDto.getSpouse().getAddress().getAddressDetail()).build();
			spouse.setAddress(address1);
			applicant.setSpouse(spouse);
		}
		Applicant applicantSaved = applicantRepository.save(applicant);

		LocalDate currentDate = LocalDate.now();
		LocalDate deadlineDate = currentDate.plusDays(30);

		AdoptionApplication application = AdoptionApplication.builder().applicant(applicant)
				.maritalStatus(applicantSaved.getMaritalStatus()).dateOfApplication(Date.valueOf(currentDate))
				.deadlineDate(Date.valueOf(deadlineDate)).spouse(applicantSaved.getSpouse())
				.reason(requestDto.getReason()).build();
		AdoptionApplication applicationSaved = adoptionApplicationRepository.saveAndFlush(application);
		sendEmailWhenAccountMakeApplication(model, applicationSaved.getAdoptionApplicationId());
	}

	@Override
	public GetAdoptionApplicationDetailResponseDto getDetail(Integer id) {
		AdoptionApplication application = adoptionApplicationRepository
				.findByAdoptionApplicationIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Adoption application not found."));

		GetAdoptionApplicationDetailResponseDto responseDto = AdoptionApplicationMapper.INSTANCE
				.toGetAdoptionApplicationDetailResponseDto(application);
		responseDto.setApplicationStatus(
				AdoptionCode.ApplicationStatus.of(application.getApplicationStatus()).getDisplay());

		Applicant applicant = application.getApplicant();
		GetApplicantDetailResponseDto applicationResponseDto = responseDto.getApplicant();

		applicationResponseDto
				.setCitizenIdFrontImageUrl(fileService.generateFileUrl(applicant.getFrontImage().getImageFilePath()));
		applicationResponseDto
				.setCitizenIdBackImageUrl(fileService.generateFileUrl(applicant.getBackImage().getImageFilePath()));
		applicationResponseDto.setMaritalStatus(application.getMaritalStatus().getMaritalStatusName());
		applicationResponseDto.setApplicantGender(ChildrenCode.Gender.of(applicant.getApplicantGender()).getDisplay());

		if (application.getSpouse() != null) {
			GetSpouseDetailResponseDto spouse = ApplicantMapper.INSTANCE
					.toGetSpouseDetailResponseDto(application.getSpouse());
			spouse.setSpouseGender(ChildrenCode.Gender.of(application.getSpouse().getSpouseGender()).getDisplay());
			applicationResponseDto.setSpouse(spouse);
		}

		return responseDto;
	}

	@Override
	public void confirmApplication(Integer id) {
		AdoptionApplication application = adoptionApplicationRepository
				.findByAdoptionApplicationIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Adoption application not found."));

		application.setApplicationStatus(AdoptionCode.ApplicationStatus.APPROVED.getCode());
		application.setDateOfProcessing(new Date(System.currentTimeMillis()));
		adoptionApplicationRepository.save(application);
	}

	@Override
	public void declineApplication(Integer id) {
		AdoptionApplication application = adoptionApplicationRepository
				.findByAdoptionApplicationIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Adoption application not found."));

		application.setApplicationStatus(AdoptionCode.ApplicationStatus.DECLINED.getCode());
		application.setDateOfProcessing(new Date(System.currentTimeMillis()));
		adoptionApplicationRepository.save(application);
	}

	@Async
	@Override
	public CompletableFuture<Void> generatePdfAndSendEmailConfirm(Model model, Integer id) {
		AdoptionApplication application = adoptionApplicationRepository
				.findByAdoptionApplicationIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Adoption application not found."));

		GetAdoptionApplicationDetailResponseDto responseDto = AdoptionApplicationMapper.INSTANCE
				.toGetAdoptionApplicationDetailResponseDto(application);

		Applicant applicant = application.getApplicant();
		GetApplicantDetailResponseDto applicationResponseDto = responseDto.getApplicant();
		applicationResponseDto.setApplicantGender(ChildrenCode.Gender.of(applicant.getApplicantGender()).getDisplay());
		if (applicationResponseDto.getSpouse() != null) {
			applicationResponseDto.getSpouse()
					.setSpouseGender(ChildrenCode.Gender.of(applicant.getSpouse().getSpouseGender()).getDisplay());
		}

		Optional<AdoptionApplicationDocument> applicationDocumentOptional = applicationDocumentRepository
				.findByAdoptionApplicationId(application.getAdoptionApplicationId());
		AdoptionApplicationDocument applicationDocument = null;
		byte[] applicationPdf = null;
		if (applicationDocumentOptional.isPresent()) {
			applicationDocument = applicationDocumentOptional.get();
			applicationPdf = fileService.downloadFromS3(applicationDocument.getApplicationPDFFilePath());
		} else {
			applicationDocument = new AdoptionApplicationDocument();
			applicationDocument.setApplication(application);

			applicationPdf = generateApplicationPdf(model, responseDto);
			String applicationPdfFilePath = fileService.uploadFileToS3(applicationPdf, "applications",
					applicant.getApplicantId() + "-application-form" + ".pdf");
			applicationDocument.setApplicationPDFFilePath(applicationPdfFilePath);
		}
		byte[] awaitingListPdf = generateAwaitingAdoptionPdf(model);
		String awaitingAdoptionPdfFilePath = fileService.uploadFileToS3(awaitingListPdf, "applications",
				applicant.getApplicantId() + "-awaiting" + "-list" + ".pdf");
		applicationDocument.setChildrenPDFFilePath(awaitingAdoptionPdfFilePath);
		applicationDocumentRepository.save(applicationDocument);
		AttachmentInfo[] attachments = { new AttachmentInfo("Application_Form.pdf", "application/pdf", applicationPdf),
				new AttachmentInfo("Awaiting_Adoption_List.pdf", "application/pdf", awaitingListPdf) };

		String subject = "Thông báo đăng ký nhận thông tin trẻ em đang chờ nhận nuôi";
		String message = "Kính gửi ông/bà,\n\n"
				+ "Cảm ơn ông/bà đã quan tâm đến việc nhận nuôi trẻ em tại trại trẻ chúng tôi. \n"
				+ "Chúng tôi xin thông báo rằng đơn đăng ký nhận thông tin trẻ em đang chờ nhận nuôi của ông/bà "
				+ "đã" + " được chấp nhận. \n"
				+ "Để thuận tiện cho việc tìm kiếm trẻ em phù hợp, ông/bà vui lòng tham khảo danh sách trẻ được" + " "
				+ "đính kèm bên dưới. \n\n" + "Mọi thắc mắc và góp ý, xin vui lòng liên hệ với chúng tôi qua:\n"
				+ "Email: support.orphanage@gmail.com\n" + "Hotline: 0123456789\n\n" + "Trân trọng,\n" + "Orphanage";
		emailService.sendEmailWithAttachments(applicationResponseDto.getApplicantMailAddress(), subject, message,
				attachments);

		application.setSentDetailedEmail(true);
		adoptionApplicationRepository.save(application);

		return CompletableFuture.completedFuture(null);
	}

	@Async
	@Override
	public CompletableFuture<Void> sendEmailDecline(Integer id) {
		AdoptionApplication application = adoptionApplicationRepository
				.findByAdoptionApplicationIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Adoption application not found."));

		String subject = "Thông báo đăng ký nhận thông tin trẻ em đang chờ nhận nuôi";
		String message = "Kính gửi ông/bà,\n\n"
				+ "Cảm ơn ông/bà đã quan tâm đến việc nhận nuôi trẻ em tại trại trẻ chúng tôi. \n"
				+ "Sau khi xem xét đơn đăng ký của ông/bà. Chúng tôi xin thông báo rằng đơn đăng ký nhận thông "
				+ "tin trẻ em đang chờ được nhận nuôi của ông/bà không được chấp nhận. \n\n"
				+ "Mọi thắc mắc và góp ý, xin vui lòng liên hệ với chúng tôi qua:\n"
				+ "Email: support.orphanage@gmail.com\n" + "Hotline: 0123456789\n\n" + "Trân trọng,\n" + "Orphanage";
		emailService.sendEmailWithSimpleMessage(application.getApplicant().getApplicantMailAddress(), subject, message);

		application.setSentDetailedEmail(true);
		adoptionApplicationRepository.save(application);
		return CompletableFuture.completedFuture(null);
	}

	@Async
	@Override
	public CompletableFuture<Void> saveApplicationDocumentAndSendEmail(Model model, AdoptionApplication application) {
		log.info("saveApplicationDocumentAndSendEmail");
		GetAdoptionApplicationDetailResponseDto responseDto = AdoptionApplicationMapper.INSTANCE
				.toGetAdoptionApplicationDetailResponseDto(application);
		log.info(responseDto);
		// Create GetAdoptionApplicationDetailResponseDto from AdoptionApplication
		Applicant applicant = application.getApplicant();
		GetApplicantDetailResponseDto applicationResponseDto = responseDto.getApplicant();
		applicationResponseDto.setApplicantGender(ChildrenCode.Gender.of(applicant.getApplicantGender()).getDisplay());
		if (applicationResponseDto.getSpouse() != null) {
			applicationResponseDto.getSpouse()
					.setSpouseGender(ChildrenCode.Gender.of(applicant.getSpouse().getSpouseGender()).getDisplay());
		}

		// Generate application pdf file and store in s3
		byte[] applicationPdf = generateApplicationPdf(model, responseDto);
		String applicationPdfFilePath = fileService.uploadFileToS3(applicationPdf, "applications",
				application.getAdoptionApplicationId() + "-application-form.pdf");
		log.info("Generate application pdf file and store in s3");
		// Create new AdoptionApplicationDocument entity and save it
		AdoptionApplicationDocument applicationDocument = AdoptionApplicationDocument.builder().application(application)
				.applicationPDFFilePath(applicationPdfFilePath).build();
		applicationDocumentRepository.save(applicationDocument);
		log.info("Create new AdoptionApplicationDocument entity and save it");
		// Send email to applicant
		AttachmentInfo[] attachments = {
				new AttachmentInfo("Application_Form.pdf", "application/pdf", applicationPdf) };
		String subject = "Thông báo đăng ký nhận thông tin trẻ em đang chờ nhận nuôi";
		String message = "Kính gửi ông/bà,\n\n"
				+ "Cảm ơn ông/bà đã quan tâm đến việc nhận nuôi trẻ em tại trại trẻ chúng tôi. \n"
				+ "Đơn đăng ký của ông/bà sẽ được xem xét và phản hồi lại trong thời gian sớm nhất. \n\n"
				+ "Mọi thắc mắc và góp ý, xin vui lòng liên hệ với chúng tôi qua:\n"
				+ "Email: support.orphanage@gmail.com\n" + "Hotline: 0123456789\n\n" + "Trân trọng,\n" + "Orphanage";
		emailService.sendEmailWithAttachments(application.getApplicant().getApplicantMailAddress(), subject, message,
				attachments);

		return CompletableFuture.completedFuture(null);
	}

	private byte[] generateApplicationPdf(Model model, GetAdoptionApplicationDetailResponseDto adoptionApplication) {
		model.addAttribute("applicant", adoptionApplication.getApplicant());

		model.addAttribute("dateOfApplication", adoptionApplication.getDateOfApplication());
		model.addAttribute("reason", adoptionApplication.getReason());
		Context context = new Context();
		String htmlContent = null;
		if (adoptionApplication.getApplicant().getSpouse() != null) {
			model.addAttribute("spouse", adoptionApplication.getApplicant().getSpouse());
			context.setVariables(model.asMap());
			htmlContent = templateEngine.process("application_pdf.html", context);
		} else {
			context.setVariables(model.asMap());
			htmlContent = templateEngine.process("application_pdf(1).html", context);
		}
		try {
			return fileService.generatePdf(htmlContent);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Async
	public CompletableFuture<Void> sendEmailWhenAccountMakeApplication(Model model, Integer id) {
		AdoptionApplication application = adoptionApplicationRepository
				.findByAdoptionApplicationIdAndIsDeleteIsFalse(id).orElseThrow(null);
		GetAdoptionApplicationDetailResponseDto responseDto = AdoptionApplicationMapper.INSTANCE
				.toGetAdoptionApplicationDetailResponseDto(application);

		// Create GetAdoptionApplicationDetailResponseDto from AdoptionApplication
		Applicant applicant = application.getApplicant();
		GetApplicantDetailResponseDto applicationResponseDto = responseDto.getApplicant();
		applicationResponseDto.setApplicantGender(ChildrenCode.Gender.of(applicant.getApplicantGender()).getDisplay());
		if (applicationResponseDto.getSpouse() != null) {
			applicationResponseDto.getSpouse()
					.setSpouseGender(ChildrenCode.Gender.of(applicant.getSpouse().getSpouseGender()).getDisplay());
		}

		// Generate application pdf file and store in s3
		byte[] applicationPdf = generateApplicationPdf(model, responseDto);
		String applicationPdfFilePath = fileService.uploadFileToS3(applicationPdf, "applications",
				application.getAdoptionApplicationId() + "-application-form.pdf");
		log.info("Generate application pdf file and store in s3");
		// Create new AdoptionApplicationDocument entity and save it
		AdoptionApplicationDocument applicationDocument = AdoptionApplicationDocument.builder().application(application)
				.applicationPDFFilePath(applicationPdfFilePath).build();
		applicationDocumentRepository.save(applicationDocument);

		log.info("Create new AdoptionApplicationDocument entity and save it");
		// Send email to applicant
		AttachmentInfo[] attachments = {
				new AttachmentInfo("Application_Form.pdf", "application/pdf", applicationPdf) };
		String subject = "Thông báo đăng ký nhận thông tin trẻ em đang chờ nhận nuôi";
		String message = "Kính gửi ông/bà,\n\n"
				+ "Cảm ơn ông/bà đã quan tâm đến việc nhận nuôi trẻ em tại trại trẻ chúng tôi. \n"
				+ "Đơn đăng ký của ông/bà sẽ được xem xét và phản hồi lại trong thời gian sớm nhất. \n\n"
				+ "Mọi thắc mắc và góp ý, xin vui lòng liên hệ với chúng tôi qua:\n"
				+ "Email: support.orphanage@gmail.com\n" + "Hotline: 0123456789\n\n" + "Trân trọng,\n" + "Orphanage";
		emailService.sendEmailWithAttachments(application.getApplicant().getApplicantMailAddress(), subject, message,
				attachments);

		return CompletableFuture.completedFuture(null);
	}

	private byte[] generateAwaitingAdoptionPdf(Model model) {
		List<Children> childrenList = childrenRepository
				.findAllAwaitingAdoption(ChildrenCode.ChildrenStatus.IN_CARE.getCode());

		List<GetChildrenResponseDto> responseDtoList = childrenList.stream().map(children -> {
			GetChildrenResponseDto responseDto = ChildrenMapper.INSTANCE.toGetChildrenResponseDto(children);
			responseDto.setChildrenGender(ChildrenCode.Gender.of(children.getChildrenGender()).getDisplay());
			// Create s3 image url from image file path
			responseDto.setImageUrl(fileService.generateFileUrl(children.getImage().getImageFilePath()));
			return responseDto;
		}).collect(Collectors.toList());

		Date currentDate = new Date(System.currentTimeMillis());

		model.addAttribute("children", responseDtoList);
		model.addAttribute("currentDate", currentDate);
		Context context = new Context();
		context.setVariables(model.asMap());
		String htmlContent = templateEngine.process("awaiting_adoption_pdf.html", context);
		try {
			return fileService.generatePdf(htmlContent);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
