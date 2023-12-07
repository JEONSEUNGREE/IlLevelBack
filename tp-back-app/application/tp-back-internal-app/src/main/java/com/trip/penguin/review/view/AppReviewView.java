package com.trip.penguin.review.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AppReviewView {

	private Long bookingId;

	private String reTitle;

	private String reContent;

	private Integer rating;

}
