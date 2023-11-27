package com.trip.penguin.cs.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.trip.penguin.cs.domain.CsMS;
import com.trip.penguin.user.domain.UserMS;

public interface CsMsRepository extends JpaRepository<CsMS, Long> {

	Page<CsMS> findAllByUserMS(UserMS userMS, Pageable pageable);
}
