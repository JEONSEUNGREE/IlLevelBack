package com.trip.penguin.category.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.trip.penguin.category.domain.CategoryMS;
import com.trip.penguin.category.dto.CategoryDepthDTO;

public interface CateService {

	CategoryMS createCategory(CategoryMS categoryMS);

	Map<Integer, List<CategoryDepthDTO>> getUntilCurrentDepthCateKeyMap(Collection<Long> ancestors,
		Integer depth);

	List<CategoryDepthDTO> getUntilCurrentDepthCateList(Collection<Long> ancestors, Integer depth);

	List<CategoryDepthDTO> getCurrentDepthChildCateList(Integer depth, Long currentId);

	CategoryDepthDTO getUntilCurrentDepthFlatCate(Long ancestor, Integer depth, Long cateId);

	List<CategoryDepthDTO> getAllCateList();

	CategoryMS getCategoryDtoByCategoryNm(String cateName);

}
