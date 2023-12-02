package com.trip.penguin.category.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryInfoDTO {

    private Long id;

    private Long ancestor;

    private Long parentId;

    private String cateName;

    private Integer depth;
}
