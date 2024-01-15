package com.graduatebackend.service.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.graduatebackend.dto.articles.AddNewArticleCategoryRequestDto;
import com.graduatebackend.dto.articles.AddNewArticleRequestDto;
import com.graduatebackend.dto.articles.GetArticleCategoryResponseDto;
import com.graduatebackend.dto.articles.GetArticlePostDetailResponseDto;
import com.graduatebackend.dto.articles.GetArticlePostResponseDto;
import com.graduatebackend.dto.articles.GetArticleResponseDto;
import com.graduatebackend.dto.articles.GetCategoryDetailResponseDto;
import com.graduatebackend.dto.articles.GetCategoryPostResponseDto;
import com.graduatebackend.dto.articles.GetRelatedArticleResponseDto;
import com.graduatebackend.entity.Article;
import com.graduatebackend.entity.ArticleCategory;
import com.graduatebackend.entity.ArticleDetail;
import com.graduatebackend.exception.ResourceNotFoundException;
import com.graduatebackend.mapper.ArticleMapper;
import com.graduatebackend.repository.ArticleCategoryRepository;
import com.graduatebackend.repository.ArticleRepository;
import com.graduatebackend.service.ArticleService;
import com.graduatebackend.service.FileService;
import com.graduatebackend.utils.PaginationUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {
	private final ArticleCategoryRepository categoryRepository;
	private final ArticleRepository articleRepository;
	private final FileService fileService;

	@Override
	public void addNewCategory(AddNewArticleCategoryRequestDto requestDto) {
		ArticleCategory articleCategory = ArticleMapper.INSTANCE.toArticleCategoryEntity(requestDto);
		categoryRepository.save(articleCategory);
	}

	@Override
	@Transactional
	public void addNewArticle(AddNewArticleRequestDto requestDto) {
		ArticleCategory category = categoryRepository
				.findByArticleCategoryIdAndIsDeleteIsFalse(requestDto.getCategoryId())
				.orElseThrow(() -> new ResourceNotFoundException("Category not found."));
		Article article = ArticleMapper.INSTANCE.toArticleEntity(requestDto);
		article.setCategory(category);
		int i = 1;
		for (ArticleDetail articleDetail : article.getArticleDetails()) {
			articleDetail.setDisplayOrder(i);
			i++;
		}
		article.prePersist();
		articleRepository.save(article);
	}

	@Override
	public List<GetArticleCategoryResponseDto> getAllCategories() {
		List<ArticleCategory> categories = categoryRepository.findAllByIsDeleteIsFalse();
		return categories.stream().map(ArticleMapper.INSTANCE::toGetArticleCategoryResponseDto)
				.collect(Collectors.toList());
	}

	@Override
	public Page<GetCategoryDetailResponseDto> fetchCategories(PageRequest pageRequest, String keyword) {
		Page<ArticleCategory> categories = categoryRepository.findAll(pageRequest, keyword);
		return categories.map(category -> {
			GetCategoryDetailResponseDto responseDto = ArticleMapper.INSTANCE.toGetCategoryDetailResponseDto(category);
			long articleCount = category.getArticles().stream().filter(article -> !article.isDelete()).count();
			responseDto.setNoOfArticles(articleCount);
			return responseDto;
		});
	}

	@Override
	public Page<GetArticleResponseDto> fetchArticles(PageRequest pageRequest, String keyword, Integer categoryId,
			String fromDate, String toDate) {
		Date minDate = fromDate == null ? null : Date.valueOf(fromDate);
		Date maxDate = toDate == null ? null : Date.valueOf(toDate);
		Page<Article> articles = articleRepository.findAll(pageRequest, keyword, categoryId, minDate, maxDate);
		Page<GetArticleResponseDto> articleResponseDtoPage = articles.map(article -> {
			GetArticleResponseDto responseDto = ArticleMapper.INSTANCE.toGetArticleResponseDto(article);
			if (article.getPublicationEndDateTime() == null
					|| article.getPublicationEndDateTime().after(new Timestamp(System.currentTimeMillis())))
				responseDto.setActive(true);
			else
				responseDto.setActive(false);
			return responseDto;
		});
		return articleResponseDtoPage;
	}

	@Override
	public Page<GetArticlePostResponseDto> fetchArticlePosts(PageRequest pageRequest, String keyword,
			Integer categoryId, String fromDate, String toDate) {
		Date minDate = fromDate == null ? null : Date.valueOf(fromDate);
		Date maxDate = toDate == null ? null : Date.valueOf(toDate);
		Page<Article> articles = articleRepository.findAllPost(pageRequest, keyword, categoryId, minDate, maxDate);
		Page<GetArticlePostResponseDto> responseDtoPage = articles.map(article -> {
			GetArticlePostResponseDto responseDto = ArticleMapper.INSTANCE.toGetArticlePostResponseDto(article);
			List<String> images = new ArrayList<>();
			images.add(fileService.generateFileUrl(article.getImage().getImageFilePath()));
			article.getArticleDetails().forEach(articleDetail -> images
					.add(fileService.generateFileUrl(articleDetail.getImage().getImageFilePath())));
			responseDto.setImages(images);
			return responseDto;
		});
		return responseDtoPage;
	}

	@Override
	public List<GetArticlePostResponseDto> getTopArticles(Integer top) {
		List<Article> articles = articleRepository.getTopArticles(Pageable.ofSize(top));
		return articles.stream().map(article -> {
			GetArticlePostResponseDto responseDto = ArticleMapper.INSTANCE.toGetArticlePostResponseDto(article);
			List<String> images = new ArrayList<>();
			images.add(fileService.generateFileUrl(article.getImage().getImageFilePath()));
			article.getArticleDetails().forEach(articleDetail -> images
					.add(fileService.generateFileUrl(articleDetail.getImage().getImageFilePath())));
			responseDto.setImages(images);
			return responseDto;
		}).collect(Collectors.toList());
	}

	@Override
	public GetCategoryPostResponseDto getCategoryPost(Integer categoryId, PageRequest pageRequest) {
		ArticleCategory category = categoryRepository.findByArticleCategoryIdAndIsDeleteIsFalse(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category not found."));
		GetCategoryPostResponseDto categoryPostResponseDto = ArticleMapper.INSTANCE
				.toGetCategoryPostResponseDto(category);
		categoryPostResponseDto.setImageFilePath(fileService.generateFileUrl(category.getImageFilePath()));

		Page<Article> articles = articleRepository.findAllPost(pageRequest, null, categoryId, null, null);
		Page<GetArticlePostResponseDto> articlePostResponseDtoPage = articles.map(article -> {
			GetArticlePostResponseDto responseDto = ArticleMapper.INSTANCE.toGetArticlePostResponseDto(article);
			List<String> images = new ArrayList<>();
			images.add(fileService.generateFileUrl(article.getImage().getImageFilePath()));
			article.getArticleDetails().forEach(articleDetail -> images
					.add(fileService.generateFileUrl(articleDetail.getImage().getImageFilePath())));
			responseDto.setImages(images);
			return responseDto;
		});

		categoryPostResponseDto.setArticlePosts(PaginationUtils.buildPageRes(articlePostResponseDtoPage));
		return categoryPostResponseDto;
	}

	@Override
	public GetArticlePostDetailResponseDto getArticlePostDetail(Integer id) {
		Article article = articleRepository.findByArticleIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Article not found."));
		GetArticlePostDetailResponseDto articlePostDetail = ArticleMapper.INSTANCE
				.toGetArticlePostDetailResponseDto(article);
		articlePostDetail.setImage(fileService.generateFileUrl(article.getImage().getImageFilePath()));
		articlePostDetail.setBannerImage(fileService.generateFileUrl(article.getBannerImage().getImageFilePath()));
		articlePostDetail.getArticleDetails().forEach(
				articleDetail -> articleDetail.setImage(fileService.generateFileUrl(articleDetail.getImage())));
		return articlePostDetail;
	}

	@Override
	public List<GetRelatedArticleResponseDto> getRelatedArticlePosts(Integer id) {
		Article article = articleRepository.findByArticleIdAndIsDeleteIsFalse(id)
				.orElseThrow(() -> new ResourceNotFoundException("Article not found."));
		List<Article> articles = articleRepository.getRelatedArticles(PageRequest.of(0, 8), article.getArticleId(),
				article.getCategory().getArticleCategoryId());
		return articles.stream().map(article1 -> {
			GetRelatedArticleResponseDto responseDto = ArticleMapper.INSTANCE.toGetRelatedArticleResponseDto(article1);
			responseDto.setImageUrl(fileService.generateFileUrl(article1.getImage().getImageFilePath()));
			return responseDto;
		}).collect(Collectors.toList());
	}

	@Override
	public void deleteCategories(List<Integer> ids) {
		categoryRepository.deleteByIds(ids);
	}

	@Override
	public void deleteArticles(List<Integer> ids) {
		articleRepository.deleteByIds(ids);
	}

	@Override
	public void hideArticles(List<Integer> ids) {
		articleRepository.hideByIds(ids);
	}

	@Override
	public void activeArticles(List<Integer> ids) {
		articleRepository.activeByIds(ids);
	}
}
