package com.trip.penguin.room.service;

import com.trip.penguin.company.domain.CompanyMS;
import com.trip.penguin.room.domain.RoomMS;
import com.trip.penguin.room.repository.RoomMSRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@NoArgsConstructor
public class RoomServiceImpl implements RoomService {


    private RoomMSRepository roomMSRepository;

    @Autowired
    public RoomServiceImpl(RoomMSRepository roomMSRepository) {
        this.roomMSRepository = roomMSRepository;
    }

    @Override
    public RoomMS createRoom(RoomMS roomMS, CompanyMS companyMS) {
        roomMS.setCompanyInfo(companyMS);
        return roomMSRepository.save(roomMS);
    }

    @Override
    public Optional<RoomMS> getRoomById(RoomMS roomMS) {
        return roomMSRepository.findById(roomMS.getId());
    }


    @Override
    public RoomMS updateRoom(RoomMS roomMS) {
        return roomMSRepository.save(roomMS);
    }

    @Override
    public List<RoomMS> getRoomPicListByRoomId(RoomMS roomMS) {
        return roomMSRepository.getRoomPicListByRoomId(roomMS.getId());
    }

    @Override
    public List<RoomMS> getReviewListByRoomId(RoomMS roomMS) {
        return roomMSRepository.getReviewListByRoomId(roomMS.getId());
    }

    @Override
    public Optional<RoomMS> getRoomBookingList(RoomMS roomMS) {
        return null;
    }

    @Override
    public void deleteRoom(RoomMS roomMS) {
        roomMSRepository.deleteById(roomMS.getId());
    }

}
