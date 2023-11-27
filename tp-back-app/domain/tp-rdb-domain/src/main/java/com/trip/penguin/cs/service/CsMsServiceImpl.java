package com.trip.penguin.cs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.trip.penguin.cs.domain.CsMS;
import com.trip.penguin.cs.repository.CsMsRepository;
import com.trip.penguin.exception.DataNotFoundException;
import com.trip.penguin.exception.UserNotAllowedException;
import com.trip.penguin.exception.UserNotFoundException;
import com.trip.penguin.user.domain.UserMS;
import com.trip.penguin.user.repository.UserMSRepository;

@Service
public class CsMsServiceImpl implements CsMsService {

	private final CsMsRepository csMsRepository;

	private final UserMSRepository userMSRepository;

	@Autowired
	public CsMsServiceImpl(CsMsRepository csMsRepository, UserMSRepository userMSRepository) {
		this.csMsRepository = csMsRepository;
		this.userMSRepository = userMSRepository;
	}

	@Override
	public CsMS createCsMsByUserEmail(String userEmail, CsMS csMS) {
		UserMS foundUser = userMSRepository.findByUserEmail(userEmail)
			.orElseThrow(UserNotFoundException::new);

		csMS.createCsMs(foundUser);
		return csMsRepository.save(csMS);
	}

	@Override
	public CsMS getCsMsDetailByUserEmailAndCsMsId(String userEmail, Long csMsId) throws UserNotAllowedException {
		CsMS csMS = csMsRepository.findById(csMsId).orElseThrow(DataNotFoundException::new);

		if (!userEmail.equalsIgnoreCase(csMS.getUserMS().getUserEmail())) {
			throw new UserNotAllowedException();
		}

		return csMS;
	}

	@Override
	public Page<CsMS> getCsMsDetailListByUserEmail(String userEmail, Pageable pageable) {
		UserMS foundUser = userMSRepository.findByUserEmail(userEmail).orElseThrow(UserNotFoundException::new);

		return csMsRepository.findAllByUserMS(foundUser, pageable);
	}

	@Override
	public void deleteCsMsByUserEmailAndCsMsId(String userEmail, Long csMsId) throws UserNotAllowedException {
		CsMS foundCsMS = csMsRepository.findById(csMsId).orElseThrow(DataNotFoundException::new);

		if (!userEmail.equalsIgnoreCase(foundCsMS.getUserMS().getUserEmail())) {
			throw new UserNotAllowedException();
		}

		csMsRepository.deleteById(foundCsMS.getId());
	}
}
