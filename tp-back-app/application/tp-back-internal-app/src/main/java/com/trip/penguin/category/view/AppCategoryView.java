package com.trip.penguin.category.view;

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
public class AppCategoryView {

	private String categoryName;

	private Integer depth;

	private Long parentId;

}
