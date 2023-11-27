package com.trip.penguin.user.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.trip.penguin.cs.domain.CsMS;
import com.trip.penguin.cs.service.CsMsService;
import com.trip.penguin.exception.UserNotAllowedException;
import com.trip.penguin.exception.UserNotFoundException;
import com.trip.penguin.oauth.service.DefaultUserService;
import com.trip.penguin.resolver.vo.LoginInfo;
import com.trip.penguin.user.domain.UserMS;
import com.trip.penguin.user.dto.UserCsqDetailDTO;
import com.trip.penguin.user.dto.UserCsqPageDTO;
import com.trip.penguin.user.dto.UserMyPageDTO;
import com.trip.penguin.user.view.UserCsqView;
import com.trip.penguin.user.view.UserMyPageView;
import com.trip.penguin.util.ImgUtils;

@Service
public class UserMyPageServiceImpl implements UserMyPageService {

	private UserService userService;

	private DefaultUserService defaultUserService;

	private CsMsService csMsService;

	private ImgUtils imgUtils;

	@Autowired
	public UserMyPageServiceImpl(UserService userService, DefaultUserService defaultUserService,
		CsMsService csMsService,
		ImgUtils imgUtils) {
		this.userService = userService;
		this.defaultUserService = defaultUserService;
		this.csMsService = csMsService;
		this.imgUtils = imgUtils;
	}

	@Override
	public UserMyPageDTO userMyPageModify(LoginInfo loginInfo, UserMyPageView userMyPageView,
		MultipartFile multipartFile) throws
		RuntimeException {

		String userImgUUID = "";
		UserMyPageDTO userMyPageDto = new UserMyPageDTO();

		// 여러 모듈에 의존 하여 좋지 못한 방법이라고 생각됨
		try {

			// 도메인 모듈에서 조회
			UserMS userMS = userService.getUserByUserEmail(loginInfo.getUserEmail())
				.orElseThrow(IllegalAccessException::new);

			// 이미지 저장
			userImgUUID = imgUtils.saveAndChangeFile(multipartFile, userMS);

			// webSystem 모듈에서 패스워드 인코딩 후 업데이트
			BeanUtils.copyProperties(userMyPageView, userMS);
			UserMS updatedUser = defaultUserService.modifyUser(userMS);

			// 리턴 객체
			BeanUtils.copyProperties(updatedUser, userMyPageDto);

		} catch (Exception e) {
			imgUtils.deleteFile(userImgUUID);
			throw new RuntimeException("회원 정보 수정 실패");
		}

		return userMyPageDto;
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
