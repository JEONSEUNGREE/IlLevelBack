package com.trip.penguin.user.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.trip.penguin.account.service.DefaultUserService;
import com.trip.penguin.resolver.vo.LoginUserInfo;
import com.trip.penguin.user.domain.UserMS;
import com.trip.penguin.user.dto.UserMyPageDTO;
import com.trip.penguin.user.view.UserMyPageView;
import com.trip.penguin.util.ImgUtils;

@Service
public class UserMyPageServiceImpl implements UserMyPageService {

	@Value("file.save.user-path")
	private String defaultUserImgRoute;

	private UserService userService;

	private DefaultUserService defaultUserService;

	private ImgUtils imgUtils;

	@Autowired
	public UserMyPageServiceImpl(UserService userService, DefaultUserService defaultUserService, ImgUtils imgUtils) {
		this.userService = userService;
		this.defaultUserService = defaultUserService;
		this.imgUtils = imgUtils;
	}

	@Override
	public UserMyPageDTO userMyPageModify(LoginUserInfo loginUserInfo, UserMyPageView userMyPageView,
										  MultipartFile multipartFile) throws
		RuntimeException {

		String userImgUUID = "";
		UserMyPageDTO userMyPageDto = new UserMyPageDTO();

		// 여러 모듈에 의존 하여 좋지 못한 방법이라고 생각됨
		try {

			// 도메인 모듈에서 조회
			UserMS userMS = userService.getUserByUserEmail(loginUserInfo.getUserEmail())
				.orElseThrow(IllegalAccessException::new);

			// 이미지 저장
			userImgUUID = imgUtils.saveAndChangeFile(multipartFile, userMS, defaultUserImgRoute);

			// webSystem 모듈에서 패스워드 인코딩 후 업데이트
			BeanUtils.copyProperties(userMyPageView, userMS);
			UserMS updatedUser = defaultUserService.modifyUser(userMS);

			// 리턴 객체
			BeanUtils.copyProperties(updatedUser, userMyPageDto);

		} catch (Exception e) {
			imgUtils.deleteFile(userImgUUID, defaultUserImgRoute);
			throw new RuntimeException("회원 정보 수정 실패");
		}

		return userMyPageDto;
	}
}
