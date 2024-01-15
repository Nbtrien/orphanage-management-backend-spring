package com.graduatebackend.service.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.graduatebackend.dto.articles.AddNewFamilyPostImageRequestDto;
import com.graduatebackend.dto.articles.AddNewFamilyPostRequestDto;
import com.graduatebackend.dto.articles.GetFamilyPostDetailResponseDto;
import com.graduatebackend.dto.articles.GetFamilyPostResponseDto;
import com.graduatebackend.dto.articles.GetFamilyPostUpdateResponseDto;
import com.graduatebackend.dto.children.GetChildrenFamilyHistoryResponseDto;
import com.graduatebackend.dto.family.AddNewFamilyConditionRequestDto;
import com.graduatebackend.dto.family.AddNewFamilyRequestDto;
import com.graduatebackend.dto.family.GetFamilyConditionResponseDto;
import com.graduatebackend.dto.family.GetFamilyDonationDetailResponseDto;
import com.graduatebackend.dto.family.GetFamilyDonationStatsResponseDto;
import com.graduatebackend.dto.family.GetFamilyForDonateResponseDto;
import com.graduatebackend.dto.family.GetFamilyResponseDto;
import com.graduatebackend.dto.family.GetMotherResponseDto;
import com.graduatebackend.entity.Children;
import com.graduatebackend.entity.ChildrenFamilyHistory;
import com.graduatebackend.entity.Family;
import com.graduatebackend.entity.FamilyCondition;
import com.graduatebackend.entity.FamilyPost;
import com.graduatebackend.entity.FamilyPostImage;
import com.graduatebackend.entity.Mother;
import com.graduatebackend.entity.MotherFamilyHistory;
import com.graduatebackend.exception.ResourceExistedException;
import com.graduatebackend.exception.ResourceNotFoundException;
import com.graduatebackend.mapper.FamilyMapper;
import com.graduatebackend.repository.ChildrenFamilyHistoryRepository;
import com.graduatebackend.repository.ChildrenRepository;
import com.graduatebackend.repository.FamilyConditionRepository;
import com.graduatebackend.repository.FamilyPostImageRepository;
import com.graduatebackend.repository.FamilyPostRepository;
import com.graduatebackend.repository.FamilyRepository;
import com.graduatebackend.repository.MotherFamilyHistoryRepository;
import com.graduatebackend.repository.MotherRepository;
import com.graduatebackend.service.FamilyService;
import com.graduatebackend.service.FileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FamilyServiceImpl implements FamilyService {
	private final FamilyConditionRepository familyConditionRepository;
	private final FamilyRepository familyRepository;
	private final MotherRepository motherRepository;
	private final ChildrenRepository childrenRepository;
	private final FamilyPostRepository familyPostRepository;
	private final FamilyPostImageRepository familyPostImageRepository;
	private final FileService fileService;
	private final ChildrenFamilyHistoryRepository childrenFamilyHistoryRepository;
	private final MotherFamilyHistoryRepository motherFamilyHistoryRepository;

	@Override
	public List<GetFamilyConditionResponseDto> fetchAllFamilyConditions() {
		List<FamilyCondition> familyConditions = familyConditionRepository.findByIsDeleteIsFalse();
		return FamilyMapper.INSTANCE.toGetFamilyConditionResponseDtoList(familyConditions);
	}

	@Override
	public List<GetMotherResponseDto> fetchAllMothersAvailable() {
		List<Mother> mothers = motherRepository.findByIsDeleteIsFalseAndFamilyIsNull();

		return mothers.stream().map(mother -> {
			GetMotherResponseDto responseDto = FamilyMapper.INSTANCE.toGetMotherResponseDto(mother);
			responseDto.setImageUrl(fileService.generateFileUrl(mother.getEmployee().getImage().getImageFilePath()));
			return responseDto;
		}).collect(Collectors.toList());
	}

	@Override
	public Page<GetFamilyConditionResponseDto> fetchConditions(PageRequest pageRequest) {
		Page<FamilyCondition> familyConditions = familyConditionRepository.findByIsDeleteIsFalse(pageRequest);
		return familyConditions.map(familyCondition -> {
			GetFamilyConditionResponseDto responseDto = FamilyMapper.INSTANCE
					.toGetFamilyConditionResponseDto(familyCondition);
			responseDto.setNoOfFamily(familyCondition.getFamilies().size());
			return responseDto;
		});
	}

	@Override
	public Page<GetFamilyResponseDto> fetchFamilies(PageRequest pageRequest) {
		Page<Family> families = familyRepository.findByIsDeleteIsFalse(pageRequest);
		return families.map(family -> {
			GetFamilyResponseDto responseDto = FamilyMapper.INSTANCE.toGetFamilyResponseDto(family);
			Integer noOfChildren = Math.toIntExact(family.getChildren().stream().filter(c -> !c.isDelete()).count());
			responseDto.setNoOfChildren(noOfChildren);
			responseDto.setPosted(family.getPost() != null);
			return responseDto;
		});
	}

	@Override
	public Page<GetFamilyResponseDto> searchFamilies(PageRequest pageRequest, String keyword, Integer familyConditionId,
			String fromDate, String toDate) {
		Date minDateOfFormation = fromDate == null ? null : Date.valueOf(fromDate);
		Date maxDateOfFormation = toDate == null ? null : Date.valueOf(toDate);

		Page<Family> families = familyRepository.searchByKeyword(pageRequest, keyword, familyConditionId,
				minDateOfFormation, maxDateOfFormation);
		return families.map(family -> {
			GetFamilyResponseDto responseDto = FamilyMapper.INSTANCE.toGetFamilyResponseDto(family);
			Integer noOfChildren = Math.toIntExact(family.getChildren().stream().filter(c -> !c.isDelete()).count());
			responseDto.setNoOfChildren(noOfChildren);
			responseDto.setPosted(family.getPost() != null);
			return responseDto;
		});
	}

	@Override
	public List<GetFamilyResponseDto> fetchFamiliesForChildren(Integer childrenId) {
		Children children = childrenRepository.findByChildrenIdAndIsDeleteIsFalse(childrenId)
				.orElseThrow(() -> new ResourceNotFoundException("Children not found."));

		LocalDate currentDate = LocalDate.now();
		LocalDate dateOfBirth = children.getChildrenDateOfBirth().toLocalDate();
		Period period = Period.between(dateOfBirth, currentDate);
		double age = period.getYears() + ((double) period.getMonths() / 12) + (period.getDays() / 365.25);

		List<Family> families = familyRepository.findFamilyByAgeCondition(age);
		return families.stream().filter(family -> {
			Integer noOfChildren = Math.toIntExact(family.getChildren().stream().filter(c -> !c.isDelete()).count());
			return noOfChildren < family.getCondition().getMaxNumberOfChildren();
		}).map(family -> {
			GetFamilyResponseDto responseDto = FamilyMapper.INSTANCE.toGetFamilyResponseDto(family);
			Integer noOfChildren = Math.toIntExact(family.getChildren().stream().filter(c -> !c.isDelete()).count());
			responseDto.setNoOfChildren(noOfChildren);
			return responseDto;
		}).collect(Collectors.toList());
	}

	@Override
	public List<GetFamilyForDonateResponseDto> fetchAllFamiliesForDonate() {
		List<Family> families = familyRepository.findByIsDeleteIsFalse();
		return families.stream().map(FamilyMapper.INSTANCE::toGetFamilyForDonateResponseDto)
				.collect(Collectors.toList());
	}

	@Override
	public GetFamilyResponseDto getDetail(Integer id) {
		Family family = familyRepository.findFamilyByFamilyIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Family not found."));
		GetFamilyResponseDto responseDto = FamilyMapper.INSTANCE.toGetFamilyResponseDto(family);
		Integer onOfChildren = Math.toIntExact(family.getChildren().stream().filter(c -> !c.isDelete()).count());
		responseDto.setNoOfChildren(onOfChildren);
		return responseDto;
	}

	@Override
	public Page<GetFamilyDonationStatsResponseDto> getFamilyDonationStats(PageRequest pageRequest, String keyword) {
		Page<Family> families = familyRepository.searchByKeyword(pageRequest, keyword, null, null, null);
		Page<GetFamilyDonationStatsResponseDto> responseDtoPage = families.map(family -> {
			GetFamilyDonationStatsResponseDto familyDonationStats = new GetFamilyDonationStatsResponseDto();
			familyDonationStats.setFamilyId(family.getFamilyId());
			familyDonationStats.setFamilyName(family.getFamilyName());

			List<Object[]> familyDonationInfoObjects = familyRepository.getFamilyDonationInfo(family.getFamilyId());
			familyDonationStats.setDonationCount((Long) familyDonationInfoObjects.get(0)[0]);
			familyDonationStats.setTotalDonationAmount(
					familyDonationInfoObjects.get(0)[1] == null ? null : (Double) familyDonationInfoObjects.get(0)[1]);
			List<Object[]> familyFundingInfoObject = familyRepository.getFamilyFundingInfo(family.getFamilyId());
			if (familyFundingInfoObject.get(0) != null) {
				familyDonationStats.setFundingAmount(
						familyFundingInfoObject.get(0)[0] == null ? null : (Double) familyFundingInfoObject.get(0)[0]);
			}
			return familyDonationStats;
		});

		return responseDtoPage;
	}

	@Override
	public GetFamilyDonationDetailResponseDto getFamilyDonationStatsDetail(Integer id) {
		Family family = familyRepository.findFamilyByFamilyIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Family not found."));
		GetFamilyDonationDetailResponseDto familyDonationDetail = FamilyMapper.INSTANCE
				.toGetFamilyDonationDetailResponseDto(family);
		Integer onOfChildren = Math.toIntExact(family.getChildren().stream().filter(c -> !c.isDelete()).count());
		familyDonationDetail.setNoOfChildren(onOfChildren);

		List<Object[]> familyDonationInfoObjects = familyRepository.getFamilyDonationInfo(family.getFamilyId());
		familyDonationDetail.setDonationCount((Long) familyDonationInfoObjects.get(0)[0]);
		familyDonationDetail.setTotalDonationAmount(
				familyDonationInfoObjects.get(0)[1] == null ? null : (Double) familyDonationInfoObjects.get(0)[1]);
		List<Object[]> familyFundingInfoObject = familyRepository.getFamilyFundingInfo(family.getFamilyId());
		if (familyFundingInfoObject.get(0) != null) {
			familyDonationDetail.setFundingAmount(
					familyFundingInfoObject.get(0)[0] == null ? null : (Double) familyFundingInfoObject.get(0)[0]);
		}
		return familyDonationDetail;
	}

	@Override
	public GetFamilyPostUpdateResponseDto getFamilyPostUpdate(Integer familyId) {
		Family family = familyRepository.findFamilyByFamilyIdAndIsDeleteIsFalse(familyId)
				.orElseThrow(() -> new ResourceNotFoundException("Family not found."));
		if (family.getPost() != null)
			return FamilyMapper.INSTANCE.toGetFamilyPostUpdateResponseDto(family.getPost());
		else
			return GetFamilyPostUpdateResponseDto.builder().familyId(family.getFamilyId())
					.familyName(family.getFamilyName()).build();
	}

	@Override
	public void addNewFamilyCondition(AddNewFamilyConditionRequestDto requestDto) {
		Optional<FamilyCondition> familyConditionCheck = familyConditionRepository
				.findFamilyConditionByAgeFromAndAgeTo(requestDto.getAgeFrom(), requestDto.getAgeTo());
		if (familyConditionCheck.isPresent()) {
			throw new ResourceExistedException("Family condition is existed!");
		}

		FamilyCondition familyCondition = FamilyMapper.INSTANCE.toFamilyConditionEntity(requestDto);

		familyConditionRepository.save(familyCondition);
	}

	@Override
	public void addNewFamily(AddNewFamilyRequestDto requestDto) {
		Optional<Family> familyCheck = familyRepository.findFamilyByFamilyName(requestDto.getFamilyName());
		if (familyCheck.isPresent()) {
			throw new ResourceExistedException("Family is existed!");
		}
		Mother mother = motherRepository.findMotherByMotherIdAndIsDeleteIsFalse(requestDto.getMotherId())
				.orElseThrow(() -> new ResourceNotFoundException("Mother not found!"));
		if (mother.getFamily() != null) {
			throw new ResourceExistedException("Mother was in the family!");
		}
		Family family = FamilyMapper.INSTANCE.toFamilyEntity(requestDto);
		family.setMother(mother);
		mother.setFamily(family);
		Family familySaved = familyRepository.save(family);

		MotherFamilyHistory motherFamilyHistory = MotherFamilyHistory.builder().mother(mother).family(familySaved)
				.startDate(requestDto.getDateOfFormation()).build();
		motherFamilyHistoryRepository.save(motherFamilyHistory);
	}

	@Override
	public FamilyPost addNewFamilyPost(Integer familyId, AddNewFamilyPostRequestDto requestDto) {
		Family family = familyRepository.findFamilyByFamilyIdAndIsDeleteIsFalse(familyId)
				.orElseThrow(() -> new ResourceNotFoundException("Family not found."));

		if (family.getPost() != null) {
			FamilyPost post = family.getPost();
			FamilyMapper.INSTANCE.updateFamilyPostEntity(post, requestDto);
			if (requestDto.getImages().size() > 0) {
				List<FamilyPostImage> familyImages = post.getImages();
				for (AddNewFamilyPostImageRequestDto image : requestDto.getImages()) {
					if (image.getImageCategory() == 1) {
						FamilyPostImage familyPostImage = familyImages.stream()
								.filter(img -> img.getImageCategory() == 1).findFirst().get();
						familyPostImage.setImageFileName(image.getImageFileName());
						familyPostImage.setImageFilePath(image.getImageFilePath());
					}
					if (image.getImageCategory() == 2) {
						FamilyPostImage familyPostImage = familyImages.stream()
								.filter(img -> img.getImageCategory() == 2).findFirst().get();
						familyPostImage.setImageFileName(image.getImageFileName());
						familyPostImage.setImageFilePath(image.getImageFilePath());
					}
					if (image.getImageCategory() == 3) {
						FamilyPostImage familyPostImage = FamilyPostImage.builder().imageCategory(3)
								.imageFileName(image.getImageFileName()).imageFilePath(image.getImageFilePath())
								.familyPost(post).build();
						familyImages.add(familyPostImageRepository.save(familyPostImage));
					}
				}
			}
			return familyPostRepository.save(post);
		} else {
			FamilyPost familyPost = FamilyMapper.INSTANCE.toFamilyPostEntity(requestDto);
			familyPost.setActive(true);
			familyPost.setFamily(family);
			familyPost.prePersist();
			return familyPostRepository.save(familyPost);
		}
	}

	@Override
	public List<GetFamilyPostResponseDto> fetchFamilyPost() {
		List<Family> families = familyRepository.getFamilyPosts();
		List<GetFamilyPostResponseDto> responseDtoList = families.stream().map(family -> {
			GetFamilyPostResponseDto familyPost = FamilyMapper.INSTANCE.toGetFamilyPostResponseDto(family);
			familyPost.setTitle(family.getPost().getTitle());
			familyPost.setSummary(family.getPost().getSummary());

			List<Object[]> familyDonationInfoObjects = familyRepository.getFamilyDonationInfo(family.getFamilyId());
			familyPost.setDonationCount((Long) familyDonationInfoObjects.get(0)[0]);
			familyPost.setTotalDonationAmount(
					familyDonationInfoObjects.get(0)[1] == null ? null : (Double) familyDonationInfoObjects.get(0)[1]);

			String imagePath = family.getPost().getImages().stream().filter(image -> image.getImageCategory() == 1)
					.findFirst().get().getImageFilePath();
			familyPost.setImageUrl(fileService.generateFileUrl(imagePath));
			return familyPost;
		}).collect(Collectors.toList());
		return responseDtoList;
	}

	@Override
	public GetFamilyPostDetailResponseDto getFamilyPostDetail(Integer id) {
		Family family = familyRepository.findFamilyByFamilyIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Family not found."));

		GetFamilyPostDetailResponseDto familyPost = GetFamilyPostDetailResponseDto.builder()
				.familyId(family.getFamilyId()).familyName(family.getFamilyName()).title(family.getPost().getTitle())
				.summary(family.getPost().getSummary()).content(family.getPost().getContent()).build();

		List<Object[]> familyDonationInfoObjects = familyRepository.getFamilyDonationInfo(family.getFamilyId());
		familyPost.setDonationCount((Long) familyDonationInfoObjects.get(0)[0]);
		familyPost.setTotalDonationAmount(
				familyDonationInfoObjects.get(0)[1] == null ? null : (Double) familyDonationInfoObjects.get(0)[1]);
		familyPost.setNoOfChildren(family.getChildren().stream().filter(c -> !c.isDelete()).count());
		familyPost.setRangeAge(family.getCondition().getAgeFrom() + " ~ " + family.getCondition().getAgeTo());
		family.getPost().getImages().forEach(image -> {
			String imageUrl = fileService.generateFileUrl(image.getImageFilePath());
			switch (image.getImageCategory()) {
			case 1:
				familyPost.setMainImageUrl(imageUrl);
				break;
			case 2:
				familyPost.setBannerImageUrl(imageUrl);
				break;
			case 3:
				if (familyPost.getSlideImage() == null) {
					familyPost.setSlideImage(new ArrayList<>());
				}
				familyPost.getSlideImage().add(imageUrl);
				break;
			}
		});
		return familyPost;
	}

	@Override
	public List<GetChildrenFamilyHistoryResponseDto> getChildrenFamilyHistory(Integer id) {
		Children children = childrenRepository.findByChildrenIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Children not found."));
		List<ChildrenFamilyHistory> childrenFamilyHistories = childrenFamilyHistoryRepository.getByChildren(children);
		List<GetChildrenFamilyHistoryResponseDto> responseDtoList = new ArrayList<>();
		childrenFamilyHistories.forEach(childrenFamilyHistory -> {
			Date startDate = childrenFamilyHistory.getStartDate();
			Date endDate = childrenFamilyHistory.getEndDate();
			List<MotherFamilyHistory> motherFamilyHistories;
			if (endDate == null) {
				motherFamilyHistories = motherFamilyHistoryRepository
						.getByFamilyAndStartDate(childrenFamilyHistory.getFamily(), startDate);
			} else {
				motherFamilyHistories = motherFamilyHistoryRepository
						.getByFamilyAndDateRange(childrenFamilyHistory.getFamily(), startDate, endDate);
			}
			motherFamilyHistories.forEach(motherFamilyHistory -> {
				GetChildrenFamilyHistoryResponseDto responseDto = GetChildrenFamilyHistoryResponseDto.builder()
						.startDate(startDate).endDate(endDate).familyId(childrenFamilyHistory.getFamily().getFamilyId())
						.familyName(childrenFamilyHistory.getFamily().getFamilyName())
						.motherId(motherFamilyHistory.getMother().getMotherId())
						.motherName(motherFamilyHistory.getMother().getMotherName())
						.childrenFamilyHistoryId(childrenFamilyHistory.getChildrenFamilyHistoryId()).build();
				if (motherFamilyHistory.getStartDate().before(startDate)) {
					responseDto.setMotherStartDate(startDate);
				} else {
					responseDto.setMotherStartDate(motherFamilyHistory.getStartDate());
				}

				if (endDate != null && motherFamilyHistory.getEndDate() != null) {
					if (motherFamilyHistory.getEndDate().after(endDate)) {
						responseDto.setMotherEndDate(endDate);
					} else {
						responseDto.setMotherEndDate(motherFamilyHistory.getEndDate());
					}
				} else if (endDate == null)
					responseDto.setMotherEndDate(motherFamilyHistory.getEndDate());
				else {
					responseDto.setMotherEndDate(endDate);
				}
				responseDtoList.add(responseDto);
			});
		});
		return responseDtoList;
	}

	@Override
	@Transactional
	public void updateMotherForFamily(Integer familyId, Integer motherId) {
		Mother mother = motherRepository.findMotherByMotherIdAndIsDeleteIsFalse(motherId)
				.orElseThrow(() -> new ResourceNotFoundException("Mother not found."));
		Family family = familyRepository.findFamilyByFamilyIdAndIsDeleteIsFalse(familyId)
				.orElseThrow(() -> new ResourceNotFoundException("Family not found"));

		if (family.getMother() != null) {
			Mother oldMother = family.getMother();
			oldMother.setFamily(null);
			motherRepository.save(oldMother);

			// set end date for current mother
			MotherFamilyHistory oldHistory = motherFamilyHistoryRepository
					.findNewestByMotherAndFamily(oldMother, family).orElseThrow(ResourceNotFoundException::new);
			Date currentDate = new Date(System.currentTimeMillis());

			LocalDate localDate = currentDate.toLocalDate();
			LocalDate yesterday = localDate.minusDays(1);
			Date yesterdaySqlDate = Date.valueOf(yesterday);
			oldHistory.setEndDate(yesterdaySqlDate);
			motherFamilyHistoryRepository.save(oldHistory);
		}
		mother.setFamily(family);
		motherRepository.save(mother);

		// set new family history
		MotherFamilyHistory motherFamilyHistory = MotherFamilyHistory.builder().mother(mother).family(family)
				.startDate(new Date(System.currentTimeMillis())).build();
		motherFamilyHistoryRepository.save(motherFamilyHistory);
	}

	@Override
	public void delete(List<Integer> ids) {
		familyRepository.softDeleteByIds(ids);
	}

	@Override
	@Transactional
	public void deleteChildren(Integer familyId, List<Integer> ids) {
		Family family = familyRepository.findFamilyByFamilyIdAndIsDeleteIsFalse(familyId)
				.orElseThrow(() -> new ResourceNotFoundException("Family not found"));
		for (Integer childrenId : ids) {
			Children children = childrenRepository.findByChildrenIdAndIsDeleteIsFalse(childrenId)
					.orElseThrow(() -> new ResourceNotFoundException("Children not found."));
			ChildrenFamilyHistory oldHistory = childrenFamilyHistoryRepository
					.findNewestByChildrenAndFamily(children, family).orElseThrow(null);
			oldHistory.setEndDate(new Date(System.currentTimeMillis()));
			childrenFamilyHistoryRepository.save(oldHistory);
			children.setFamily(null);
			childrenRepository.save(children);
		}
	}
}
