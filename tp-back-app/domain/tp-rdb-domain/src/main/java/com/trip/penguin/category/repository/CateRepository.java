package com.trip.penguin.category.repository;

import java.util.Collection;
import java.util.List;
import java.util.Stack;

import org.springframework.data.jpa.repository.JpaRepository;

import com.trip.penguin.category.domain.CategoryMS;

public interface CateRepository extends JpaRepository<CategoryMS, Long> {

	Stack<CategoryMS> findAllByAncestorInAndDepthLessThanEqual(Collection<Long> ancestors, Integer depth);

	List<CategoryMS> findAllByAncestorAndDepthLessThanEqual(Long ancestor, Integer depth);

	CategoryMS findByCateNm(String cateNm);

	List<CategoryMS> findAllByDepthAndParentCateId(Integer depth, Long parentId);
}
