package com.trip.penguin.booking.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.trip.penguin.booking.domain.BookingMS;
import com.trip.penguin.booking.dto.AppBookingDTO;
import com.trip.penguin.booking.repository.BookingMSRepository;
import com.trip.penguin.booking.view.AppBookingView;
import com.trip.penguin.constant.CommonConstant;
import com.trip.penguin.resolver.vo.LoginUserInfo;
import com.trip.penguin.room.domain.RoomMS;
import com.trip.penguin.room.repository.RoomMSRepository;
import com.trip.penguin.user.domain.UserMS;
import com.trip.penguin.user.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class AppBookingServiceImpl implements AppBookingService {

	private BookingMSRepository bookingMSRepository;

	private RoomMSRepository roomMSRepository;

	private UserService userService;

	@Autowired
	public AppBookingServiceImpl(BookingMSRepository bookingMSRepository, RoomMSRepository roomMSRepository,
		UserService userService) {
		this.bookingMSRepository = bookingMSRepository;
		this.roomMSRepository = roomMSRepository;
		this.userService = userService;
	}

	@Override
	@Transactional
	public AppBookingDTO bookingCreate(AppBookingView appBookingView, LoginUserInfo loginUserInfo) {

		UserMS foundUser = userService.getUserByUserEmail(loginUserInfo.getUserEmail()).orElseThrow();

		RoomMS foundRoom = roomMSRepository.findByRoomId(appBookingView.getRoomId());

		foundRoom.checkStock(appBookingView.getCount());

		foundRoom.decreaseStock(appBookingView.getCount());

		BookingMS booking = BookingMS.builder()
			.checkOut(foundRoom.getCheckOut())
			.checkIn(foundRoom.getCheckIn())
			.payAmount(appBookingView.getCount())
			.userMS(foundUser)
			.room(foundRoom)
			.count(appBookingView.getCount())
			.sellPrc(foundRoom.getSellPrc())
			.payAmount(foundRoom.getSellPrc() * appBookingView.getCount())
			.couponYn(CommonConstant.N.getName())
			.bookNm(foundRoom.getRoomNm())
			.modifiedDate(LocalDateTime.now())
			.createDate(LocalDateTime.now())
			.build();

		roomMSRepository.save(foundRoom);
		bookingMSRepository.save(booking);

		return null;
	}
}
