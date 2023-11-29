package com.trip.penguin.user.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFollowDTO {

    private Long followId;

    private String userNick;

    private String userImg;

}
