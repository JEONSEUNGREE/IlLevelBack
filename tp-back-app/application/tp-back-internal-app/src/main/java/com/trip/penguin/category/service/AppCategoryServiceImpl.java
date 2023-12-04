package com.trip.penguin.category.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.penguin.category.domain.CategoryMS;
import com.trip.penguin.category.dto.CategoryDepthDTO;
import com.trip.penguin.category.repository.CateRepository;
import com.trip.penguin.category.view.AppCategoryView;
import com.trip.penguin.exception.CannotFoundCateInfoException;

@Service
public class AppCategoryServiceImpl implements AppCategoryService {

	private CateRepository cateRepository;

	private CateService cateService;

	@Autowired
	public AppCategoryServiceImpl(CateRepository cateRepository, CateService cateService) {
		this.cateRepository = cateRepository;
		this.cateService = cateService;
	}

	@Override
	public CategoryDepthDTO appCategoryCreate(AppCategoryView appCategoryView) {

		CategoryDepthDTO result = null;

		// 최상위가 아닌 카테고리 생성 시
		if (appCategoryView.getDepth() != 0) {
			CategoryMS findCate = cateRepository.findById(appCategoryView.getParentId())
				.orElseThrow(CannotFoundCateInfoException::new);

			result = cateService.createCategory(
				new CategoryMS(appCategoryView.getDepth(), findCate, appCategoryView.getCategoryName())).changeDTO();
		} else {
			// 최상위 카테고리 생성 시
			result = cateService.createCategory(
				new CategoryMS(0, null, appCategoryView.getCategoryName())).changeDTO();
		}

		return result;
	}

	@Override
	public List<CategoryDepthDTO> getAllCategory() {
		return cateService.getAllCateList();
	}

	@Override
	public Map<Integer, List<CategoryDepthDTO>> getUntilCurrentDepthCateKeyMap(Long[] ancestors, Integer depth) {
		return cateService.getUntilCurrentDepthCateKeyMap(List.of(ancestors), depth);
	}

	@Override
	public List<CategoryDepthDTO> getUntilCurrentDepthCateList(Long[] ancestors, Integer depth) {
		return cateService.getUntilCurrentDepthCateList(List.of(ancestors), depth);
	}

	@Override
	public List<CategoryDepthDTO> getCurrentDepthChildCateList(Integer depth, Long id) {
		return cateService.getCurrentDepthChildCateList(depth, id);
	}

	@Override
	public CategoryDepthDTO getUntilCurrentDepthFlatCate(Long ancestors, Integer depth, Long id) {
		return cateService.getUntilCurrentDepthFlatCate(ancestors, depth, id);
	}
}
