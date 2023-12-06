package com.trip.penguin.user.service;

import java.util.Optional;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.trip.penguin.user.domain.UserMS;
import com.trip.penguin.user.repository.UserMSRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserMSRepository userMSRepository;

	/**
	 * 회원 가입
	 * @param userMS - 회티원 엔티티
	 * @return UserMS
	 */
	@Override
	public UserMS signUpUser(UserMS userMS) {
		userMS.createUserMs();
		return userMSRepository.save(userMS);
	}

	/**
	 * 회원 정보 조회
	 * @param userMS - 회원 엔티티
	 * @return Optional<UserMS>
	 */
	@Override
	public Optional<UserMS> getUser(UserMS userMS) {
		return userMSRepository.findById(userMS.getId());
	}


	/**
	 * 회원 정보 조회
	 * @param userId - 회원 ID
	 * @return Optional<UserMS>
	 */
    @Override
    public Optional<UserMS> getUserByUserId(Long userId) {
		return userMSRepository.findById(userId);
    }

    /**
	 * 회원 정보 조회
	 * @param userEmail - 회원 이메일
	 * @return Optional<UserMS>
	 */
	@Override
	public Optional<UserMS> getUserByUserEmail(String userEmail) {
		return userMSRepository.findByUserEmail(userEmail);
	}

	/**
	 * 회원 정보 수정
	 * @param userMS - 회원 엔티티
	 * @return UserMS
	 */
	@Override
	public UserMS updateUser(UserMS userMS) {
		return userMSRepository.save(userMS);
	}

	/**
	 * 회원 탈퇴
	 * @param userMS - 회원 엔티티
	 */
	@Override
	public void deleteUser(UserMS userMS) {
		userMSRepository.deleteById(userMS.getId());
	}
}
