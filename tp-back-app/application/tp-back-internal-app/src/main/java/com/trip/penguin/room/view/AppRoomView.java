package com.trip.penguin.room.view;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppRoomView {

    private String roomNm;

    private String comName;

    private Integer sellPrc;

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

    private String roomDesc;

    private String couponYn;

    private Integer maxCount;

}
