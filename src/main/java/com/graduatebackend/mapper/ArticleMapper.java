package com.graduatebackend.mapper;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.ReportingPolicy.IGNORE;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.graduatebackend.dto.articles.AddNewArticleCategoryRequestDto;
import com.graduatebackend.dto.articles.AddNewArticleRequestDto;
import com.graduatebackend.dto.articles.GetArticleCategoryResponseDto;
import com.graduatebackend.dto.articles.GetArticleDetailResponseDto;
import com.graduatebackend.dto.articles.GetArticlePostDetailResponseDto;
import com.graduatebackend.dto.articles.GetArticlePostResponseDto;
import com.graduatebackend.dto.articles.GetArticleResponseDto;
import com.graduatebackend.dto.articles.GetCategoryDetailResponseDto;
import com.graduatebackend.dto.articles.GetCategoryPostResponseDto;
import com.graduatebackend.dto.articles.GetRelatedArticleResponseDto;
import com.graduatebackend.entity.Article;
import com.graduatebackend.entity.ArticleCategory;
import com.graduatebackend.entity.ArticleDetail;

@Mapper(unmappedTargetPolicy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface ArticleMapper {
	ArticleMapper INSTANCE = Mappers.getMapper(ArticleMapper.class);

	/**
	 * Convert AddNewArticleCategoryRequestDto to ArticleCategory Entity
	 *
	 * @param requestDto
	 * @return
	 */
	ArticleCategory toArticleCategoryEntity(AddNewArticleCategoryRequestDto requestDto);

	/**
	 * Convert AddNewArticleRequestDto to Article Entity
	 *
	 * @param requestDto
	 * @return
	 */
	Article toArticleEntity(AddNewArticleRequestDto requestDto);

	/**
	 * Convert ArticleCategory Entity to GetArticleCategoryResponseDto
	 *
	 * @param category
	 * @return
	 */
	GetArticleCategoryResponseDto toGetArticleCategoryResponseDto(ArticleCategory category);

	/**
	 * Convert ArticleCategory Entity to GetCategoryDetailResponseDto
	 *
	 * @param category
	 * @return
	 */
	GetCategoryDetailResponseDto toGetCategoryDetailResponseDto(ArticleCategory category);

	/**
	 * Convert Article to GetArticleResponseDto Entity
	 *
	 * @param article
	 * @return
	 */
	@Mapping(target = "categoryId", source = "category.articleCategoryId")
	@Mapping(target = "categoryName", source = "category.categoryName")
	GetArticleResponseDto toGetArticleResponseDto(Article article);

	/**
	 * Convert Article to GetArticlePostResponseDto
	 *
	 * @param article
	 * @return
	 */
	@Mapping(target = "categoryId", source = "category.articleCategoryId")
	@Mapping(target = "categoryName", source = "category.categoryName")
	GetArticlePostResponseDto toGetArticlePostResponseDto(Article article);

	/**
	 * Convert ArticleCategory Entity to GetCategoryPostResponseDto
	 *
	 * @param category
	 * @return
	 */
	GetCategoryPostResponseDto toGetCategoryPostResponseDto(ArticleCategory category);

	/**
	 * Convert ArticleDetail Entity to GetArticleDetailResponseDto
	 *
	 * @param articleDetail
	 * @return
	 */
	@Mapping(target = "image", source = "image.imageFilePath")
	@Named("GetArticleDetailResponseDto")
	GetArticleDetailResponseDto toGetArticleDetailResponseDtoDto(ArticleDetail articleDetail);

	/**
	 * Convert ArticleDetail entity list to GetArticleDetailResponseDto list
	 *
	 * @param articleDetails
	 * @return
	 */
	@IterableMapping(qualifiedByName = "GetArticleDetailResponseDto")
	@Named("toGetArticleDetailResponseDtoList")
	List<GetArticleDetailResponseDto> toGetArticleDetailResponseDtoList(List<ArticleDetail> articleDetails);

	/**
	 * Convert Article Entity to GetArticlePostDetailResponseDto
	 *
	 * @param article
	 * @return
	 */
	@Mapping(target = "articleDetails", expression = "java(toGetArticleDetailResponseDtoList(article"
			+ ".getArticleDetails()))")
	@Mapping(target = "image", ignore = true)
	@Mapping(target = "bannerImage", ignore = true)
	@Mapping(target = "categoryId", source = "category.articleCategoryId")
	@Mapping(target = "categoryName", source = "category.categoryName")
	GetArticlePostDetailResponseDto toGetArticlePostDetailResponseDto(Article article);

	/**
	 * Convert Article Entity to GetRelatedArticleResponseDto
	 *
	 * @param article
	 * @return
	 */
	GetRelatedArticleResponseDto toGetRelatedArticleResponseDto(Article article);
}
