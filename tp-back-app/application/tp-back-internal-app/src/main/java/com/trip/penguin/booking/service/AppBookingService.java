package com.trip.penguin.booking.service;

import com.trip.penguin.booking.dto.AppBookingDTO;
import com.trip.penguin.booking.view.AppBookingView;
import com.trip.penguin.resolver.vo.LoginUserInfo;
import com.trip.penguin.room.domain.RoomMS;
import com.trip.penguin.user.domain.UserMS;

public interface AppBookingService {

    AppBookingDTO bookingCreate(AppBookingView appBookingView, LoginUserInfo loginUserInfo);

}
