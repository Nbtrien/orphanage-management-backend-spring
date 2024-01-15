package com.graduatebackend.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.graduatebackend.dto.articles.AddNewArticleCategoryRequestDto;
import com.graduatebackend.dto.articles.AddNewArticleRequestDto;
import com.graduatebackend.dto.articles.GetArticleCategoryResponseDto;
import com.graduatebackend.dto.articles.GetArticlePostDetailResponseDto;
import com.graduatebackend.dto.articles.GetArticlePostResponseDto;
import com.graduatebackend.dto.articles.GetArticleResponseDto;
import com.graduatebackend.dto.articles.GetCategoryDetailResponseDto;
import com.graduatebackend.dto.articles.GetCategoryPostResponseDto;
import com.graduatebackend.dto.articles.GetRelatedArticleResponseDto;

public interface ArticleService {
	/**
	 * add new category
	 *
	 * @param requestDto
	 */
	void addNewCategory(AddNewArticleCategoryRequestDto requestDto);

	/**
	 * add new article
	 *
	 * @param requestDto
	 */
	void addNewArticle(AddNewArticleRequestDto requestDto);

	/**
	 * get all categories
	 *
	 * @return
	 */
	List<GetArticleCategoryResponseDto> getAllCategories();

	/**
	 * fetch categories
	 *
	 * @param pageRequest
	 * @return
	 */
	Page<GetCategoryDetailResponseDto> fetchCategories(PageRequest pageRequest, String keyword);

	/**
	 * fetch articles
	 *
	 * @param pageRequest
	 * @param keyword
	 * @param categoryId
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	Page<GetArticleResponseDto> fetchArticles(PageRequest pageRequest, String keyword, Integer categoryId,
			String fromDate, String toDate);

	/**
	 * fetch articles
	 *
	 * @param pageRequest
	 * @param keyword
	 * @param categoryId
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	Page<GetArticlePostResponseDto> fetchArticlePosts(PageRequest pageRequest, String keyword, Integer categoryId,
			String fromDate, String toDate);

	/**
	 * get top articles post
	 *
	 * @param top
	 * @return
	 */
	List<GetArticlePostResponseDto> getTopArticles(Integer top);

	/**
	 * get category and posts
	 *
	 * @param categoryId
	 * @param pageRequest
	 * @return
	 */
	GetCategoryPostResponseDto getCategoryPost(Integer categoryId, PageRequest pageRequest);

	/**
	 * get article post detail
	 *
	 * @param id
	 * @return
	 */
	GetArticlePostDetailResponseDto getArticlePostDetail(Integer id);

	/**
	 * get related posts
	 *
	 * @param id
	 * @return
	 */
	List<GetRelatedArticleResponseDto> getRelatedArticlePosts(Integer id);

	/**
	 * delete categories
	 *
	 * @param ids
	 */
	void deleteCategories(List<Integer> ids);

	/**
	 * delete articles
	 *
	 * @param ids
	 */
	void deleteArticles(List<Integer> ids);

	/**
	 * hide articles
	 *
	 * @param ids
	 */
	void hideArticles(List<Integer> ids);

	/**
	 * active by ids
	 *
	 * @param ids
	 */
	void activeArticles(List<Integer> ids);
}
