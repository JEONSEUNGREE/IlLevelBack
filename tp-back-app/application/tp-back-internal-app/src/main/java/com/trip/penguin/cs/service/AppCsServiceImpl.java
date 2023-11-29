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
import com.trip.penguin.resolver.vo.LoginInfo;

@Service
public class AppCsServiceImpl implements AppCsService {

	private CsMsService csMsService;

	@Autowired
	public AppCsServiceImpl(CsMsService csMsService) {
		this.csMsService = csMsService;
	}

	@Override
	public UserCsqDetailDTO userMyPageCsqCreate(LoginInfo loginInfo, UserCsqView csqView) throws UserNotFoundException {
		CsMS csMS = new CsMS();
		UserCsqDetailDTO userCsqDetailDTO = new UserCsqDetailDTO();

		BeanUtils.copyProperties(csqView, csMS);
		CsMS createdCsMs = csMsService.createCsMsByUserEmail(loginInfo.getUserEmail(), csMS);

		BeanUtils.copyProperties(createdCsMs, userCsqDetailDTO);

		return userCsqDetailDTO;
	}

	@Override
	public UserCsqDetailDTO userMyPageCsqRead(LoginInfo loginInfo, Integer csqId) throws UserNotFoundException {
		UserCsqDetailDTO userCsqDetailDTO = new UserCsqDetailDTO();

		CsMS getCsMsDetail = csMsService.getCsMsDetailByUserEmailAndCsMsId(loginInfo.getUserEmail(), csqId.longValue());

		BeanUtils.copyProperties(getCsMsDetail, userCsqDetailDTO);

		return userCsqDetailDTO;
	}

	@Override
	public UserCsqPageDTO userMyPageCsqList(LoginInfo loginInfo, Integer curPage) {
		Page<CsMS> csMsList = csMsService.getCsMsDetailListByUserEmail(loginInfo.getUserEmail(),
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
	public void userMyPageCsqDelete(LoginInfo loginInfo, Integer csqId) throws UserNotAllowedException {
		csMsService.deleteCsMsByUserEmailAndCsMsId(loginInfo.getUserEmail(), csqId.longValue());
	}
}
