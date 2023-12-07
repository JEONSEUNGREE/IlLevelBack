package com.trip.penguin.review.dto;

import java.time.LocalDateTime;

import com.trip.penguin.review.domain.ReviewMS;

import lombok.Getter;

@Getter
public class AppReviewDTO {

	private Long id;

	private String reTitle;

	private String reContent;

	private Integer rating;

	private LocalDateTime createdDate;

	private LocalDateTime modifiedDate;

	public AppReviewDTO changeDTO(ReviewMS reviewMS) {
		this.id = reviewMS.getId();
		this.reTitle = reviewMS.getReTitle();
		this.reContent = reviewMS.getReContent();
		this.rating = reviewMS.getRating();
		this.createdDate = reviewMS.getCreatedDate();
		this.modifiedDate = reviewMS.getModifiedDate();
		return this;
	}

}
