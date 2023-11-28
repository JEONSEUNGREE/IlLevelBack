package com.trip.penguin.cs.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCsqDetailDTO {

	private Long id;

	private String csqTitle;

	private String csqContent;

	private LocalDateTime createdDate;

	private LocalDateTime modifiedDate;

}
