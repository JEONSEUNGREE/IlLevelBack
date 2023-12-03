package com.trip.penguin.category.service;

import java.util.List;
import java.util.Map;

import com.trip.penguin.category.dto.CategoryDepthDTO;
import com.trip.penguin.category.view.AppCategoryView;

public interface AppCategoryService {

	CategoryDepthDTO appCategoryCreate(AppCategoryView appCategoryView);

	List<CategoryDepthDTO> getAllCategory();

	Map<Integer, List<CategoryDepthDTO>> getUntilCurrentDepthCateKeyMap(Long[] ancestors, Integer depth);

	List<CategoryDepthDTO> getUntilCurrentDepthCateList(Long[] ancestors, Integer depth);

	List<CategoryDepthDTO> getCurrentDepthChildCateList(Integer depth, Long id);

	CategoryDepthDTO getUntilCurrentDepthFlatCate(Long ancestors, Integer depth, Long id);

}
