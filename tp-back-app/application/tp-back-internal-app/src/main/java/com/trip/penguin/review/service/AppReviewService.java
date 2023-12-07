package com.trip.penguin.review.service;

import com.trip.penguin.resolver.vo.LoginUserInfo;
import com.trip.penguin.review.dto.AppReviewDTO;
import com.trip.penguin.review.view.AppReviewView;

public interface AppReviewService {

	AppReviewDTO reviewCreate(AppReviewView appReviewView, LoginUserInfo loginUserInfo);
}
