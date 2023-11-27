package com.trip.penguin.user.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserCsqPageDTO {

	private List<UserCsqDetailDTO> csqList;

	private int pageNumber;

	private int totalPage;
}
