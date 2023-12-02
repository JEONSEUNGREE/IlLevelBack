package com.trip.penguin.category.repository;

import com.trip.penguin.category.domain.CategoryMS;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public interface CateRepository extends JpaRepository<CategoryMS, Long> {

    Stack<CategoryMS> findAllByAncestorInAndDepthLessThanEqual(Collection<Long> ancestors, Integer depth);

    List<CategoryMS> findAllByAncestorAndDepthEqualsAndParentCateId(Long ancestor, Integer depth, Long parentCate_id);

    List<CategoryMS> findAllByAncestorAndDepthLessThanEqual(Long ancestor, Integer depth);

}
