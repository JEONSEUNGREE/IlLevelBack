package com.trip.penguin.cs.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.trip.penguin.cs.domain.CsMS;
import com.trip.penguin.cs.dto.UserCsqDetailDTO;
import com.trip.penguin.cs.dto.UserCsqPageDTO;
import com.trip.penguin.cs.view.UserCsqView;
import com.trip.penguin.exception.UserNotAllowedException;
import com.trip.penguin.exception.UserNotFoundException;
import com.trip.penguin.resolver.vo.LoginUserInfo;

@Service
public class AppCsServiceImpl implements AppCsService {

	private CsMsService csMsService;

	@Autowired
	public AppCsServiceImpl(CsMsService csMsService) {
		this.csMsService = csMsService;
	}

	@Override
	public UserCsqDetailDTO userMyPageCsqCreate(LoginUserInfo loginUserInfo, UserCsqView csqView) throws UserNotFoundException {
		CsMS csMS = new CsMS();
		UserCsqDetailDTO userCsqDetailDTO = new UserCsqDetailDTO();

		BeanUtils.copyProperties(csqView, csMS);
		CsMS createdCsMs = csMsService.createCsMsByUserEmail(loginUserInfo.getUserEmail(), csMS);

		BeanUtils.copyProperties(createdCsMs, userCsqDetailDTO);

		return userCsqDetailDTO;
	}

	@Override
	public UserCsqDetailDTO userMyPageCsqRead(LoginUserInfo loginUserInfo, Integer csqId) throws UserNotFoundException {
		UserCsqDetailDTO userCsqDetailDTO = new UserCsqDetailDTO();

		CsMS getCsMsDetail = csMsService.getCsMsDetailByUserEmailAndCsMsId(loginUserInfo.getUserEmail(), csqId.longValue());

		BeanUtils.copyProperties(getCsMsDetail, userCsqDetailDTO);

		return userCsqDetailDTO;
	}

	@Override
	public UserCsqPageDTO userMyPageCsqList(LoginUserInfo loginUserInfo, Integer curPage) {
		Page<CsMS> csMsList = csMsService.getCsMsDetailListByUserEmail(loginUserInfo.getUserEmail(),
			PageRequest.of(curPage, 10));

		List<UserCsqDetailDTO> covertList = csMsList.stream().map(item ->
			UserCsqDetailDTO.builder()
				.id(item.getId())
				.csqContent(item.getCsqContent())
				.csqTitle(item.getCsqTitle())
				.modifiedDate(item.getModifiedDate())
				.createdDate(item.getCreatedDate())
				.build()).toList();

		return UserCsqPageDTO.builder()
			.csqList(covertList)
			.pageNumber(csMsList.getNumber())
			.totalPage(csMsList.getTotalPages())
			.build();
	}

	@Override
	public void userMyPageCsqDelete(LoginUserInfo loginUserInfo, Integer csqId) throws UserNotAllowedException {
		csMsService.deleteCsMsByUserEmailAndCsMsId(loginUserInfo.getUserEmail(), csqId.longValue());
	}
}
