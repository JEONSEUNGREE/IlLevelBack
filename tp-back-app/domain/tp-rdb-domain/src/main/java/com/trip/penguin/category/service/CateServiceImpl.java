package com.trip.penguin.category.service;

import com.trip.penguin.category.domain.CategoryMS;
import com.trip.penguin.category.dto.CategoryDepthListDTO;
import com.trip.penguin.category.repository.CateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@RequiredArgsConstructor
public class CateServiceImpl implements CateService {

    private final CateRepository cateRepository;

    @Override
    public CategoryMS createCategory(CategoryMS categoryMS) {
        // 처음 저장시 ID값이 없기 때문에 한번더 저장 한다.
        CategoryMS savedCate = cateRepository.save(categoryMS);
        // 최상위 카테고리 등록
        savedCate.setAncestor();
        return cateRepository.save(savedCate);
    }

    @Override
    public Map<Integer, List<CategoryDepthListDTO>> getUntilCurrentDepthCategory(Collection<Long> ancestors, Integer depth) {

        Stack<CategoryMS> allByAncestorAndDepthLessThanEqual = cateRepository.findAllByAncestorInAndDepthLessThanEqual(ancestors, depth);

        Map<Integer, List<CategoryDepthListDTO>> resultCateList = new HashMap<>();

        for (CategoryMS categoryMS : allByAncestorAndDepthLessThanEqual) {
            Integer key = categoryMS.getDepth();
            CategoryDepthListDTO value = categoryMS.changeDTO();

            if (resultCateList.get(key) != null) {
                resultCateList.get(key).add(value);
            } else {
                resultCateList.put(key, new ArrayList<>(Collections.singletonList(value)));
            }
        }
        return resultCateList;
    }

    @Override
    public List<CategoryDepthListDTO> getCurrentParentIdCate(Long ancestor, Integer depth, Long parentId) {

        return cateRepository.findAllByAncestorAndDepthEqualsAndParentCateId(ancestor, depth, parentId).stream().map(
                CategoryMS::changeDTO).toList();
    }

    @Override
    public CategoryDepthListDTO getUntilCurrentFlatOneCategory(Long ancestor, Integer depth, Long cateId) {
        List<CategoryDepthListDTO> cateList = cateRepository.findAllByAncestorAndDepthLessThanEqual(ancestor, depth)
                .stream().map(CategoryMS::changeDTO).toList();
        CategoryDepthListDTO categoryDepthListDTO = cateList.stream().filter(c -> cateId.equals(c.getId())).findAny().orElseThrow();
        return categoryDepthListDTO.makeOneFlatCateChild(null, cateList);
    }

    @Override
    public List<CategoryDepthListDTO> getAllCategory() {
        List<CategoryDepthListDTO> cateList = cateRepository.findAll().stream().map(CategoryMS::changeDTO).toList();
//
//        cateList.forEach(c -> {
//            cateList.forEach(cc -> cc.ad);
//        }).findAny().get().addChildCate(c));
//
//        return cateList;
    }
}
