package com.trip.penguin.user.dto;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UserMyPageDto {

    @NonNull
    private String userLast;

    @NonNull
    private String userFirst;

    @NonNull
    private String userNick;

    @NonNull
    private String userImg;

    @NonNull
    private String userCity;


}
