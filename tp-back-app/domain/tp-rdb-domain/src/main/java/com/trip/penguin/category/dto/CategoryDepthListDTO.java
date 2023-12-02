package com.trip.penguin.category.dto;


import com.trip.penguin.category.domain.CategoryMS;
import lombok.*;

import java.util.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDepthListDTO {

    private Long id;

    private Long ancestor;

    private Long parentId;

    private String cateName;

    private String parentCateName;

    private Integer depth;

    private List<CategoryDepthListDTO> childCateList = new ArrayList<>();

    public void addChildCate(CategoryDepthListDTO childCate) {
        this.childCateList.add(childCate);
    }

    public CategoryDepthListDTO makeOneFlatCateChild(CategoryDepthListDTO categoryDepthListDTO, List<CategoryDepthListDTO> childCateList) {

        if (categoryDepthListDTO != null) {
            this.childCateList.add(categoryDepthListDTO);
        }

        if (!this.id.equals(this.parentId)) {
            childCateList.stream().filter(c -> this.parentId.equals(c.getId()))
                    .findAny().orElseThrow().makeOneFlatCateChild(this, childCateList);
        }

        return childCateList.stream().filter(c -> this.ancestor.equals(c.id)).findAny().get();
    }
}
