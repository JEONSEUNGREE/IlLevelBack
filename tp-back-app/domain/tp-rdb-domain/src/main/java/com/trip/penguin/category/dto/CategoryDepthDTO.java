package com.trip.penguin.category.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDepthDTO {

	private Long id;

	private Long ancestor;

	private Long parentId;

	private String cateName;

	private String parentCateName;

	private Integer depth;

	private List<CategoryDepthDTO> childCateList = new ArrayList<>();

	public void addChildCate(CategoryDepthDTO childCate) {
		this.childCateList.add(childCate);
	}

	public CategoryDepthDTO makeOneFlatCateChild(CategoryDepthDTO categoryDepthDTO,
		List<CategoryDepthDTO> childCateList) {

		if (categoryDepthDTO != null) {
			this.childCateList.add(categoryDepthDTO);
		}

		if (!this.id.equals(this.parentId)) {
			childCateList.stream().filter(c -> this.parentId.equals(c.getId()))
				.findAny().orElseThrow().makeOneFlatCateChild(this, childCateList);
		}

		return childCateList.stream().filter(c -> this.ancestor.equals(c.id)).findAny().get();
	}
}
