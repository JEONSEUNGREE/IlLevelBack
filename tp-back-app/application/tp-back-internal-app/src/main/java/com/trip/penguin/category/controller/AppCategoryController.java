package com.trip.penguin.category.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.trip.penguin.category.dto.CategoryDepthDTO;
import com.trip.penguin.category.service.AppCategoryService;
import com.trip.penguin.category.view.AppCategoryView;
import com.trip.penguin.response.JsonResponse;

@RestController
@RequestMapping(value = "/category")
public class AppCategoryController {

	private final AppCategoryService appCategoryService;

	@Autowired
	public AppCategoryController(AppCategoryService appCategoryService) {
		this.appCategoryService = appCategoryService;
	}

	@PostMapping("/create")
	public JsonResponse<CategoryDepthDTO> appCategoryCreate(@RequestBody AppCategoryView appCategoryView) {

		CategoryDepthDTO categoryDepthDTO = appCategoryService.appCategoryCreate(appCategoryView);

		return JsonResponse.success(categoryDepthDTO);
	}

	@GetMapping("/untilDepth/map")
	public JsonResponse<Map<Integer, List<CategoryDepthDTO>>> getUntilCurrentDepthCateKeyMap(
		@RequestParam(value = "ancestors") Long[] ancestors, @RequestParam(value = "depth") Integer depth) {
		return JsonResponse.success(appCategoryService.getUntilCurrentDepthCateKeyMap(ancestors, depth));
	}

	@GetMapping("/untilDepth/list")
	public JsonResponse<List<CategoryDepthDTO>> getUntilCurrentDepthCateList(
		@RequestParam(value = "ancestors") Long[] ancestors, @RequestParam(value = "depth") Integer depth) {
		return JsonResponse.success(appCategoryService.getUntilCurrentDepthCateList(ancestors, depth));
	}

	@GetMapping("/depthList/current/child")
	public JsonResponse<List<CategoryDepthDTO>> getCurrentDepthChildCateList(
		@RequestParam(value = "depth") Integer depth, @RequestParam(value = "id") Long id) {
		return JsonResponse.success(appCategoryService.getCurrentDepthChildCateList(depth, id));
	}

	@GetMapping("/depthList/flat")
	public JsonResponse<CategoryDepthDTO> getUntilCurrentDepthFlatCate(
		@RequestParam(value = "ancestor") Long ancestor,
		@RequestParam(value = "id") Long id,
		@RequestParam(value = "depth") Integer depth) {
		return JsonResponse.success(appCategoryService.getUntilCurrentDepthFlatCate(ancestor, depth, id));
	}

	@GetMapping("/getAllCate")
	public JsonResponse<List<CategoryDepthDTO>> getAllCategory() {
		return JsonResponse.success(appCategoryService.getAllCategory());
	}
}
