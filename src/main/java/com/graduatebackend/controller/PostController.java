package com.graduatebackend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.graduatebackend.dto.ResponseDto;
import com.graduatebackend.dto.articles.AddNewArticleCategoryRequestDto;
import com.graduatebackend.dto.articles.AddNewArticleRequestDto;
import com.graduatebackend.dto.articles.GetArticlePostResponseDto;
import com.graduatebackend.dto.articles.GetArticleResponseDto;
import com.graduatebackend.dto.articles.GetCategoryDetailResponseDto;
import com.graduatebackend.service.ArticleService;
import com.graduatebackend.service.FamilyService;
import com.graduatebackend.utils.PaginationUtils;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin
public class PostController {
	private final FamilyService familyService;
	private final ArticleService articleService;

	@GetMapping(value = "/families/posts")
	public ResponseEntity<?> fetchDonationPrograms() {
		return ResponseEntity.ok(ResponseDto.ok(familyService.fetchFamilyPost()));
	}

	@GetMapping(value = "/families/{id}/posts")
	public ResponseEntity<?> getFamilyPostDetail(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(ResponseDto.ok(familyService.getFamilyPostDetail(id)));
	}

	@PostMapping(value = "/admin/article-categories")
	public ResponseEntity<?> addNewArticleCategory(@RequestBody AddNewArticleCategoryRequestDto requestDto) {
		articleService.addNewCategory(requestDto);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "/article-categories")
	public ResponseEntity<?> getAllArticleCategories() {
		return ResponseEntity.ok(ResponseDto.ok(articleService.getAllCategories()));
	}

	@GetMapping(value = "/articles")
	public ResponseEntity<?> fetchArticlePost(@RequestParam(required = false) final Map<String, String> requestParams) {
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		String keyword = requestParams.get("keyword");
		Integer categoryId = requestParams.get("categoryId") == null ? null
				: Integer.valueOf(requestParams.get("categoryId"));
		String fromDate = requestParams.get("fromDate");
		String toDate = requestParams.get("toDate");

		Page<GetArticlePostResponseDto> page = articleService.fetchArticlePosts(pageRequest, keyword, categoryId,
				fromDate, toDate);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(page)));
	}

	@GetMapping(value = "/articles/top")
	public ResponseEntity<?> fetchArticlePost(@RequestParam("top") Integer top) {
		return ResponseEntity.ok(ResponseDto.ok(articleService.getTopArticles(top)));
	}

	@GetMapping(value = "/article-categories/{id}")
	public ResponseEntity<?> getCategoryPost(@RequestParam(required = false) final Map<String, String> requestParams,
			@PathVariable("id") Integer id) {
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		return ResponseEntity.ok(ResponseDto.ok(articleService.getCategoryPost(id, pageRequest)));
	}

	@GetMapping(value = "/articles/{id}")
	public ResponseEntity<?> getArticlePostDetail(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(ResponseDto.ok(articleService.getArticlePostDetail(id)));
	}

	@GetMapping(value = "/articles/{id}/related")
	public ResponseEntity<?> getRelatedArticlePosts(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(ResponseDto.ok(articleService.getRelatedArticlePosts(id)));
	}

	@PostMapping(value = "/admin/articles")
	public ResponseEntity<?> addNewArticle(@RequestBody AddNewArticleRequestDto requestDto) {
		articleService.addNewArticle(requestDto);
		return ResponseEntity.noContent().build();
	}

	@GetMapping(value = "/admin/article-categories")
	public ResponseEntity<?> fetchCategories(@RequestParam(required = false) final Map<String, String> requestParams) {
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		String keyword = requestParams.get("keyword");
		Page<GetCategoryDetailResponseDto> page = articleService.fetchCategories(pageRequest, keyword);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(page)));
	}

	@GetMapping(value = "/admin/articles")
	public ResponseEntity<?> fetchArticles(@RequestParam(required = false) final Map<String, String> requestParams) {
		PageRequest pageRequest = PaginationUtils.generatePageRequest(requestParams);
		String keyword = requestParams.get("keyword");
		Integer categoryId = requestParams.get("categoryId") == null ? null
				: Integer.valueOf(requestParams.get("categoryId"));
		String fromDate = requestParams.get("fromDate");
		String toDate = requestParams.get("toDate");

		Page<GetArticleResponseDto> page = articleService.fetchArticles(pageRequest, keyword, categoryId, fromDate,
				toDate);
		return ResponseEntity.ok(ResponseDto.ok(PaginationUtils.buildPageRes(page)));
	}

	@DeleteMapping("/admin/article-categories")
	public ResponseEntity<?> deleteCategories(@RequestParam("ids") List<Integer> ids) {
		articleService.deleteCategories(ids);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping(value = "/admin/articles")
	public ResponseEntity<?> deleteArticles(@RequestParam("ids") List<Integer> ids) {
		articleService.deleteArticles(ids);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(value = "/admin/articles/hidden")
	public ResponseEntity<?> hideArticles(@RequestParam("ids") List<Integer> ids) {
		articleService.hideArticles(ids);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping(value = "/admin/articles/active")
	public ResponseEntity<?> activeArticles(@RequestParam("ids") List<Integer> ids) {
		articleService.activeArticles(ids);
		return ResponseEntity.noContent().build();
	}
}
