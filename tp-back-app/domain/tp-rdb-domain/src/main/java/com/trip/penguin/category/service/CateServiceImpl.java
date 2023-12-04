package com.trip.penguin.category.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.springframework.stereotype.Service;

import com.trip.penguin.category.domain.CategoryMS;
import com.trip.penguin.category.dto.CategoryDepthDTO;
import com.trip.penguin.category.repository.CateRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CateServiceImpl implements CateService {

	private final CateRepository cateRepository;

	/**
	 * 카테고리 생성
	 * @param categoryMS - 카테 고리 정보 ( 카테고리 뎁스, 부모 카테고리 ID, 카테고리 명)
	 * @return CategoryMS
	 */
	@Override
	public CategoryMS createCategory(CategoryMS categoryMS) {
		/* 처음 저장시 ID값이 없기 때문에 한번더 저장 한다. */
		CategoryMS savedCate = cateRepository.save(categoryMS);
		/* 최상위 카테고리 등록 */
		savedCate.setAncestor();
		return cateRepository.save(savedCate);
	}

	/**
	 * 특정 최상위 카테고리들을 특정 뎁스 까지, 단 뎁스 별로 분류해서 리턴
	 * @param ancestors - 최상위 카테고리 ID
	 * @param depth - 뎁스
	 * @return
	 */
	@Override
	public Map<Integer, List<CategoryDepthDTO>> getUntilCurrentDepthCateKeyMap(Collection<Long> ancestors,
		Integer depth) {

		/* 같은 최상위 카테고리에 해당되는 카테고리들 조회 */
		Stack<CategoryMS> allByAncestorAndDepthLessThanEqual = cateRepository.findAllByAncestorInAndDepthLessThanEqual(
			ancestors, depth);

		Map<Integer, List<CategoryDepthDTO>> resultCateList = new HashMap<>();

		/* key는 depth, value는 depth에 해당되는 카테고리 */
		for (CategoryMS categoryMS : allByAncestorAndDepthLessThanEqual) {
			Integer key = categoryMS.getDepth();
			CategoryDepthDTO value = categoryMS.changeDTO();

			if (resultCateList.get(key) != null) {
				resultCateList.get(key).add(value);
			} else {
				resultCateList.put(key, new ArrayList<>(Collections.singletonList(value)));
			}
		}
		return resultCateList;
	}

	/**
	 * 특정 최상위 카테고리들을 특정 뎁스 까지, 단 계층구조로 리턴
	 * @param ancestors - 최상위 카테고리 ID
	 * @param depth - 뎁스
	 * @return
	 */
	@Override
	public List<CategoryDepthDTO> getUntilCurrentDepthCateList(Collection<Long> ancestors,
		Integer depth) {

		Map<Integer, List<CategoryDepthDTO>> untilCurrentDepthCategory = this.getUntilCurrentDepthCateKeyMap(
			ancestors, depth);

		// 하위 카테 고리 부터 위에 더한다.
		for (int i = untilCurrentDepthCategory.size() - 1; 0 < i; i--) {

			// 마지막 뎁스 카테 고리 리스트
			List<CategoryDepthDTO> currentDepthCateList = untilCurrentDepthCategory.get(i);

			for (int j = 0; j < currentDepthCateList.size(); j++) {

				// 현재 카테 고리 부모 카테 고리 매핑
				CategoryDepthDTO currentDepthCate = currentDepthCateList.get(j);

				// 바로 위에 부모 카테 고리 찾기
				List<CategoryDepthDTO> parentCateList = untilCurrentDepthCategory.get(i - 1);

				// 부모 카테 고리와 아이디 비교
				for (int k = 0; k < parentCateList.size(); k++) {
					if (currentDepthCate.getParentId().equals(parentCateList.get(k).getId())) {
						parentCateList.get(k).addChildCate(currentDepthCate);
						break;
					}
				}
			}
		}
		return untilCurrentDepthCategory.get(0);
	}

	/**
	 * 현재 카테고리의 1뎁스 하위 카테고리들만 조회
	 * @param depth - 뎁스
	 * @param currentId - 현재 아이디
	 * @return List<CategoryDepthDTO>
	 */
	@Override
	public List<CategoryDepthDTO> getCurrentDepthChildCateList(Integer depth, Long currentId) {

		/* 현재 카테고리에 하위 카테고리 조회 */
		return cateRepository.findAllByDepthAndParentCateId(depth + 1, currentId).stream().map(
			CategoryMS::changeDTO).toList();
	}

	/**
	 * 현재 카테고리까지 최상위 카테고리 부터 해당되는 부모 객체들만 조회
	 * @param ancestor
	 * @param depth
	 * @param cateId
	 * @return CategoryDepthDTO
	 */
	@Override
	public CategoryDepthDTO getUntilCurrentDepthFlatCate(Long ancestor, Integer depth, Long cateId) {

		/* DTO로 변환 */
		List<CategoryDepthDTO> cateList = cateRepository.findAllByAncestorAndDepthLessThanEqual(ancestor, depth)
			.stream().map(CategoryMS::changeDTO).toList();

		/* 현재 카테고리 찾기 -> DB 에서 조회 해도 되지만 반복문 으로 꺼냄 */
		CategoryDepthDTO categoryDepthDTO = cateList.stream()
			.filter(c -> cateId.equals(c.getId()))
			.findAny()
			.orElseThrow();

		/* 상위 카테고리만 찾아서 리턴*/
		return categoryDepthDTO.makeOneFlatCateChild(null, cateList);
	}

	/**
	 * 모든 카테고리 조회
	 * @return List<CategoryDepthDTO>
	 */
	@Override
	public List<CategoryDepthDTO> getAllCateList() {
		List<CategoryDepthDTO> cateList = cateRepository.findAll().stream().map(CategoryMS::changeDTO).toList();

		for (int i = 0; i < cateList.size(); i++) {
			/* 현재 카테고리 */
			CategoryDepthDTO currentCate = cateList.get(i);

			for (int j = 0; j < cateList.size(); j++) {
				/* 부모 카테고리 */
				CategoryDepthDTO parentCate = cateList.get(j);

				/* 비교해서 찾기 */
				if (currentCate.getParentId().equals(parentCate.getId()) && !currentCate.getParentId()
					.equals(currentCate.getId())) {
					parentCate.addChildCate(currentCate);
				}
			}
		}
		/* 최상위만 카테고리만 리턴 */
		return cateList.stream().filter(c -> c.getParentId().equals(c.getId())).toList();
	}

	/**
	 * 카테고리 이름으로 조회 equal
	 * @param cateName - 카테고리 이름
	 * @return CategoryMS
	 */
	@Override
	public CategoryMS getCategoryDtoByCategoryNm(String cateName) {
		return cateRepository.findByCateNm(cateName);
	}
}
