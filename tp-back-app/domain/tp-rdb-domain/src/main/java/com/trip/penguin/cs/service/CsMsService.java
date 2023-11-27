package com.trip.penguin.cs.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.trip.penguin.cs.domain.CsMS;
import com.trip.penguin.exception.UserNotAllowedException;
import com.trip.penguin.exception.UserNotFoundException;

public interface CsMsService {

	CsMS createCsMsByUserEmail(String userEmail, CsMS csMS) throws UserNotFoundException;

	CsMS getCsMsDetailByUserEmailAndCsMsId(String userEmail, Long CsMsId) throws UserNotAllowedException;

	Page<CsMS> getCsMsDetailListByUserEmail(String userEmail, Pageable pageable);

	void deleteCsMsByUserEmailAndCsMsId(String userEmail, Long CsMsId) throws UserNotAllowedException;

}
