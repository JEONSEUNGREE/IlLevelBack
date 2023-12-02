package com.trip.penguin.category.service;

import com.trip.penguin.category.domain.CategoryMS;
import com.trip.penguin.category.dto.CategoryDepthListDTO;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public interface CateService {

    CategoryMS createCategory(CategoryMS categoryMS);

    Map<Integer, List<CategoryDepthListDTO>> getUntilCurrentDepthCategory(Collection<Long> ancestors, Integer depth);

    List<CategoryDepthListDTO> getCurrentParentIdCate(Long ancestor, Integer depth, Long parentId);

    CategoryDepthListDTO getUntilCurrentFlatOneCategory(Long ancestor, Integer depth, Long cateId);

    List<CategoryDepthListDTO> getAllCategory();

}
